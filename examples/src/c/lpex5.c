/* --------------------------------------------------------------------------
 * File: lpex5.c
 * Version 12.5
 * --------------------------------------------------------------------------
 * Licensed Materials - Property of IBM
 * 5725-A06 5725-A29 5724-Y48 5724-Y49 5724-Y54 5724-Y55 5655-Y21
 * Copyright IBM Corporation 1997, 2013. All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with
 * IBM Corp.
 * --------------------------------------------------------------------------
 */

/* lpex5.c - Illustrating the CPLEX message handler
   This is a modification of lpex1.c .
   We'll label each channels output with its name, and
   also put our own output into a file.  Note that the labels
   may cause the output to go past 80 characters per line. */

/* Bring in the CPLEX function declarations and the C library 
   header file stdio.h with the following single include. */

#include <ilcplex/cplex.h>

/* Bring in the declarations for the string functions */

#include <string.h>

/* Include declaration for function at end of program */

static int
   populatebycolumn  (CPXENVptr env, CPXLPptr lp);

static void CPXPUBLIC
   ourmsgfunc     (void *handle, const char *message);



/* The problem we are optimizing will have 2 rows, 3 columns 
   and 6 nonzeros.  */

#define NUMROWS    2
#define NUMCOLS    3
#define NUMNZ      6


