package mr;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;
import org.apache.hadoop.util.LineReader;

/**
 * duplicate using input file as multi-files input.
 * 
 * @author root
 * @date 2015-5-4
 */
public class MyFileInputFormat extends FileInputFormat<LongWritable, Text> {

	public static final String READFILETIME = "READFILETIMES";

	public static void setReadFileTime(Job job, int numLines) {
		job.getConfiguration().setInt(READFILETIME, numLines);
	}

	public static int getNumLinesPerSplit(JobContext job) {
		return job.getConfiguration().getInt(READFILETIME, 1);
	}

	@Override
	public RecordReader<LongWritable, Text> createRecordReader(
			InputSplit genericSplit, TaskAttemptContext context)
			throws IOException {

		return new MyFileRecordReader();
	}

	protected boolean isSplitable(JobContext context, Path filename) {
		return false;
	}

	@Override
	public List<InputSplit> getSplits(JobContext job) throws IOException {
		List<InputSplit> splits = new ArrayList<InputSplit>();
		for (FileStatus status : listStatus(job)) {
			splits.addAll(getSplitsForFile(status, job.getConfiguration(),
					getNumLinesPerSplit(job)));
		}
		return splits;
	}

	private Collection<? extends InputSplit> getSplitsForFile(
			FileStatus status, Configuration conf, int readTimeNum)
			throws IOException {
		List<FileSplit> splits = new ArrayList<FileSplit>();
		Path fileName = status.getPath();
		FileSystem fs = fileName.getFileSystem(conf);
		LineReader lr = null;
		try {
			FSDataInputStream in = fs.open(fileName);
			lr = new LineReader(in, conf);
			Text line = new Text();
			long begin = 0;
			long length = 0;
			int num = -1;
			while ((num = lr.readLine(line)) > 0) {
				length += num;

			}
			for (int i = 0; i < readTimeNum; i++)
				splits.add(createFileSplit(fileName, begin, length));
		} finally {
			if (lr != null) {
				lr.close();
			}
		}
		return splits;
	}

	private FileSplit createFileSplit(Path fileName, long begin, long length) {
		return new FileSplit(fileName, begin, length - 1, new String[] {});
	}

}
