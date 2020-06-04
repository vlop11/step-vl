// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.List;
import java.util.ArrayList;


/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    int maxComments = Integer.parseInt(request.getParameter("max-comments"));
    int movie = Integer.parseInt(request.getParameter("movie"));

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    Filter movieFilter = new FilterPredicate("movie", FilterOperator.EQUAL, movie);
    Query query = new Query("Comment").setFilter(movieFilter).addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery pq = datastore.prepare(query);
    List<Entity> resultsList = pq.asList(FetchOptions.Builder.withLimit(maxComments));

    ArrayList<String> comments = new ArrayList<>();
    for (Entity entity : resultsList) {
        String text = (String) entity.getProperty("text");

        comments.add(text);
    }

    response.setContentType("application/json;");
    response.getWriter().println(convertToJSON(comments));
  }

  private String convertToJSON(ArrayList<String> arr) {
    Gson gson = new Gson();
    String json = gson.toJson(arr);
    
    return json;
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    long timestamp = System.currentTimeMillis();
    String comment = this.getComment(request);
    int movie = Integer.parseInt(request.getParameter("movie"));

    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("timestamp", timestamp);
    commentEntity.setProperty("text", comment);
    commentEntity.setProperty("movie", movie);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);

    response.sendRedirect("/movies.html");
  }

  private String getComment(HttpServletRequest request) {
    String comment = request.getParameter("comment");

    return comment;
  }
}
