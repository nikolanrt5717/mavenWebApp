package com.msg.laza.project.parsers;

import com.msg.laza.project.exception.CustomException;

import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.List;

public class ParserController {
    private Parser parser;
    private static Parser JSONParser = new JSONParser();
    private static Parser XMlParser = new XMLParser();
    private static Parser CSVParser = new CSVParser();

    public void setParser(HttpServletRequest req) {
        Enumeration headerName = req.getHeaderNames();
        String acceptHeader  = "";

        if(headerName.hasMoreElements()) {
            acceptHeader = req.getHeader(headerName.nextElement().toString());
        }

        if(acceptHeader.equals("application/json") || acceptHeader.equals("*/*"))
            this.parser = JSONParser;
        else  if(acceptHeader.equals("application/xml"))
            this.parser = XMlParser;
        else  if(acceptHeader.equals("text/csv"))
            this.parser = CSVParser;
    }

    public <T> String getParsedObject(T object){
        return parser.parseObject(object);
    }

    public <T> String getParsedList(List<T> list){
        return parser.parseList(list);
    }

    public String getParsedException(CustomException e){
        return parser.parseException(e);
    }
}
