package moead;

import java.io.IOException;

import mop.AMOP;
import mop.CMOP;
import problems.AProblem;
import problems.DTLZ1;
import problems.DTLZ2;
import problems.ZDT1;
import problems.ZDT2;
import problems.ZDT3;
import problems.ZDT4;
import problems.ZDT6;

public class MOEAD {
	
	public static void moead(AMOP mop,int iterations){
		mop.initial();
		for(int i = 0 ; i < iterations; i ++)
			mop.updatePop();
	}
	
	
	public static void main(String[] args) throws IOException{
		
		int popSize = 100;
		int neighbourSize = 20;
		int iterations = 200;
		
		AProblem problem = ZDT1.getInstance();
		AMOP mop = CMOP.getInstance(popSize,neighbourSize,problem);
//		mop.setProblem(problem);
		mop.initial();
		long startTime = System.currentTimeMillis();
		for(int i = 0 ; i < iterations; i ++)
			mop.updatePop();
		System.out.println("Running time is : " + (System.currentTimeMillis()-startTime));
		String filename = "/home/hadoop/experiment/serial_result/moead_new.txt";
		mop.write2File(filename);
		System.out.println("done!");
		
	}
	
}
