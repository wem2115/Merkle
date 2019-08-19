import java.security.MessageDigest;


class Merkle {
    public static void main(String[] args) {
	    
	   int n = 10; //change this value to generate a tree with a different number of nodes
	    int numfiles = (int)(Math.pow(2, n));
	    int height = (int)(Math.log(numfiles) / Math.log(2));
	    int numnodes = (int)(Math.pow(2, n+1))-1;
	    Object [] files = new Object[numfiles]; 
	    for(int i = 0; i < numfiles; i++){  //fill the n files with letters
		    files[i] = "a";
	    }
	       Object [] MerkleTree = new Object[(int)(Math.pow(2, n+1))-1];
	       MerkleTree = setup(files);
	       System.out.print("Tree created with ");
	       System.out.print(numnodes);
	        System.out.println(" nodes");
		       
	       int index = 4;
	       Object [] ProveList = new Object[height];
	       ProveList = prove(index, MerkleTree);
	       System.out.println("Prove List Generated!");
	      int accept = 0;
	      String file = "a";
	      accept = verify(file, ProveList, MerkleTree[0].toString());
	      if (accept == 1) {
		   System.out.println("New File Accepted!");
	      }
	      else {
		      System.out.println("New File Rejected!");
	      }
	      String UpdateFile = "fff";
	      Object [] NewMerkleTree = new Object[(int)(Math.pow(2, n+1))-1];
	      NewMerkleTree = update(UpdateFile, MerkleTree);
	      System.out.println("New Tree Generated!");
    }
	    
    
    public static Object [] setup(Object [] files){
	     int n = 10;
	     int numfiles = (int)(Math.pow(2, n));
	     
	    int height = (int)(Math.log(numfiles) / Math.log(2));
	     int numnodes = (int)(Math.pow(2, n+1))-1;
	     int curheight = height;
	     Object [] MerkleTree = new Object[(int)(Math.pow(2, n+1))-1];
	    
	    for (int i = 0; i < numfiles; i++){
		   MerkleTree[numnodes-numfiles+i] = sha256(files[i].toString()); //fill the root nodes of the tree with the hashes of the files themselves
	    }
	    for(int k=height-1;k>=0;k--){//iterates over the height, each level 
		    for(int t=(int)Math.pow(2,k)-1;t<=2*((int)Math.pow(2,k)-1);t++){    //computes hash string for each node at height level 
	
	
       	 MerkleTree[t]=sha256((MerkleTree[2*t+1]).toString()+(MerkleTree[2*t+2]).toString()); 
	} 
	    }
		return MerkleTree;
    }
    
   public static Object [] prove(int i, Object [] MerkleTree){
    		int n = 10;
		int numfiles = (int)(Math.pow(2, n));
		int height = (int)(Math.log(numfiles) / Math.log(2));
	    Object [] ProveList = new Object[height];
	    	int nextparent = i;
	    for (int j = i; j > 0; j--){
	    nextparent = (nextparent - 1) / 2;
		    if(j == nextparent){
		    ProveList[j] = MerkleTree[j];
		    }
	    }
	    return ProveList;
	    }
	    
	    
	    
	    public static int verify(String file, Object [] ProveList, String RootHash){
		    int n = 10;
		    int numfiles = (int)(Math.pow(2, n));
		    //check if the file is the correct file by comparing it to hashes along the provelist
		    int height = (int)(Math.log(numfiles) / Math.log(2));
		    if(ProveList[height - 1] == RootHash){
		     return 1;
		    }
		    else {
			    return 0;
		    }
	    }
	    
	    public static Object [] update(String file, Object [] MerkleTree){
		    int n = 10;
		    int index = (int)(Math.pow(2, n+1))-10;  //where to place the new file
		    Object [] NewMerkleTree = new Object[(int)(Math.pow(2, n+1))-1];
		    NewMerkleTree = MerkleTree;
		    NewMerkleTree[index] = sha256(file);
		    
		    return NewMerkleTree;
	    }
	    

public static String sha256(String base) { //this is the method that hashes the input string in SHA-256
try{ 
	MessageDigest digest = MessageDigest.getInstance("SHA-256"); 
	byte[] hash = digest.digest(base.getBytes("UTF-8")); 
	StringBuffer hexString = new StringBuffer();

	for (int i = 0; i < hash.length; i++) { 
		String hex = Integer.toHexString(0xff & hash[i]); 
		if(hex.length() == 1) hexString.append('0'); 
		hexString.append(hex); 
	}

	return hexString.toString(); 
} 
catch(Exception ex){ 
	throw new RuntimeException(ex); 
} 
}//end sha256 method thingy

}





