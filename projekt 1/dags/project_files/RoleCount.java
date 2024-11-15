package com.example.bigdata;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;
import java.io.*;

public class RoleCount implements WritableComparable<RoleCount> {
    private IntWritable actingCount;
    private IntWritable directingCount;

    public RoleCount() {
        this.actingCount = new IntWritable(0);
        this.directingCount = new IntWritable(0);
    }

    public RoleCount(int actingCount, int directingCount) {
        this.actingCount = new IntWritable(actingCount);
        this.directingCount = new IntWritable(directingCount);
    }

    public IntWritable getActingCount() {
        return actingCount;
    }

    public IntWritable getDirectingCount() {
        return directingCount;
    }

    // Updated set method to accept primitive int types and reuse existing IntWritable objects
    public void set(int actingCount, int directingCount) {
        this.actingCount.set(actingCount);
        this.directingCount.set(directingCount);
    }

    public void add(RoleCount other) {
        this.actingCount.set(this.actingCount.get() + other.getActingCount().get());
        this.directingCount.set(this.directingCount.get() + other.getDirectingCount().get());
    }

    @Override
    public void write(DataOutput out) throws IOException {
        actingCount.write(out);
        directingCount.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        actingCount.readFields(in);
        directingCount.readFields(in);
    }

    @Override
    public int compareTo(RoleCount o) {
        int cmp = actingCount.compareTo(o.actingCount);
        if (cmp != 0) {
            return cmp;
        }
        return directingCount.compareTo(o.directingCount);
    }

    @Override
    public String toString() {
        return actingCount.get() + "\t" + directingCount.get();
    }
}
