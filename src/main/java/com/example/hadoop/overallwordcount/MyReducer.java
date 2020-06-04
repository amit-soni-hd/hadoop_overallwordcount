package com.example.hadoop.overallwordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

public class MyReducer extends MapReduceBase implements Reducer<Text, IntWritable, Text, LongWritable> {

    @Override
    public void reduce(Text text, Iterator<IntWritable> iterator, OutputCollector<Text, LongWritable> outputCollector, Reporter reporter) throws IOException {
        AtomicLong total_word = new AtomicLong();
        iterator.forEachRemaining((value) -> {
            total_word.addAndGet(value.get());
        });
        LongWritable longWritable = new LongWritable(total_word.longValue());
        outputCollector.collect(text, longWritable);
    }
}
