package mr;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import moead.MOEAD;
import mop.AMOP;
import mop.CMOP;
import mop.MopData;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import problems.AProblem;
import problems.DTLZ2;
import problems.ZDT1;
import utilities.StringJoin;

public class MoeadMr {

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {

		int popSize = 406;
		int neighbourSize = 30;
		int iterations = 800;
//		int popSize = 4000;
//		int neighbourSize = 400;
//		int iterations = 6000;
		int readFileTime = 4;
		int innerLoop = 20;
		int loopTime = iterations / (readFileTime * innerLoop);
		AProblem problem = ZDT1.getInstance();
		AMOP mop = CMOP.getInstance(popSize, neighbourSize, problem);

		mop.initial();
		// MOEAD.moead(mop,iterations);
		MopData mopData = new MopData(mop);
		String mopStr = mopData.mop2Str();
		HdfsOper hdfsOper = new HdfsOper();
		for(int i = 0; i < iterations + 1; i ++)
			hdfsOper.rm("moead/" + i + "/");
		hdfsOper.mkdir("moead/0/");
		hdfsOper.createFile("moead/0/part-r-00000", mopStr);

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < loopTime; i++) {

			JobConf conf = new JobConf(MoeadMr.class);
			conf.setJobName("moead mapreduce");
			

			conf.setJarByClass(MoeadMr.class);
			MapClass.setInnerLoop(innerLoop);
			//MyFileInputFormat.setReadFileTime(conf,readFileTime);
			//conf.setInputFormat(MyFileInputFormat.class);
			conf.setInputFormat(NLineFileInputFomat.class);
			conf.setOutputFormat(TextOutputFormat.class);
			conf.setMapperClass(MapClass.class);
			conf.setReducerClass(ReduceClass.class);
			conf.setOutputKeyClass(Text.class);
			conf.setOutputValueClass(Text.class);
			FileInputFormat.setInputPaths(conf,new Path(
					"hdfs://192.168.1.102:9000/user/root/moead/"
					//"hdfs://localhost:9000/moead/" 
					+ i +"/part-r-00000"));
			FileOutputFormat.setOutputPath(conf,new Path(
					"hdfs://192.168.1.102:9000/user/root/moead/"
					//"hdfs://localhost:9000/moead/" 
					+ (i+1)));
			JobClient.runJob(conf);
		}
		System.out.println("Running time is : " + (System.currentTimeMillis() - startTime));
		for (int i = 0; i < loopTime + 1; i++) {
			BufferedReader br = new BufferedReader(hdfsOper.open("moead/" + i + "/part-r-00000"));
			String line = null;
			String content = null;
			List<String> col = new ArrayList<String>();
			while ((line = br.readLine()) != null && line.split(" ").length > 2) {
				col.add(StringJoin.join(" ",mopData.line2ObjValue(line)));
			}
			content = StringJoin.join("\n", col);
			mopData.write2File("/home/laboratory/experiments/" + i + ".txt",content);
//			hdfsOper.createFile("/moead/" + i + "/objectiveValue.txt", content);
		}
		System.out.println("LoopTime is : " + loopTime + "\n");
	}
}
