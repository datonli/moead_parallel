package mr;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class MoeadMr {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = new Job(conf, "moead mr");
		job.setJarByClass(MoeadMr.class);
		MyFileInputFormat.setReadFileTime(job,4);
		job.setInputFormatClass(MyFileInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class); 
		job.setMapperClass(MapClass.class);
		job.setReducerClass(ReduceClass.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path("hdfs://localhost:9000/inputFormatTest/test.txt"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://localhost:9000/inputFormatTest/output/"));
		job.waitForCompletion(true) ;
	}
}
