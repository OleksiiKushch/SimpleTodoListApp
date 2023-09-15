package com.example.simpletodolistapp.mapper;

public interface Mapper<Source, Target> {
    void map(Source source, Target target);
}