int
main (void)
{
   char     probname[16];  /* Problem name is max 16 characters */

   /* Declare and allocate space for the variables and arrays where we
      will store the optimization results including the status, objective
      value, variable values, dual values, row slacks and variable
      reduced costs. */

   int      solstat;
   double   objval;
   double   x[NUMCOLS];
   double   pi[NUMROWS];
   double   slack[NUMROWS];
   double   dj[NUMCOLS];


   CPXENVptr     env = NULL;
   CPXLPptr      lp = NULL;
   int           status;
   int           i, j;
   int           cur_numrows, cur_numcols;
   char          errmsg[CPXMESSAGEBUFSIZE];

   CPXCHANNELptr  cpxerror   = NULL;
   CPXCHANNELptr  cpxwarning = NULL;
   CPXCHANNELptr  cpxresults = NULL;
   CPXCHANNELptr  ourchannel = NULL;

   char errorlabel[] = "cpxerror";
   char warnlabel[]  = "cpxwarning";
   char reslabel[]   = "cpxresults";
   char ourlabel[]   = "Our Channel";
   char ourmessage[] = "Our Message";

   CPXFILEptr fpout  = NULL;


   /* Initialize the CPLEX environment */

   env = CPXopenCPLEX (&status);

   /* If an error occurs, the status value indicates the reason for
      failure.  A call to CPXgeterrorstring will produce the text of
      the error message.  Note that CPXopenCPLEX produces no output,
      so the only way to see the cause of the error is to use
      CPXgeterrorstring.  For other CPLEX routines, the errors will
      be seen if the CPX_PARAM_SCRIND indicator is set to CPX_ON.  */

   /* Since the message handler is yet to be set up, we'll call our
      messaging function directly to print out any errors  */

   if ( env == NULL ) {
      ourmsgfunc (ourmessage, "Could not open CPLEX environment.\n");
      goto TERMINATE;
   }

   /* Now get the standard channels.  If an error, just call our
      message function directly. */

   status = CPXgetchannels (env, &cpxresults, &cpxwarning, &cpxerror, NULL);
   if ( status ) {
      ourmsgfunc (ourmessage, "Could not get standard channels.\n");
      CPXgeterrorstring (env, status, errmsg);
      ourmsgfunc (ourmessage, errmsg);
      goto TERMINATE;
   }

   /* Now set up the error channel first.  The label will be "cpxerror" */

   status = CPXaddfuncdest (env, cpxerror, errorlabel, ourmsgfunc);
   if ( status ) {
      ourmsgfunc (ourmessage, "Could not set up error message handler.\n");
      CPXgeterrorstring (env, status, errmsg);
      ourmsgfunc (ourmessage, errmsg);
   }

   /* Now that we have the error message handler set up, all CPLEX
      generated errors will go through ourmsgfunc.  So we don't have
      to use CPXgeterrorstring to determine the text of the message.
      We can also use CPXmsg to do any other printing.  */

   status = CPXaddfuncdest (env, cpxwarning, warnlabel, ourmsgfunc);
   if ( status ) {
      CPXmsg (cpxerror, "Failed to set up handler for cpxwarning.\n");
      goto TERMINATE;
   }

   status = CPXaddfuncdest (env, cpxresults, reslabel, ourmsgfunc);
   if ( status ) {
      CPXmsg (cpxerror, "Failed to set up handler for cpxresults.\n");
      goto TERMINATE;
   }
   
   /* Now turn on the iteration display. */

   status = CPXsetintparam (env, CPX_PARAM_SIMDISPLAY, 2);
   if ( status ) {
      CPXmsg (cpxerror, "Failed to turn on simplex display level.\n");
      goto TERMINATE;
   }

   /* Create the problem. */

   strcpy (probname, "example");
   lp = CPXcreateprob (env, &status, probname);

   /* A returned pointer of NULL may mean that not enough memory
      was available or there was some other problem.  In the case of 
      failure, an error message will have been written to the error 
      channel from inside CPLEX.  In this example, the setting of
      the parameter CPX_PARAM_SCRIND causes the error message to
      appear on stdout.  */

   if ( lp == NULL ) {
      CPXmsg (cpxerror, "Failed to create LP.\n");
      goto TERMINATE;
   }

   /* Now populate the problem with the data. */

   status = populatebycolumn (env, lp);

   if ( status ) {
      CPXmsg (cpxerror, "Failed to populate problem data.\n");
      goto TERMINATE;
   }


   /* Optimize the problem and obtain solution. */

   status = CPXlpopt (env, lp);
   if ( status ) {
      CPXmsg (cpxerror, "Failed to optimize LP.\n");
      goto TERMINATE;
   }

   status = CPXsolution (env, lp, &solstat, &objval, x, pi, slack, dj);
   if ( status ) {
      CPXmsg (cpxerror, "Failed to obtain solution.\n");
      goto TERMINATE;
   }


   /* Write the output to the screen.  We will also write it to a
      file as well by setting up a file destination and a function
      destination. */

   ourchannel = CPXaddchannel (env);
   if ( ourchannel == NULL ) {
      CPXmsg (cpxerror, "Failed to set up our private channel.\n");
      goto TERMINATE;
   }

   fpout = CPXfopen ("lpex5.msg", "w");
   if ( fpout == NULL ) {
      CPXmsg (cpxerror, "Failed to open lpex5.msg file for output.\n");
      goto TERMINATE;
   }
   status = CPXaddfpdest (env, ourchannel, fpout);
   if ( status ) {
      CPXmsg (cpxerror, "Failed to set up output file destination.\n");
      goto TERMINATE;
   }

   status = CPXaddfuncdest (env, ourchannel, ourlabel, ourmsgfunc);
   if ( status ) {
      CPXmsg (cpxerror, "Failed to set up our output function.\n");
      goto TERMINATE;
   }

   /* Now any message to channel ourchannel will go into the file 
      and into the file opened above. */

   CPXmsg (ourchannel, "\nSolution status = %d\n", solstat);
   CPXmsg (ourchannel, "Solution value  = %f\n\n", objval);

   /* The size of the problem should be obtained by asking CPLEX what
      the actual size is, rather than using sizes from when the problem
      was built.  cur_numrows and cur_numcols store the current number 
      of rows and columns, respectively.  */

   cur_numrows = CPXgetnumrows (env, lp);
   cur_numcols = CPXgetnumcols (env, lp);
   for (i = 0; i < cur_numrows; i++) {
      CPXmsg (ourchannel, "Row %d:  Slack = %10f  Pi = %10f\n", 
              i, slack[i], pi[i]);
   }

   for (j = 0; j < cur_numcols; j++) {
      CPXmsg (ourchannel, "Column %d:  Value = %10f  Reduced cost = %10f\n",
              j, x[j], dj[j]);
   }

   /* Finally, write a copy of the problem to a file. */

   status = CPXwriteprob (env, lp, "lpex5.lp", NULL);
   if ( status ) {
      CPXmsg (cpxerror, "Failed to write LP to disk.\n");
      goto TERMINATE;
   }
   
   
TERMINATE:

   /* First check if ourchannel is open */

   if ( ourchannel != NULL ) {
      int  chanstat;
      chanstat = CPXdelfuncdest (env, ourchannel, ourlabel, ourmsgfunc);
      if ( chanstat ) {
         strcpy (errmsg, "CPXdelfuncdest failed.\n");
         ourmsgfunc (ourmessage, errmsg); 
         if (!status)  status = chanstat;
      }
      if ( fpout != NULL ) {
         chanstat = CPXdelfpdest (env, ourchannel, fpout);
         if ( chanstat ) {
            strcpy (errmsg, "CPXdelfpdest failed.\n");
            ourmsgfunc (ourmessage, errmsg);
            if (!status)  status = chanstat;
         }
         CPXfclose (fpout);
      }

      chanstat = CPXdelchannel (env, &ourchannel);
      if ( chanstat ) {
         strcpy (errmsg, "CPXdelchannel failed.\n");
         ourmsgfunc (ourmessage, errmsg); 
         if (!status)  status = chanstat;
      }
   }

   /* Free up the problem as allocated by CPXcreateprob, if necessary */

   if ( lp != NULL ) {
      status = CPXfreeprob (env, &lp);
      if ( status ) {
         strcpy (errmsg, "CPXfreeprob failed.\n");
         ourmsgfunc (ourmessage, errmsg);
      }
   }

   /* Now delete our function destinations from the 3 CPLEX channels. */
   if ( cpxresults != NULL ) {
      int  chanstat;
      chanstat = CPXdelfuncdest (env, cpxresults, reslabel, ourmsgfunc);
      if ( chanstat && !status ) {
         status = chanstat;
         strcpy (errmsg, "Failed to delete cpxresults function.\n");
         ourmsgfunc (ourmessage, errmsg);
      }
   }

   if ( cpxwarning != NULL ) {
      int  chanstat;
      chanstat = CPXdelfuncdest (env, cpxwarning, warnlabel, ourmsgfunc);
      if ( chanstat && !status ) {
         status = chanstat;
         strcpy (errmsg, "Failed to delete cpxwarning function.\n");
         ourmsgfunc (ourmessage, errmsg);
      }
   }

   if ( cpxerror != NULL ) {
      int  chanstat;
      chanstat = CPXdelfuncdest (env, cpxerror, errorlabel, ourmsgfunc);
      if ( chanstat && !status ) {
         status = chanstat;
         strcpy (errmsg, "Failed to delete cpxerror function.\n");
         ourmsgfunc (ourmessage, errmsg);
      }
   }

   /* Free up the CPLEX environment, if necessary */

   if ( env != NULL ) {
      status = CPXcloseCPLEX (&env);

      /* Note that CPXcloseCPLEX produces no output,
         so the only way to see the cause of the error is to use
         CPXgeterrorstring.  For other CPLEX routines, the errors will
         be seen if the CPX_PARAM_SCRIND indicator is set to CPX_ON. */

      if ( status ) {
         strcpy (errmsg, "Could not close CPLEX environment.\n");
         ourmsgfunc (ourmessage, errmsg);
         CPXgeterrorstring (env, status, errmsg);
         ourmsgfunc (ourmessage, errmsg);
      }
   }
     
   return (status);

}  /* END main */


