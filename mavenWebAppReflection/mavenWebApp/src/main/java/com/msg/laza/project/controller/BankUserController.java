package com.msg.laza.project.controller;

import com.msg.laza.project.controller.annotation.Controller;
import com.msg.laza.project.controller.annotation.Path;
import com.msg.laza.project.model.BankUser;
import com.msg.laza.project.service.BankUserService;
import com.msg.laza.project.parsers.ParserController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@Controller
public class BankUserController {
    private ParserController parser;
    private BankUserService bankUserService;

    public BankUserController() {
        parser = new ParserController();
        bankUserService = BankUserService.getbankUserService();
    }

    @Path(value = "/api/users", method = "GET")
    public void getUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        String id = req.getParameter("ID");

        parser.setParser(req);

        if(id!=null)
        {
            BankUser u = null;
            u = bankUserService.getUserById(id);
            out.println(parser.getParsedObject(u));
        }else{
            ArrayList<BankUser> users = bankUserService.getAllUsers();
            out.println(parser.getParsedList(users));
        }
    }

    @Path(value = "/api/users", method = "POST")
    public void createUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        parser.setParser(req);
        bankUserService.addUser(req.getParameter("NAME"),
                req.getParameter("SURNAME"),
                req.getParameter("CITY"),
                req.getParameter("BORN"));
        out.println(parser.getParsedObject(bankUserService.getLastInsertedUser()));
    }

    @Path(value = "/api/users", method = "PUT")
    public void updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        parser.setParser(req);
        bankUserService.updateUser(req.getParameter("ID"),
                req.getParameter("SURNAME"),
                req.getParameter("CITY"));
        out.println(parser.getParsedObject(bankUserService.getUserById(req.getParameter("ID"))));
    }

    @Path(value = "/api/users", method = "DELETE")
    public void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        bankUserService.deleteUser(req.getParameter("ID"));
    }
}
