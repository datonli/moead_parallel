 javac -cp /home/laboratory/hadoop-1.2.1/hadoop-core-1.2.1.jar:/home/laboratory/hadoop-1.2.1/lib/commons-cli-1.2.jar:../apache-commons-lang.jar:/home/laboratory/hadoop-1.2.1/lib/commons-logging-1.1.1.jar:../commons-math-2.2.jar:./ ./mr/HdfsOper.java -d .
 jar -cvf HdfsOper.jar .
 hadoop jar HdfsOper.jar mr.HdfsOper
