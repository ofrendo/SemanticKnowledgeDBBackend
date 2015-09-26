package main.java;


import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import java.util.HashMap;
import java.util.Map;

import main.java.sparql.SparqlHandler;

public class Main {

  public static void main(String[] args) {
	int port = 5000;
	try {
		port = Integer.valueOf(System.getenv("PORT"));
	}
	catch (Exception e) {}
	  
	port(port);
    staticFileLocation("/public");

    get("/hello", (req, res) -> "Hello World");

    get("/", (request, response) -> {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("message", "Hello World!");
        return "root";
    });
    
    post("/", (request, response) -> {
    	String sparql = request.body();
    	SparqlHandler handler = new SparqlHandler();
    	String result = handler.doQuery(sparql);
    	return result;
    });

  }

}
