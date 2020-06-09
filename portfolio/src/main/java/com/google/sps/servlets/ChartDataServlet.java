package com.google.sps.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/chart-data")
public class ChartDataServlet extends HttpServlet {
    private HashMap<String, Integer> chartVotes = new HashMap<>();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(chartVotes);

        response.setContentType("application/json");
        response.getWriter().println(json);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uglyChoice = request.getParameter("ugly-choice");
        System.out.println(uglyChoice);
        int currentVotes;

        if (chartVotes.containsKey(uglyChoice)) {
            currentVotes = chartVotes.get(uglyChoice);
        } else {
            currentVotes = 0;
        }

        chartVotes.put(uglyChoice, currentVotes + 1);

        response.sendRedirect("/mikey-images.html");
    }
}
