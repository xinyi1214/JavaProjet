package elasticsearchAPI;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.queryparser.flexible.core.builders.QueryBuilder;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * Cette classe Search permet de rechercher des informations dans l'ElasticSearch.
 */
public class Search {
	private String index;

	public Search(String index) {
		this.index = index;
	}
	
	/**
	 * Cette méthode recherche les contenus par field.
	 * @param field, un string
	 * @param contenu, un string
	 */
	public SearchResponse searchParField(String field , String contenu ) throws IOException {
		SearchRequest searchRequest = new SearchRequest(this.index);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(field , contenu);
		searchSourceBuilder.query(matchQueryBuilder);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = ESConstants.client.search(searchRequest, RequestOptions.DEFAULT);
	    SearchHits hits = searchResponse.getHits();
	    TotalHits totalHits = hits.getTotalHits();
	    System.out.println("Total hits : " + totalHits.value);
	    SearchHit[] searchHits = hits.getHits();
	    for (SearchHit hit : searchHits) {
	    	System.out.println("ID : " + hit.getId() + " source : " + hit.getSourceAsString());
	    }
	    return searchResponse;
	    
	}
	
	/**
	 * Cette méthode recherche tous les contenus de l'index et il renvoie les 5 premiers.
	 */
	public SearchHit[] searchAll() throws IOException {
		SearchRequest searchRequest = new SearchRequest(this.index);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchSourceBuilder.size(5);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = ESConstants.client.search(searchRequest, RequestOptions.DEFAULT);
	    
		SearchHits hits = searchResponse.getHits();
	    TotalHits totalHits = hits.getTotalHits();
	    System.out.println("Total hits : " + totalHits.value);
	    SearchHit[] searchHits = hits.getHits();
	    return searchHits;
//	    for (SearchHit hit : searchHits) {
//	    	System.out.println("ID : " + hit.getId() + " source : " + hit.getSourceAsString());
//	    }
	}
	
	/**
	 * Cette méthode permet d'obtenir tous les données par l'id de l'index.
	 */
	public GetResponse getInformationParId(String id) throws IOException {
        GetRequest request = new GetRequest(this.index, id);
        GetResponse response = ESConstants.client.get(request, RequestOptions.DEFAULT);
//        System.out.println(response.toString());
        return response;
	}
}
