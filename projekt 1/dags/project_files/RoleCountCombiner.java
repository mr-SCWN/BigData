package com.example.bigdata;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class RoleCountCombiner extends Reducer<Text, RoleCount, Text, RoleCount> {
    private RoleCount result = new RoleCount();

    @Override
    public void reduce(Text key, Iterable<RoleCount> values, Context context) throws IOException, InterruptedException {
        int sumActing = 0;
        int sumDirecting = 0;

        for (RoleCount val : values) {
            sumActing += val.getActingCount().get();
            sumDirecting += val.getDirectingCount().get();
        }

        result.set(sumActing, sumDirecting); // Updated to use primitive ints
        context.write(key, result);
    }
}
