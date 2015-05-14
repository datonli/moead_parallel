package mop;

import java.io.IOException;

import problems.AProblem;

public abstract class AMOP {
	
	protected int popSize;
	protected int neighbourSize;
	protected int objectiveDimesion ;
	protected AProblem problem;
	protected static AMOP instance;
	
	abstract void initial();
	abstract void generateInitialPop();
	public abstract void updatePop();
	public void setProblem(AProblem problem){
		this.problem = problem;
	}
	public abstract void write2File(String fileName) throws IOException ;
}
