package main.java.sparql;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SparqlHandler {
	
	private static String filePath = "res/karateJSONManual.json";
	private static String prefixes = 
					"PREFIX dc: <http://purl.org/dc/elements/1.1/> " +
					"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
					"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
					"PREFIX xml: <http://www.w3.org/XML/1998/namespace> " +
					"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
					"PREFIX karate: <http://www.heidelberg-karate.de/karate#> ";
	
	private Model model;
	
	public SparqlHandler() {
		this.model = ModelFactory.createDefaultModel();
		model.read(filePath, "JSON-LD");
	}
	
	public String doQuery(String sparql) {
		sparql = prefixes + " " + sparql;
		return doQuery(sparql, model);
	}
	
	@SuppressWarnings("unchecked")
	private String doQuery(String sparql, Model model) {
		JSONArray result = new JSONArray();
		
		Query qry = QueryFactory.create(sparql);
	    QueryExecution qe = QueryExecutionFactory.create(qry, model);
	    ResultSet rs = qe.execSelect();
	    
	    while(rs.hasNext())
	    {
	    	JSONObject row = new JSONObject();
	        QuerySolution sol = rs.nextSolution();
	        for (String var : rs.getResultVars()) {
	        	RDFNode node = sol.get(var);
	        	row.put(var, node.toString());
	        	//System.out.println(var + ": " + node.toString());
	        }
	        result.add(row);
	    }

	    qe.close(); 
	    
	    return result.toJSONString();
	}
	
}
