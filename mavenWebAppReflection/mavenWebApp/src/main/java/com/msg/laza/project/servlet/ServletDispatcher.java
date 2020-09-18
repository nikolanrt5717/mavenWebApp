package com.msg.laza.project.servlet;

import com.msg.laza.project.controller.annotation.Controller;
import com.msg.laza.project.controller.annotation.Path;
import org.reflections.Reflections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@WebServlet("/api/*")
public class ServletDispatcher extends HttpServlet {
    List<Method>methods = new ArrayList<>();
    HashMap<String,ServletTempStruct>methodHashMap = new HashMap<>();

    public static class ServletTempStruct{
        private Object controller;
        private Method method;

        public ServletTempStruct(Object controller, Method method) {
            this.controller = controller;
            this.method = method;
        }

        public Object getController() {
            return controller;
        }

        public Method getMethod() {
            return method;
        }
    }

    @Override
    public void init() throws ServletException {

        Set<Class<?>> controllers = new Reflections("com.msg.laza.project.controller").getTypesAnnotatedWith(Controller.class);
        try {
            for (Class controller:controllers) {
            Object object = controller.newInstance();

                Method[] methods = controller.getDeclaredMethods();
                for (Method method :methods) {
                    if (method.isAnnotationPresent(Path.class)){
                        Path annotation = method.getAnnotation(Path.class);

                        ServletTempStruct servletTempStruct = new ServletTempStruct(object,method);
                        methodHashMap.put(annotation.method()+annotation.value(),servletTempStruct);
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String httpMethod = req.getMethod();
        String uri = req.getRequestURI();

        ServletTempStruct servletTempStruct = methodHashMap.get(httpMethod+uri);
        try {
            servletTempStruct.getMethod().invoke(servletTempStruct.getController(),req,resp);
        } catch (Exception e) {
            resp.setStatus(500);
            resp.flushBuffer();
            e.printStackTrace();
        }
    }
}
