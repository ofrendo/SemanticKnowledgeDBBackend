package main;

import static spark.Spark.get;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import java.util.HashMap;
import java.util.Map;

public class Main {

  public static void main(String[] args) {

    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");

    get("/hello", (req, res) -> "Hello World");

    get("/", (request, response) -> {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("message", "Hello World!");
        return "root";
    });

  }

}
