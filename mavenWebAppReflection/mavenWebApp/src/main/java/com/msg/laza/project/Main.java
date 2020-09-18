package com.msg.laza.project;

import com.msg.laza.project.filter.HttpRequestLoggingFilter;
import com.msg.laza.project.property.PropertiesLoader;
import com.msg.laza.project.servlet.ServletDispatcher;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(PropertiesLoader.getPropertiesLoader().getServerPort());

        Context ctx = tomcat.addContext("/", new File(".").getAbsolutePath());

        Tomcat.addServlet(ctx, "ServletDispatcher", new ServletDispatcher());

        Class filter = HttpRequestLoggingFilter.class;
        FilterDef myFilterDef = new FilterDef();
        myFilterDef.setFilterClass(filter.getName());
        myFilterDef.setFilterName(filter.getSimpleName());
        ctx.addFilterDef(myFilterDef);
        FilterMap myFilterMap = new FilterMap();
        myFilterMap.setFilterName(filter.getSimpleName());
        myFilterMap.addURLPattern("/api/*");
        ctx.addFilterMap(myFilterMap);

        ctx.addServletMappingDecoded("/api/*","ServletDispatcher");

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
        tomcat.getServer().await();
    }
}
