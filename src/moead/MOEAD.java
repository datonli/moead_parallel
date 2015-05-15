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
	
	
	public static void main(String[] args) throws IOException{
		
		int popSize = 100;
		int neighbourSize = 20;
		int iterations = 250;
		
		AProblem problem = DTLZ2.getInstance();
		AMOP mop = CMOP.getInstance(popSize,neighbourSize,problem);
//		mop.setProblem(problem);
		mop.initial();
		for(int i = 0 ; i < iterations; i ++)
			mop.updatePop();
		
		String filename = "/home/hadoop/experiment/serial_result/moead_new.txt";
		mop.write2File(filename);
		System.out.println("done!");
		
	}
	
}
