package com.example.bigdata;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
// Added necessary import statements
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.*;

public class RoleCountDriver extends Configured implements Tool {
    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new RoleCountDriver(), args);
        System.exit(res);
    }

    @Override
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(getConf(), "Role Count");
        job.setJarByClass(this.getClass());

        // Set Mapper, Combiner, and Reducer classes
        job.setMapperClass(RoleCountMapper.class);
        job.setCombinerClass(RoleCountCombiner.class);
        job.setReducerClass(RoleCountReducer.class);

        // Set output key and value classes
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(RoleCount.class);

        // Set number of reducers to 1 to produce a single output file
        job.setNumReduceTasks(1);

        // Set input and output paths from command line arguments
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // Wait for completion and return status
        return job.waitForCompletion(true) ? 0 : 1;
    }
}
