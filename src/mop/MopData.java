package mop;

import java.util.ArrayList;
import java.util.List;

import utilities.StringJoin;
import utilities.WrongRemindException;

public class MopData implements DataOperator{

	public AMOP mop ;
	
	public MopData()  {
		try {
			mop = (AMOP)CMOP.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mop.clearAll();
		mop.allocateAll();
	}
	
	@Override
	public String mop2Line(int i) {
		List<String> col = new ArrayList<String>();
		col.add(StringJoin.join(",",mop.weights.get(i)));
		col.add(StringJoin.join(",",mop.chromosomes.get(i).genes));
		col.add(StringJoin.join(",",mop.chromosomes.get(i).objectiveValue));
		col.add(StringJoin.join(",",mop.neighbourTable.get(i)));
		return StringJoin.join(" ",col);
	}

	@Override
	public void line2mop(String line) throws WrongRemindException {
		String[] lineSplit = line.split(" ");
		if("111111111".equals(lineSplit[0])){
			String[] idealPoint = lineSplit[1].split(",");
			if(idealPoint.length != mop.idealPoint.length){
				throw new WrongRemindException("idealPoint length isn't match. Data transfer error!");
			}
			for(int i = 0; i < idealPoint.length; i ++){
				mop.idealPoint[i] = Double.parseDouble(idealPoint[i]);
			}
		}
		else{
			String[] weightStr = lineSplit[1].split(",");
			double[] weight = new double[mop.objectiveDimesion];
			if(weightStr.length != weight.length){
				throw new WrongRemindException("weight length isn't match. Data transfer error!");
			}
			for(int i = 0; i < weightStr.length; i ++){
				weight[i] = Double.parseDouble(weightStr[i]);
			}
			mop.weights.add(weight);
			
			
			String[] chromosomeStr = lineSplit[1].split(",");
			String[] objectiveValueStr = lineSplit[2].split(",");
			MoChromosome chromosome = CMoChromosome.createChromosome();
			if(chromosomeStr.length != chromosome.genes.length ){
				throw new WrongRemindException("chromosome length isn't match. Data transfer error!");
			}
			if(objectiveValueStr.length != chromosome.objectiveValue.length ){
				throw new WrongRemindException("objectiveValue length isn't match. Data transfer error!");
			}
			for(int i = 0; i < chromosomeStr.length; i ++){
				chromosome.genes[i] = Double.parseDouble(chromosomeStr[i]);
			}
			for(int i = 0; i < objectiveValueStr.length; i ++){
				chromosome.objectiveValue[i] =  Double.parseDouble(objectiveValueStr[i]);
			}
			mop.chromosomes.add(chromosome);
			
			String[] neighbourStr = lineSplit[3].split(",");
			int[] neighbour = new int[mop.neighbourSize];
			if(neighbourStr.length != neighbour.length){
				throw new WrongRemindException("neighbour length isn't match. Data transfer error!");
			}
			for(int i = 0; i < neighbourStr.length; i ++){
				neighbour[i] = Integer.parseInt(neighbourStr[i]);
			}
			mop.neighbourTable.add(neighbour);
			
		}
		
	}
	
}
