package mr;

import java.io.IOException;

import moead.MOEAD;
import mop.MopData;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;

import utilities.WrongRemindException;

public class MapClass extends Mapper<Object, Text, Text, Text> {

	private static int innerLoop = 1;
	
	static MopData mopData = new MopData();
	Text weightVector = new Text();
	Text indivInfo = new Text();

	public static void setInnerLoop(int innerLoopTime){
		innerLoop = innerLoopTime;
	}
	
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {
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
			context.write(weightVector, indivInfo);

			for (int i = 0; i < mopData.mop.chromosomes.size(); i++) {
				weightVector.set(mopData.weight2Line(i));
				indivInfo.set(mopData.mop2Line(i));
				context.write(weightVector, indivInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
