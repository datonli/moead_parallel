package mr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import utilities.StringJoin;

public class ReduceClass extends Reducer<Text, Text, NullWritable, Text> {
	private Text result = new Text();

	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		String value = null;
		String tmp = null;
		double tmpFitnessValue = -1;
		double maxFitnessValue = -1;
		for (Text val : values) {
			tmp = val.toString();
			if (!"111111111".equals(key.toString())) {
				tmpFitnessValue = line2FitnessValue(tmp);
				if (tmpFitnessValue > maxFitnessValue) {
					maxFitnessValue = tmpFitnessValue;
					value = tmp;
				}
			} else {
				List<String> col = new ArrayList<String>();
				col.add("111111111");
				col.add(tmp);
				value = StringJoin.join(" ",col);
			}
		}
		NullWritable nullw = null;
		result.set(value);
		context.write(nullw, result);
	}

	public double line2FitnessValue(String line) {
		String[] lineSplit = line.split(" ");
		String fitnessValueStr = lineSplit[4];
		return Double.parseDouble(fitnessValueStr);
	}
}
