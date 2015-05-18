package mr;

import java.io.IOException;

import moead.MOEAD;
import mop.AMOP;
import mop.CMOP;
import mop.MopData;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import problems.AProblem;
import problems.DTLZ2;
import problems.ZDT1;

public class MoeadMr {

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {

		int popSize = 100;
		int neighbourSize = 20;
		int iterations = 200;
		int readFileTime = 4;
		int loopTime = iterations / readFileTime;
		AProblem problem = ZDT1.getInstance();
		AMOP mop = CMOP.getInstance(popSize, neighbourSize, problem);

		mop.initial();
		// MOEAD.moead(mop,iterations);
		MopData mopData = new MopData(mop);
		String mopStr = mopData.mop2Str();
		HdfsOper hdfsOper = new HdfsOper();
		hdfsOper.rm("/moead/0/");
		hdfsOper.mkdir("/moead/0/");
		hdfsOper.createFile("/moead/0/part-r-00000", mopStr);

		for (int i = 0; i < loopTime; i++) {
			Configuration conf = new Configuration();
			Job job = new Job(conf, "moead mr");
			job.setJarByClass(MoeadMr.class);
			MyFileInputFormat.setReadFileTime(job, readFileTime);
			job.setInputFormatClass(MyFileInputFormat.class);
			job.setOutputFormatClass(TextOutputFormat.class);
			job.setMapperClass(MapClass.class);
			job.setReducerClass(ReduceClass.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
			FileInputFormat.addInputPath(job, new Path(
					"hdfs://localhost:9000/moead/" + i +"/part-r-00000"));
			FileOutputFormat.setOutputPath(job, new Path(
					"hdfs://localhost:9000/moead/" + (i+1)));
			job.waitForCompletion(true);
		}
	}
}
