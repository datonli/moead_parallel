javac -cp /home/laboratory/hadoop-1.2.1/hadoop-core-1.2.1.jar:/home/laboratory/hadoop-1.2.1/lib/commons-cli-1.2.jar WordCount.java -d .

jar -cvf WordCount.java org

doop jar WordCount.jar org.apache.hadoop.examples.WordCount input output
