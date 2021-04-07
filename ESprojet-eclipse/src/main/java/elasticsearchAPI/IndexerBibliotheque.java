package elasticsearchAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;

/**
 * Cette classe IndexerBibliotheque indexe un dossier dans la base de données ElasticSearch.
 */
public class IndexerBibliotheque{
	private ArrayList<Livre> livres;
	private String filePath;
	private String index;
	
	

	/**
	 * Constructeur de la classe IndexerBibliotheque.
	 * @param filePath, un string
	 * @param index, un string
	 */
	public IndexerBibliotheque(String filePath, String index) throws IOException {
		this.filePath = filePath;
		this.index = index;
	}
	
	/**
	 * Constructeur de la classe IndexerBibliotheque.
	 * @param index, un string
	 */
	public IndexerBibliotheque(String index) {
		this.index = index;
	}
	
	/**
	 * Cette méthode ajoute les données dans la base de données ElasticSearch
	 */
	protected ArrayList<IndexResponse> addSource() throws IOException {
		Bibliotheque bibliotheque = new Bibliotheque(this.filePath);
		this.livres = bibliotheque.lists;
		ArrayList<IndexResponse> listIndexResponse = new ArrayList<IndexResponse>();
		for (Livre livre : livres) {
			Map<String, Object> jsonMap = new HashMap<>();
			jsonMap.put("titre", livre.getTitre());
			jsonMap.put("auteur", livre.getAuteur());
			jsonMap.put("nb_tomes", livre.getNbTomes());
			jsonMap.put("date_publication", livre.getDatePublication());
			jsonMap.put("resume", livre.getResume());
			if (livre.getLangue() != null) {
				jsonMap.put("langue", livre.getLangue());
			}
			IndexRequest indexRequest = new IndexRequest(this.index).source(jsonMap);
			IndexResponse indexResponse = ESConstants.client.index(indexRequest, RequestOptions.DEFAULT);
//			System.out.println("L'information est ajoutée "+indexResponse);
			listIndexResponse.add(indexResponse);
		}
		return listIndexResponse;
		
		
	}
	
	/**
	 * Cette méthode ajoute les données dans la base de données ElasticSearch
	 * @param id : un string
	 * @param jsonMap : Map<String, Object>
	 * @return 
	 */
	public IndexResponse addinformation(Map<String, Object> jsonMap) throws IOException {
		IndexRequest indexRequest = new IndexRequest(this.index).source(jsonMap);
		IndexResponse indexResponse = ESConstants.client.index(indexRequest, RequestOptions.DEFAULT);
		return indexResponse;
//		System.out.println("L'information est ajoutée " + indexResponse);
	}
}
