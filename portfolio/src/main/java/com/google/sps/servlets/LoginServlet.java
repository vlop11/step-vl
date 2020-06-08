package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap<String, String> responseMap = new HashMap<>();

        UserService userService = UserServiceFactory.getUserService();

        if (userService.isUserLoggedIn()) {
            responseMap.put("Status", "true");

            String logoutUrl = userService.createLogoutURL("/movies.html");
            String userEmail = userService.getCurrentUser().getEmail();

            responseMap.put("Link", logoutUrl);
        } else {
            responseMap.put("Status", "false");

            String loginUrl = userService.createLoginURL("/movies.html");
            responseMap.put("Link", loginUrl);
        }

        response.setContentType("application/json;");
        response.getWriter().println(convertToJSON(responseMap));
    }

    private String convertToJSON(HashMap<String, String> map) {
        Gson gson = new Gson();
        
        return gson.toJson(map);
    }
}