package mr;

import java.io.IOException;

import moead.MOEAD;
import mop.MopData;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import utilities.WrongRemindException;

public class MapClass extends MapReduceBase implements Mapper<Object, Text, Text, Text> {

	private static int innerLoop = 1;
	
	static MopData mopData = new MopData();
	Text weightVector = new Text();
	Text indivInfo = new Text();

	public static void setInnerLoop(int innerLoopTime){
		innerLoop = innerLoopTime;
	}
	
	public void map(Object key, Text value, OutputCollector<Text, Text> output, Reporter reporter) 
			throws IOException{
		String paragraph = value.toString();
		String[] lines = paragraph.split("\n");
		mopData.clear();
		try {
			if("".equals(lines)){
				throw new WrongRemindException("Empty lines");
			}
			for (String line : lines)
				mopData.line2mop(line);
			
//			running moead algorithm
			MOEAD.moead(mopData.mop,1);
			
			weightVector.set("111111111");
			indivInfo.set(mopData.idealPoint2Line());
			output.collect(weightVector, indivInfo);

			for (int i = 0; i < mopData.mop.chromosomes.size(); i++) {
				weightVector.set(mopData.weight2Line(i));
				indivInfo.set(mopData.mop2Line(i));
				output.collect(weightVector, indivInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
