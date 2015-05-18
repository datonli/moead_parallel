package mr;

import java.io.IOException;

import moead.MOEAD;
import mop.MopData;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MapClass extends Mapper<Object, Text, Text, Text> {

	static MopData mopData = new MopData();
	Text weightVector = new Text();
	Text indivInfo = new Text();

	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {
		String paragraph = value.toString();
		String[] lines = paragraph.split("\n");
		mopData.clear();
		try {
			for (String line : lines)
				mopData.line2mop(line);

			
//			running moead algorithm
			MOEAD.moead(mopData.mop,5);
			
			weightVector.set("111111111");
			indivInfo.set(mopData.idealPoint2Line());
			context.write(weightVector, indivInfo);

			for (int i = 0; i < mopData.mop.weights.size(); i++) {
				weightVector.set(mopData.weight2Line(i));
				indivInfo.set(mopData.mop2Line(i));
				context.write(weightVector, indivInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
