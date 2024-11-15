package com.example.bigdata;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class RoleCountMapper extends Mapper<LongWritable, Text, Text, RoleCount> {
    private Text personId = new Text();
    private RoleCount roleCount = new RoleCount();
    private String[] fields; // Reusable array for splitting lines

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        fields = value.toString().split("\t");

        if (fields.length >= 6) {
            String nconst = fields[2];
            String role = fields[3];

            personId.set(nconst);

            int acting = 0;
            int directing = 0;

            // Use efficient string comparison
            if ("actor".equals(role) || "actress".equals(role) || "self".equals(role)) {
                acting = 1;
            } else if ("director".equals(role)) {
                directing = 1;
            }

            if (acting > 0 || directing > 0) {
                roleCount.set(acting, directing); // Updated to use primitive ints
                context.write(personId, roleCount);
            }
        }
    }
}
