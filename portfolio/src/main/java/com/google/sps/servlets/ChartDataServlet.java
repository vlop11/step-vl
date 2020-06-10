package com.google.sps.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that takes user votes and tallies them according to vote category.
 * This is later visualized into a chart of the votes. 
 */
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
        final String uglyChoice = request.getParameter("ugly-choice");
        int currentVotes = 0;

        if (chartVotes.containsKey(uglyChoice)) {
            currentVotes = chartVotes.get(uglyChoice);
        }

        chartVotes.put(uglyChoice, currentVotes + 1);

        response.sendRedirect("/mikey-images.html");
    }
}
