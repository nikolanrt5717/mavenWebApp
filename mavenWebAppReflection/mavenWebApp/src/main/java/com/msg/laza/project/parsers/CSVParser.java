package com.msg.laza.project.parsers;

import com.msg.laza.project.exception.CustomException;

import java.util.ArrayList;
import java.util.List;

public class CSVParser implements Parser{


    @Override
    public <T> String parseObject(T object) {
        return parseToCSV(object);
    }

    @Override
    public <T> String parseList(List<T> list) {
        return parseToCSV(list);
    }

    @Override
    public String parseException(CustomException e) {
        return parseToCSV(e);
    }

    private static <T> String parseToCSV(T object) {
        String text = object.toString();
        String[] split = text.split("\\{");
        text = split[1];
        text = text.substring(0, text.length() - 1);
        split = text.split(", ");
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        for (String s:split){
            String[] keyValue = s.split("=");
            keys.add(keyValue[0]);
            values.add(keyValue[1]);
        }
        String csvString="";
        for(String s:keys)
            csvString += s+",";
        csvString = csvString.substring(0, csvString.length() - 1);
        csvString+="\n";
        for (String s:values) {
            csvString +=s+",";
        }
        csvString = csvString.substring(0, csvString.length() - 1);
        csvString = csvString.replace("'","");
        return csvString;
    }

    private static <T> String parseToCSV(List<T> list){
        String csvList = "";
        for (int i = 0; i <list.size() ; i++) {
            if(i==0)
                csvList+=parseToCSV(list.get(i))+"\n";
            else {
                String[] keyValue = parseToCSV(list.get(i)).split("\n");
                csvList += keyValue[1]+"\n";
            }
        }
        csvList = csvList.substring(0, csvList.length() - 1);
        return csvList;
    }
}
