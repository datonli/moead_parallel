package mr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import utilities.StringJoin;

public class ReduceClass extends MapReduceBase implements Reducer<Text, Text, NullWritable, Text> {
	private Text result = new Text();

	public void reduce(Text key, Iterator<Text> values, OutputCollector<NullWritable, Text> output, Reporter reporter)
			throws IOException{
		String value = null;
		String tmp = null;
		double tmpFitnessValue = 10;
		double maxFitnessValue = 10;
		double[] idealPointValue = {10,10};
		boolean flag = false;
		while(values.hasNext()){
			tmp = values.next().toString();
			if (!"111111111".equals(key.toString())) {
				tmpFitnessValue = line2FitnessValue(tmp);
				if (tmpFitnessValue < maxFitnessValue) {
					maxFitnessValue = tmpFitnessValue;
					value = tmp;
				}
			} else {
				String[] tmpString = tmp.split(",");
				for(int i = 0 ; i < tmpString.length; i ++) {
					if(idealPointValue[i] > Double.parseDouble(tmpString[i]) )
									idealPointValue[i] = Double.parseDouble(tmpString[i]);
				}
				flag = true;
			}
		}
		if(flag) value ="111111111 " +  StringJoin.join(",",idealPointValue);
		NullWritable nullw = null;
		result.set(value);
		output.collect(nullw, result);
	}

	public double line2FitnessValue(String line) {
		String[] lineSplit = line.split(" ");
		String fitnessValueStr = lineSplit[4];
		return Double.parseDouble(fitnessValueStr);
	}
}
