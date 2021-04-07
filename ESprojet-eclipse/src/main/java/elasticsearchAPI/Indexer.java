package elasticsearchAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;


/**
 * Cette classe Indexer indexe un dossier dans la base de données ElasticSearch.
 */
public class Indexer {
	private String filePath;
	private Map<String,String> map = new HashMap<String, String>();
	private String index;

	/**
	 * Constructeur de la classe Indexer.
	 * @param filePath, un string
	 * @param index, un string
	 */
	public Indexer(String filePath, String index) throws IOException {
		this.filePath = filePath;
		this.index = index;
		
	}
	
	/**
	 * Constructeur de la classe Indexer.
	 * @param index, un string
	 */
	public Indexer(String index) {
		this.index = index;
	}
	
	
	/**
	 * Cette méthode lit un dossier et traite les fichiers
	 */
	private void readDir() throws IOException {
		File dir = new File(this.filePath);
		File[] files = dir.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					this.readFile(file.getAbsolutePath());
				}
			}
		}
	}
	
	/**
	 * Cette méthode lis un fichier et met ses données dans un HashMap maps
	 * @param filename, un string 
	 */
	private void readFile(String filename) throws IOException {
        FileReader fr=new FileReader(filename);
        BufferedReader br=new BufferedReader(fr);
        String line = "";
        String titre = "";
        if ((line=br.readLine())!=null) {
        	titre = line;
        }
        line = "";
        String texte = "";
        while ((line=br.readLine())!=null) {
        	texte += line;     
        }
        map.put(titre, texte);
        br.close();
        fr.close();
    }
	
	/**
	 * Cette méthode ajoute les données dans la base de données ElasticSearch
	 * @throws IOException 
	 */
	protected ArrayList<IndexResponse> addSource() throws IOException {
		this.readDir();
		ArrayList<IndexResponse> listIndexResponse = new ArrayList<IndexResponse>();
		for (String key : this.map.keySet()) {
			Map<String, Object> jsonMap = new HashMap<>();
			String id_recettes = key.replaceAll(" ", "");
			id_recettes = id_recettes.replaceAll("'", "");
			jsonMap.put("id_recette",id_recettes );
			jsonMap.put("titre_recette", key);
			jsonMap.put("texte_recette", this.map.get(key));
			IndexRequest indexRequest = new IndexRequest(this.index).source(jsonMap);
			IndexResponse indexResponse = ESConstants.client.index(indexRequest, RequestOptions.DEFAULT);
			listIndexResponse.add(indexResponse);
		}
		return listIndexResponse;
		
	}
	
	/**
	 * Cette méthode ajoute les données dans la base de données ElasticSearch
	 * @param id : un string
	 * @param jsonMap : Map<String, Object>
	 */
	public void addinformation( Map<String, Object> jsonMap) throws IOException {
		IndexRequest indexRequest = new IndexRequest(this.index).source(jsonMap);
		IndexResponse indexResponse = ESConstants.client.index(indexRequest, RequestOptions.DEFAULT);
		System.out.println("L'information est ajoutée "+indexResponse);
	}
}
