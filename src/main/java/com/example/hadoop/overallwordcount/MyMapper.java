package com.example.hadoop.overallwordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;
import java.util.StringTokenizer;

public class MyMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    public void map(LongWritable longWritable, Text text, OutputCollector<Text, IntWritable> outputCollector, Reporter reporter) throws IOException {

        StringTokenizer stringTokenizer = new StringTokenizer(text.toString());
        int count = stringTokenizer.countTokens();
        IntWritable intWritable = new IntWritable(count);
        Text word = new Text("total-word");
        outputCollector.collect(word, intWritable);
    }
}
