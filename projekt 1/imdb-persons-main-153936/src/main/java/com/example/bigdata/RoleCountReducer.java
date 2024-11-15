package com.example.bigdata;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class RoleCountReducer extends Reducer<Text, RoleCount, Text, Text> {
    private Text result = new Text();

    @Override
    public void reduce(Text key, Iterable<RoleCount> values, Context context) throws IOException, InterruptedException {
        int sumActing = 0;
        int sumDirecting = 0;

        for (RoleCount val : values) {
            sumActing += val.getActingCount().get();
            sumDirecting += val.getDirectingCount().get();
        }

        result.set(sumActing + "\t" + sumDirecting);
        context.write(key, result);
    }
}
