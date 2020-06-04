package com.google.sps.servlets;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.List;
import java.util.ArrayList;

@WebServlet("/delete-data")
public class DeleteServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String movie = request.getParameter("movie");

        Query query = new Query(movie);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery pq = datastore.prepare(query);

        for (Entity entity : pq.asIterable()) {
            datastore.delete(entity.getKey());
        }

        response.sendRedirect("/movies.html");
    }
}