package mr;

import java.io.IOException;

import mop.MopData;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class MapClass extends Mapper<Object, Text, Text, Text> {

	static MopData mopData = new MopData();
	Text weightVector = new Text();
	Text indivInfo = new Text();
	
	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		try {
			
			mopData.line2mop(line);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
}
