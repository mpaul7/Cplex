
public class GetResourceDescription_v2 {
	public GetResourceDescription_v2(String fileName){
		InputDataWrite_v3 writer = new InputDataWrite_v3(fileName);
		energyLocal(writer);
		energyRemote(writer);
		rhoN(writer);
		rhoMN(writer);
		latencyLocal(writer);
		latencyRemote(writer);
	}
	
	public void energyLocal(InputDataWrite_v3 writer){
		int [] writeIntArray = {1, 2};
		writer.writeIntArray(writeIntArray);
		}
	
	public void energyRemote(InputDataWrite_v3 writer){
		int [][] writeIntArrayArray = {{9, 4}, {2, 9}, {8,8}};
		writer.writeIntArrayArray(writeIntArrayArray);
		}
	
	public void rhoN(InputDataWrite_v3 writer){
		int [] writeIntArray = {5, 7, 6};
		writer.writeIntArray(writeIntArray);
		}

	public void rhoMN(InputDataWrite_v3 writer){
		int [][] writeIntArrayArray = {{5, 4}, {6, 4}, {9, 3}};
		writer.writeIntArrayArray(writeIntArrayArray);
		}
	
	public void latencyLocal(InputDataWrite_v3 writer){
		int [] writeIntArray = {1, 1};
		writer.writeIntArray(writeIntArray);
		}
	
	public void latencyRemote(InputDataWrite_v3 writer){
		int [][] writeIntArrayArray = {{5, 4}, {6, 8}, {1, 1}};
		writer.writeIntArrayArray(writeIntArrayArray);
		}


}
