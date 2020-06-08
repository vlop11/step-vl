package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<String> responseList = new ArrayList<>();

        UserService userService = UserServiceFactory.getUserService();

        if (userService.isUserLoggedIn()) {
            responseList.add("true");

            String logoutUrl = userService.createLogoutURL("/movies.html");
            responseList.add(logoutUrl);
        } else {
            responseList.add("false");

            String loginUrl = userService.createLoginURL("/login");
            responseList.add(loginUrl);
        }

        response.setContentType("application/json;");
        response.getWriter().println(convertToJSON(responseList));
    }

    private String convertToJSON(ArrayList<String> arr) {
        Gson gson = new Gson();
        
        return gson.toJson(arr);
    }
}