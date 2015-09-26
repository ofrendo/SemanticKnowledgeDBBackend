package main;

import java.io.IOException;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileUtils;

public class TestJena {
	
	private static String filePath = "res/karateJSONManual.json";
	private static String prefixes = 
					"PREFIX dc: <http://purl.org/dc/elements/1.1/> " +
					"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
					"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
					"PREFIX xml: <http://www.w3.org/XML/1998/namespace> " +
					"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
					"PREFIX karate: <http://www.heidelberg-karate.de/karate#> ";
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Test");
		
		Model model = ModelFactory.createDefaultModel();
		model.read(filePath, "JSON-LD");
		//QueryExecutionFactory.create
		
		String sparql = prefixes + 
			" SELECT * WHERE { " +
			" 	?subject a karate:kategorie . " +
			" 	?subject rdfs:label ?label ." + 
			" 	?subject rdfs:comment ?comment ." + 
			" } ";
		doQuery(sparql, model);
		
		sparql = prefixes +
				" SELECT * WHERE { " + 
				"	karate:heianShodan karate:abfolge/rdf:rest*/rdf:first ?item . " + 
				" }";
		doQuery(sparql, model);
	}
	
	private static void doQuery(String sparql, Model model) {
		Query qry = QueryFactory.create(sparql);
	    QueryExecution qe = QueryExecutionFactory.create(qry, model);
	    ResultSet rs = qe.execSelect();

	    while(rs.hasNext())
	    {
	        QuerySolution sol = rs.nextSolution();
	        System.out.println(sol);
	    }

	    qe.close(); 
	}
	
	private String getJSONLD() {
		String result = "{}";
		try {
			result = FileUtils.readWholeFileAsUTF8(filePath);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
