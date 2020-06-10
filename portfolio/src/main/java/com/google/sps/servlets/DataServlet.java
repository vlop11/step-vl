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
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * Servlet that posts comments to datastore and returns 
 * all current posted comments according to movie when called. 
 */
@WebServlet(urlPatterns = "/data")
public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    final int maxComments = Integer.parseInt(request.getParameter("max-comments"));
    final String movie = request.getParameter("movie");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    Query query = new Query(movie).addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery pq = datastore.prepare(query);
    ImmutableList<Entity> resultsList = 
        new ImmutableList.Builder<Entity>().addAll(pq.asList(FetchOptions.Builder.withLimit(maxComments))).build();

    ImmutableList.Builder<HashMap<String, String>> builder = new ImmutableList.Builder<>();
    for (Entity entity : resultsList) {
        HashMap<String, String> entityMap = new HashMap<>();
        entityMap.put("displayName", (String) entity.getProperty("email"));
        entityMap.put("text", (String) entity.getProperty("text"));

        builder.add(entityMap);
    }
    ImmutableList<HashMap<String, String>> comments = builder.build();

    response.setContentType("application/json;");
    response.getWriter().println(convertToJSON(comments));
  }

  private String convertToJSON(ImmutableList<HashMap<String, String>> arr) {
    Gson gson = new Gson();
    
    return gson.toJson(arr);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    final long timestamp = System.currentTimeMillis();
    final String comment = request.getParameter("comment");
    final String movie = request.getParameter("curr-movie");

    UserService userService = UserServiceFactory.getUserService();
    final String userEmail = userService.getCurrentUser().getEmail();

    Entity commentEntity = new Entity(movie);
    commentEntity.setProperty("timestamp", timestamp);
    commentEntity.setProperty("email", userEmail);
    commentEntity.setProperty("text", comment);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);

    response.sendRedirect("/movies.html");
  }
}
