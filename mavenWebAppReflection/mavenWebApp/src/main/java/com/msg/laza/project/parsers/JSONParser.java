package com.msg.laza.project.parsers;

import com.google.gson.Gson;
import com.msg.laza.project.exception.CustomException;

import java.util.List;

public class JSONParser implements Parser{

    @Override
    public <T> String parseObject(T object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    @Override
    public <T> String parseList(List<T> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @Override
    public String parseException(CustomException e) {
        Gson gson = new Gson();
        return gson.toJson(e);
    }
}
