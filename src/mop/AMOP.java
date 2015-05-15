package mop;

import java.io.IOException;
import java.util.List;

import problems.AProblem;

public abstract class AMOP extends MopData{
	
	protected int popSize;
	protected int neighbourSize;
	protected int objectiveDimesion ;
	protected AProblem problem;
	protected static AMOP instance;
	
	protected double[] idealPoint;
	protected List<double[]> weights;
	protected List<int[]> neighbourTable;
	protected List<CMoChromosome> chromosomes;
	
	abstract void initial();
	abstract void generateInitialPop();
	public abstract void updatePop();
	public void setProblem(AProblem problem){
		this.problem = problem;
	}
	public abstract void write2File(String fileName) throws IOException ;
}
