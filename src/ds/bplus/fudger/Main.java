package ds.bplus.fudger;

import ds.bplus.bptree.BPlusConfiguration;
import ds.bplus.bptree.BPlusTree;
import ds.bplus.bptree.BPlusTreePerformanceCounter;
import ds.bplus.util.InvalidBTreeStateException;
import ds.bplus.util.TestRunner;
import java.io.FileNotFoundException;

import java.io.IOException;

public class Main {

    public static void main(String[] args)
            throws IOException, InvalidBTreeStateException {
        boolean fastTrials = true;
        boolean recreateTree = true;
        BPlusConfiguration btconf128 = new BPlusConfiguration(128, 8, 4);
        BPlusConfiguration btconf256 = new BPlusConfiguration(256, 8, 4);
        BPlusTreePerformanceCounter bPerf;
        BPlusTree bt;
        FileManager fm = new FileManager("keys_1000000_BE.bin");
        int maxKeys = 1000000;
        int nKeys = 100000;
        int[] keys = new int[maxKeys];
        int[] iKeys = new int[100];
        int[] sKeys = new int[100];
        int[] dKeys = new int[100];
        
        for(int i = 0; i < maxKeys; i++) {
        	keys[i] = fm.readInt();
        }
        
        fm = new FileManager("keys_insert_100_BE.bin");
        for(int i = 0; i < 100; i++) {
        	iKeys[i] = fm.readInt();
        }
        
        fm = new FileManager("keys_search_100_BE.bin");
        for(int i = 0; i < 100; i++) {
        	sKeys[i] = fm.readInt();
        }
        
        fm = new FileManager("keys_delete_100_BE.bin");
        for(int i = 0; i < 100; i++) {
        	dKeys[i] = fm.readInt();
        }
        
        ///////////////////////////////////////
        //PageSize = 128bytes
        System.out.println("For PageSize = 128bytes ");
        System.out.println();
        System.out.println();
        for(int j = 0; j < 10; j++) {
        	System.out.println("Iteration for " + (j+1) + "*10^5 keys.");
	        //Insert
	        bPerf = new BPlusTreePerformanceCounter(true);
	        bt = new BPlusTree(btconf128, recreateTree ? "rw+" : "rw", "Itree.bin", bPerf);
	        System.out.println("μπαινω");
	        for(int i = 0; i < (j+1)*nKeys; i++) {
	        	//System.out.println("i = " + i);
	        	bt.insertKey(keys[i], null, true);
	        }
	        System.out.println("βγηκα");
	        bPerf.resetAllMetrics();
	        for(int i = 0; i < 100; i++) {
	        	bPerf.insertIO(keys[i], null, true, false);	
	        }
	        System.out.println("Insertion - Avg accesses : " + (float)(bPerf.getTotalWrites()+ bPerf.getTotalReads())/100);
	        
	        //Search
	        bPerf = new BPlusTreePerformanceCounter(true);
	        bt = new BPlusTree(btconf128, recreateTree ? "rw+" : "rw", "Stree.bin", bPerf);
	        
	        for(int i = 0; i < (j+1)*nKeys; i++) {
	        	bPerf.insertIO(keys[i], null, true, false);
	        }
	        bPerf.resetAllMetrics();
	        for(int i = 0; i < 100; i++) {
	        	bPerf.searchIO(keys[i], true, false);	
	        }
	        System.out.println("Search - Avg accesses: " + (float)(bPerf.getTotalWrites()+ bPerf.getTotalReads())/100);

	        
	        //Delete
	        bPerf = new BPlusTreePerformanceCounter(true);
	        bt = new BPlusTree(btconf128, recreateTree ? "rw+" : "rw", "Dtree.bin", bPerf);
	        
	        for(int i = 0; i < (j+1)*nKeys; i++) {
	        	bPerf.insertIO(keys[i], null, true, false);
	        }
	        bPerf.resetAllMetrics(); 
	       
	        for(int i = 0; i < 100; i++) {
	        	bPerf.deleteIO(keys[i], true, false);	        	
	        }
	        System.out.println("Delete - Avg accesses: " + (float)(bPerf.getTotalWrites()+ bPerf.getTotalReads())/100);
        }
        
	     //////////////////////////////////
	        /////PageSize 256bytes
        System.out.println("For PageSize = 128bytes ");
        System.out.println();
        System.out.println();
	        for(int j = 0; j < 9; j++) {
	        	System.out.println("Iteration for " + (j+1) + "*10^5 keys.");
		        //Insert
		        bPerf = new BPlusTreePerformanceCounter(true);
		        bt = new BPlusTree(btconf256, recreateTree ? "rw+" : "rw", "I2tree.bin", bPerf);
		        
		        for(int i = 0; i < (j+1)*nKeys; i++) {
		        	bt.insertKey(keys[i], null, true);
		        }
		        bPerf.resetAllMetrics();
		        for(int i = 0; i < 100; i++) {
		        	bPerf.insertIO(keys[i], null, true, false);	
		        }
		        System.out.println("Insertion - Avg accesses : " + (float)(bPerf.getTotalWrites()+ bPerf.getTotalReads())/100);
		        
		        //Search
		        bPerf = new BPlusTreePerformanceCounter(true);
		        bt = new BPlusTree(btconf256, recreateTree ? "rw+" : "rw", "S2tree.bin", bPerf);
		        
		        for(int i = 0; i < (j+1)*nKeys; i++) {
		        	bPerf.insertIO(keys[i], null, true, false);
		        }
		        bPerf.resetAllMetrics();
		        for(int i = 0; i < 100; i++) {
		        	bPerf.searchIO(keys[i], true, false);	
		        }
		        System.out.println("Search - Avg accesses: " + (float)(bPerf.getTotalWrites()+ bPerf.getTotalReads())/100);

		        
		        //Delete
		        bPerf = new BPlusTreePerformanceCounter(true);
		        bt = new BPlusTree(btconf256, recreateTree ? "rw+" : "rw", "D2tree.bin", bPerf);
		        
		        for(int i = 0; i < (j+1)*nKeys; i++) {
		        	bPerf.insertIO(keys[i], null, true, false);
		        }
		        bPerf.resetAllMetrics(); 
		       
		        for(int i = 0; i < 100; i++) {
		        	bPerf.deleteIO(keys[i], true, false);	        	
		        }
		        System.out.println("Delete - Avg accesses: " + (float)(bPerf.getTotalWrites()+ bPerf.getTotalReads())/100);
        }


    }

}
