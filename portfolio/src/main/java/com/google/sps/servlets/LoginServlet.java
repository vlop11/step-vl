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

/** 
 * Servlet that checks whether or not the current user is 
 * logged into their Google account. If the user is logged
 * in, it provides a logout link. If the user is not logged
 * in, it provides a login link. 
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap<String, String> responseMap = new HashMap<>();

        UserService userService = UserServiceFactory.getUserService();

        if (userService.isUserLoggedIn()) {
            final String logoutUrl = userService.createLogoutURL("/movies.html");
            
            responseMap.put("Status", "true");
            responseMap.put("Link", logoutUrl);
        } else {
            final String loginUrl = userService.createLoginURL("/movies.html");
            
            responseMap.put("Status", "false");
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
