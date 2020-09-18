package com.msg.laza.project.parsers;

import com.msg.laza.project.exception.CustomException;

import java.util.List;

public interface Parser {
    public <T> String parseObject(T object);
    public <T> String parseList(List<T> list);
    public String parseException(CustomException e);
}
