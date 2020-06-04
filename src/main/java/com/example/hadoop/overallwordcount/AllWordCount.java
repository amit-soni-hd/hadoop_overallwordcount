package com.example.hadoop.overallwordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

public class AllWordCount {

    public static void main(String[] args) throws IOException {

        Configuration configuration = new Configuration();
        String[] remainingArgs = new GenericOptionsParser(configuration, args).getRemainingArgs();

        if (remainingArgs.length < 2) {
            System.out.println("please enter the input and output path");
            System.exit(2);
        }

        JobConf entries = new JobConf(configuration, AllWordCount.class);

        entries.setJobName("total-word-count");
        entries.setJarByClass(AllWordCount.class);

        entries.setMapperClass(MyMapper.class);
        entries.setReducerClass(MyReducer.class);

        entries.setMapOutputKeyClass(Text.class);
        entries.setMapOutputValueClass(IntWritable.class);

        entries.setOutputKeyClass(Text.class);
        entries.setOutputValueClass(LongWritable.class);

        FileInputFormat.addInputPath(entries, new Path(remainingArgs[remainingArgs.length - 2]));
        FileOutputFormat.setOutputPath(entries, new Path(remainingArgs[remainingArgs.length - 1]));

        boolean result = JobClient.runJob(entries).isComplete();

        if (result) {
            System.out.println("Successfully complete the job");
        } else {
            System.out.println("Something is wrong");
        }
    }
}