/* This function builds by column the linear program:

      Maximize
       obj: x1 + 2 x2 + 3 x3
      Subject To
       c1: - x1 + x2 + x3 <= 20
       c2: x1 - 3 x2 + x3 <= 30
      Bounds
       0 <= x1 <= 40
      End
 */

static int
populatebycolumn (CPXENVptr env, CPXLPptr lp)
{
   int      status    = 0;
   double   obj[NUMCOLS];
   double   lb[NUMCOLS];
   double   ub[NUMCOLS];
   char     *colname[NUMCOLS];
   int      matbeg[NUMCOLS];
   int      matind[NUMNZ];
   double   matval[NUMNZ];
   double   rhs[NUMROWS];
   char     sense[NUMROWS];
   char     *rowname[NUMROWS];

   /* To build the problem by column, create the rows, and then 
      add the columns. */

   status = CPXchgobjsen (env, lp, CPX_MAX);  /* Problem is maximization */
   if ( status )  goto TERMINATE;

   /* Now create the new rows.  First, populate the arrays. */

   rowname[0] = "c1";
   sense[0]   = 'L';
   rhs[0]     = 20.0;

   rowname[1] = "c2";
   sense[1]   = 'L';
   rhs[1]     = 30.0;

   status = CPXnewrows (env, lp, NUMROWS, rhs, sense, NULL, rowname);
   if ( status )   goto TERMINATE;

   /* Now add the new columns.  First, populate the arrays. */

       obj[0] = 1.0;      obj[1] = 2.0;           obj[2] = 3.0;

    matbeg[0] = 0;     matbeg[1] = 2;          matbeg[2] = 4;
      
    matind[0] = 0;     matind[2] = 0;          matind[4] = 0;
    matval[0] = -1.0;  matval[2] = 1.0;        matval[4] = 1.0;
 
    matind[1] = 1;     matind[3] = 1;          matind[5] = 1;
    matval[1] = 1.0;   matval[3] = -3.0;       matval[5] = 1.0;

        lb[0] = 0.0;       lb[1] = 0.0;           lb[2]  = 0.0;
        ub[0] = 40.0;      ub[1] = CPX_INFBOUND;  ub[2]  = CPX_INFBOUND;

   colname[0] = "x1"; colname[1] = "x2";      colname[2] = "x3";

   status = CPXaddcols (env, lp, NUMCOLS, NUMNZ, obj, matbeg, matind,
                        matval, lb, ub, colname);
   if ( status )  goto TERMINATE;

TERMINATE:

   return (status);

}  /* END populatebycolumn */


/* For our message functions, we will interpret the handle as a pointer
 * to a string, which will be the label for the channel.  We'll put
 * angle brackets <> around the message so its clear what the function is
 * sending to us.  We'll place the newlines that appear at the end of
 * a message after the > bracket.
 */

static void CPXPUBLIC
ourmsgfunc (void *handle, const char *msg)
{
   char const *label = (char const *) handle;
   size_t     lenstr;
   int        flag = 0;
   char       message[CPXMESSAGEBUFSIZE + 2];

   /* Copy message and remove trailing '\n' if any. */
   strcpy(message, msg);
   lenstr = strlen(message);
   if ( message[lenstr-1] == '\n' ) {
      message[lenstr-1] = '\0';
      flag = 1;
   }

   printf ("%-15s: <%s>", label, message);
   if (flag) putchar('\n');

} /* END ourmsgfunc */
