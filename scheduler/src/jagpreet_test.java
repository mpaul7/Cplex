#include <ilcplex/ilocplex.h>
#include "schedule.h"

ILOSTLBEGIN

typedef IloArray<IloNumVarArray> IloNumVarArrayArray;
typedef IloArray<IloNumArray> IloNumArrayArray;
typedef IloArray<IloNumArrayArray> IloNumArray3D;
typedef IloArray<IloNumVarArrayArray> IloNumVarArray3D;
typedef IloArray<IloNumVarArray3D> IloNumVarArray4D;


bool schedule::solveGeneral(float timeLimit, int nCopies, float mipgap, int algo, int mipemph) {
	bool status;
	IloEnv env;
	bool stop = false;
	
	cout<<"Overlapping Jobs\n";
	for(int i=0;i<nJobs;i++) {
		cout<<i+1 <<" ";
		for(int j=0;j<nJobs;j++) {
			cout<<ol[i][j]<<" ";
		}
		cout<<"\n";
	}

	if (nCopies == -1)
		cout << "Solving the General MILP for unrestricted duplication for " << timeLimit << " seconds " << "using " << 100 << " percent optimality" << endl;
	else
		cout << "Solving the General MILP for " << nCopies << " copies of a task for " << timeLimit << " seconds " << "using " << 100 << " percent optimality" << endl;

	try {
		IloModel model(env);
		IloCplex cplex(env);

		// Declaring Parameters
		int numJ;
		IloInt numJobs , numPE = this->nPE;	//, M = this->makespan;

		float	alpha_f=mipgap;//=10,alpha_e=1,alpha_t=1;

		// Clustering
		stop = Cluster1(numJ);
		numJobs = numJ;

		//decide on number of jobs here and also decide which jobs are in the cluster

		IloNumArrayArray 	Edges(env, numJobs),Energybusy(env, numPE);
		IloNumArray3D		ExecutionCost(env, numJobs);

		IloInt				minF=0, maxF=0 ;
		IloInt				minF2=0 ,maxF2=0;
		// Fill In Values for jobs, pe, edges and execution cost by parsing
		// Adjacency list for the Task Graph
		for(IloInt i = 0; i < numJobs; ++i) {
			IloInt idi = Job[i];
			Edges[i] = IloNumArray(env, numJobs);
			for(IloInt j = 0; j < numJobs; ++j) {
				IloInt idj = Job[j];
				if (this->edges[idi].count(idj) > 0)
					Edges[i][j] = this->edges[idi][idj];
				else
					Edges[i][j] = 0;
			}
		}

		for(IloInt i = 0; i < numJobs; ++i) {
			IloInt idi = Job[i];
			ExecutionCost[i] = IloNumArrayArray(env, numPE);

			for(IloInt j = 0; j < numPE; ++j) {
				minF = 0; maxF = 0;	minF2 = 0 ;	maxF2 = 0 ;
				ExecutionCost[i][j] = IloNumArray(env, 2);		// created array of voltage levels
				Energybusy[j] = IloNumArray(env, 2);		// created array of voltage levels
				
				  //remaining level execution cost
				for(IloInt v=0; v < Num_VL[j] ; ++v) {
					if(VL_T[j][v] > maxF )
						maxF = VL_T[j][v] ;
					if(VL_T[j][v] < minF )
						minF = VL_T[j][v] ;
				}

				ExecutionCost[i][j][1]=this->execution_cost[idi][j]*(2 - maxF/100);
				ExecutionCost[i][j][0]=this->execution_cost[idi][j]*(2 - minF/100);
				//***********************************ask access************************//
				for(IloInt v=0; v<Num_VL[j] ; ++v)	{
					if(VL_E[j][v] > maxF2 )
						maxF2 = VL_E[j][v] ;
					if(VL_E[j][v] < minF2 )
						minF2 = VL_E[j][v] ;
				}
				Energybusy[j][1] = (this->E_busy[j]/pow(VL_E[j][0],2)) * pow(maxF2,2);
				Energybusy[j][0] = (this->E_busy[j]/pow(VL_E[j][0],2)) * pow(minF2,2);
			}
		}

		// Declaring Variables
		IloNumVarArray3D d(env, numJobs);
		IloNumVarArrayArray s(env, numJobs), f(env, numJobs), x(env, numJobs);//, drelax(env,numJobs);
		IloNumVarArray4D	EdgePP(env,numJobs);
		IloNumVarArrayArray dd(env, numJobs);
		IloNumVarArray3D o(env, nPE);
		IloNumVarArray f_min(env, numJobs, 0, M, ILOFLOAT);
		IloNumVar f_max(env, ILOFLOAT);
		//Variable for Energy and tardiness
		IloNumVarArray			Ebusy(env,numPE,0,M*M,ILOFLOAT), Eidle(env,numPE,0,M*M,ILOFLOAT);
		IloNumVarArrayArray		ECbusy(env,numPE), ECidle(env,numPE);
		IloNumVar				Etotal;

		for(IloInt w= 0; w < numJobs; ++w) {
			d[w] = IloNumVarArrayArray(env, nPE);
			dd[w] = IloNumVarArray(env, nPE,0, 1, ILOINT);
			EdgePP[w] = IloNumVarArray3D(env,numJobs);

			for(IloInt m=0;m<nPE;m++)
				d[w][m] = IloNumVarArray(env, 2, 0, 1, ILOFLOAT);

			s[w] = IloNumVarArray(env, nPE, 0, M, ILOFLOAT);
			f[w] = IloNumVarArray(env, nPE, 0, M, ILOFLOAT);
			x[w] = IloNumVarArray(env, nPE, 0, 1, ILOINT);

			for(IloInt i= 0; i < numJobs; ++i) {
				EdgePP[w][i] = IloNumVarArrayArray(env,nPE);
				for(IloInt m=0;m<nPE;m++)
					EdgePP[w][i][m] = IloNumVarArray(env,nPE,0,1,ILOINT);
			}
		}

		for(IloInt w = 0; w < nPE; ++w) {
			ECbusy[w] = IloNumVarArray(env, nPE, 0, M*M, ILOFLOAT);
			ECidle[w] = IloNumVarArray(env, nPE, 0, M*M, ILOFLOAT);

			o[w] = IloNumVarArrayArray(env, numJobs);
			for(IloInt i = 0; i < numJobs; ++i) {
				o[w][i] = IloNumVarArray(env, numJobs, 0, 1, ILOINT);
			}
		}

		IloInt i, j, m, k,dl,idi,idj,v;

		// C1
		for(i = 0; i < numJobs; ++i) {
			for (m = 0; m < nPE; ++m) {
				model.add(f_max >= f[i][m]);
			}
		}

		for(i = 0; i < numJobs; ++i) {
			IloNumVarArray auxdd(env);
			for (m = 0; m < nPE; ++m) {
				IloNumVarArray aux(env);
				for (v = 0; v < 2; ++v)
					aux.add(d[i][m][v]);
				model.add(dd[i][m] == IloSum(aux));
				auxdd.add(dd[i][m]);
				aux.end();
			}
			model.add(IloSum(auxdd) >= 1);
			if(i==0)
				model.add(IloSum(auxdd) <= nPE);
			else if (nCopies != -1)
				model.add(IloSum(auxdd) <= nCopies);
			auxdd.end();
		}

		// C3
		for(i = 0; i < numJobs; ++i) {
			for (m = 0; m < nPE; ++m) {
				for (k = 0; k < nPE; ++k) {
					model.add(f[i][k] + (x[i][k] - 1)*M <= f_min[i]);
				}
			}
		}


		// C5
		for(i = 0; i < numJobs; ++i) {
			for (m = 0; m < nPE; ++m) {
				IloExpr aux(env);
				for (v = 0; v < 2; ++v)
				{
					aux += d[i][m][v] * ExecutionCost[i][m][v];
				}

				model.add(f[i][m] == s[i][m] + aux);
				aux.end();
			}
		}

		// C6
		for(i = 0; i < numJobs; ++i) {
			for(j = 0; j < numJobs; ++j) {
				if (i != j && ol[i][j])
					for (m = 0; m < nPE; ++m) {
						model.add(f[i][m] - s[j][m] <= M * o[m][i][j]);
					}
			}
		}

		// C7
		for(i = 0; i < numJobs; ++i) {
			for(j = 0; j < numJobs; ++j) {
				if (i != j && ol[i][j])
					for (m = 0; m < nPE; ++m)
						model.add(o[m][i][j] + o[m][j][i] + dd[i][m] + dd[j][m] <= 3); //
			}
		}

		// C8
		for(i = 0; i < numJobs; ++i) {
			IloNumVarArray aux(env);
			for (m = 0; m < nPE; ++m) {
				aux.add(x[i][m]);
				model.add(x[i][m] <= dd[i][m]);
			}
			model.add(IloSum(aux) == 1);
			aux.end();
		}

		// C9 
		for(i = 0; i < numJobs; ++i)
			for(m = 0; m < nPE; ++m) 
			{
				IloNumVarArray aux5(env);
				for(v= 0 ; v<2 ; ++v) {
					aux5.add(d[i][m][v]);
				}
				model.add(dd[i][m] == IloSum(aux5) );
				aux5.end();
			}

		// Constraints for Communication Cost

		// X1, X2, X3
		for(i = 0; i < numJobs; ++i)
			for(j = 0; j < numJobs; ++j)
				if (i != j && Edges[i][j] != 0)
					for (m = 0; m < nPE; ++m) {
						IloExpr x1(env), x3(env); //x2(env)
						x1 = f_min[i] + (dd[j][m] - dd[i][m])*Edges[i][j] - (1 - dd[j][m])*M;
//						x3 = f[i][m] - (2 - dd[i][m] - dd[j][m])*M;
						model.add(x1 <= s[j][m]);
//						model.add(x3 <= s[j][m]);
						model.add(f[i][m] <= s[j][m]);
						x1.end(); x3.end();
					}

		for(i = 0; i < numJobs; ++i)
			for(j = 0; j < numJobs; ++j)
				if (i != j && Edges[i][j] != 0)
					for (m = 0; m < nPE; ++m)
						for (k = 0; k < nPE; ++k)
							if(m!=k) {
								model.add(x[i][m] + dd[j][k] + (1-dd[i][k]) - 3 * EdgePP[i][j][m][k] <= 2);
								model.add(x[i][m] + dd[j][k] + (1-dd[i][k]) - 3 * EdgePP[i][j][m][k] >= 0);
							}

		IloNumExprArray etotal(env);
		for(m = 0; m < numPE; ++m) {
			IloExpr epp(env);					//energy per processor
			IloExpr excost(env);				//execution cost per processor
			for(i = 0; i < numJobs; ++i) {
				for (v = 0; v < 2; ++v) {
					excost +=  ExecutionCost[i][m][v] * d[i][m][v];
					epp += ExecutionCost[i][m][v] * d[i][m][v] * Energybusy[m][v];
				}
			}
			model.add(Ebusy[m] >= epp);
			model.add(Eidle[m] >= (f_max - excost) * E_idle[m]);
			etotal.add(Ebusy[m] + Eidle[m]);
			excost.end();
			epp.end();
		}
		
		// --------------------- Calculates Busy Communication Energy between two processors ----------------
		for (m = 0; m < nPE; ++m) {
			for (k = 0; k < nPE; ++k)
				if(m!=k) {
					IloExpr epp(env);
					for(i = 0; i < numJobs; ++i)
						for(j = 0; j < numJobs; ++j)
							if (i != j && Edges[i][j] != 0) {
								epp += EdgePP[i][j][m][k] * Edges[i][j];
							}
					epp *= EC_busy[m][k];
					model.add(ECbusy[m][k] >= epp);
					etotal.add(ECbusy[m][k]);
					epp.end();
				}
		}
		// --------------------- Calculates Idle Communication Energy between two processors ----------------
		for (m = 0; m < nPE; ++m) {
			for (k = 0; k < nPE; ++k)
				if(m!=k && m<k) {
					IloExpr epp(env);
					epp = (f_max - (ECbusy[m][k] + ECbusy[k][m])/EC_busy[m][k]) * EC_idle[m][k];
					model.add(ECidle[m][k] >= epp);
					etotal.add(ECidle[m][k]);
					epp.end();
				}
		}

		
		model.add(IloMinimize(env, IloSum(etotal) + alpha_f * f_max));
//		model.add(IloMinimize(env,IloSum(etotal)));
		cplex.extract(model);

		IloNumVarArray startVar(env);
		IloNumArray startValue(env);
		for(idi = 0; idi < numJobs; ++idi) {
			i = Job[idi];
			for(m = 0; m < numPE; ++m) {
				startVar.add(dd[i][m]);
				startValue.add(this->allocation[i][m]);
			}
		}

		startVar.add(f_max);
		startValue.add(this->makespan);

		cplex.addMIPStart(startVar, startValue, IloCplex::MIPStartAuto, "secondMIPStart");

		cplex.setParam(IloCplex::Probe, 3);			//very aggressive probing

		//Options from command line

		if (algo > 0)
			cplex.setParam(IloCplex::RootAlg,algo);

		if (mipemph > 0)
			cplex.setParam(IloCplex::MIPEmphasis,mipemph);

		if (timeLimit > 0)
			cplex.setParam(IloCplex::TiLim, timeLimit);

//		if (mipgap > 0)
			cplex.setParam(IloCplex::EpGap, 0);

		if (cplex.solve()) {
			env.out() << endl;
 			env.out() << "SolutionStatus= \t" << cplex.getStatus() << endl;
        	env.out() << "SolutionValue= \t" << cplex.getObjValue() << endl;
        	float oldmakespan = this->makespan;
			this->makespan = cplex.getValue(f_max);
			for(i = 0; i < numJobs; ++i) {
				idi = Job[i];
				for(m = 0; m < numPE; ++m) {
					this->allocation[idi][m] = cplex.getValue(dd[i][m]);
					if(this->allocation[idi][m]) {
						for(v=0;v< 2;v++) {
							cout << "v="<< v <<" d="<<cplex.getValue(d[i][m][v])<<endl;
							if(cplex.getValue(d[i][m][v]))
								this->allocation_level[idi][m] = v;
						}
						cout<<endl;
					}
					this->start_time[idi][m] = cplex.getValue(s[i][m]);
					if(this->start_time[idi][m] < 0.0001)
						this->start_time[idi][m] = 0;
					this->finish_time[idi][m] = cplex.getValue(f[i][m]);
				}
			}

			for(i = 0; i < numJobs; ++i)
				for(j = 0; j < numJobs; ++j)
					if (i != j && Edges[i][j] != 0)
						for (m = 0; m < nPE; ++m)
							for (k = 0; k < nPE; ++k)
								if(m!=k)
									if(cplex.getValue(EdgePP[i][j][m][k]))
										cout<<"Edgefrom "<<i+1<<" "<<m<<" - "<<j+1<<" "<<k<<endl;

			float	eidle=0,ebusy=0,ecbusy=0,ecidle=0;
			for(m=0;m<numPE;++m) {
				eidle += cplex.getValue(Eidle[m]);
				ebusy += cplex.getValue(Ebusy[m]);
				for(k=0;k<numPE;++k)
					if(m!=k && m<k) {
						ecbusy += cplex.getValue(ECbusy[m][k]) + cplex.getValue(ECbusy[k][m]);
						ecidle += cplex.getValue(ECidle[m][k]);
					}
			}
			cout <<"----------------------------------\n";
			cout<<"Makespan= \t"<<this->makespan<<"\n";
			cout<<"Speedup= \t"<<oldmakespan/this->makespan<<"\n";
			cout<<"EnergyCore(busy,idle)= \t( "<<ebusy<<" , "<<eidle<<" )\n";
			cout<<"EnergyCommunication=\t( "<<ecbusy<<" , "<<ecidle<<" )\n";
			cout<<"TotalEnergy=\t "<<ebusy + eidle + ecbusy + ecidle<<"\n";
			cout <<"----------------------------------\n";
		}
		else {
			cout << " No solution found " << endl;
		}
		status = cplex.getStatus();
	}
	catch(IloException &e) {
		cerr << "Concert Exception caught: " << e << endl;
	}
	Phase++;
	env.end();
	return stop;
}