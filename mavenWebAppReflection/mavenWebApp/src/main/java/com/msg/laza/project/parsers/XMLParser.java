package com.msg.laza.project.parsers;

import com.msg.laza.project.model.Account;
import com.msg.laza.project.model.BankUser;
import com.thoughtworks.xstream.XStream;
import com.msg.laza.project.exception.CustomException;

import java.util.List;

public class XMLParser implements Parser{

    @Override
    public <T> String parseObject(T object) {
        XStream xStream = new XStream();
        xStream.alias("BankUser", BankUser.class);
        xStream.alias("Account", Account.class);
        return xStream.toXML(object);
    }

    @Override
    public <T> String parseList(List<T> list) {
        XStream xStream = new XStream();
        xStream.alias("BankUser", BankUser.class);
        xStream.alias("Account", Account.class);
        return xStream.toXML(list);
    }

    @Override
    public String parseException(CustomException e) {
        XStream xStream = new XStream();
        xStream.alias("Exception", CustomException.class);
        return xStream.toXML(e);
    }
}
