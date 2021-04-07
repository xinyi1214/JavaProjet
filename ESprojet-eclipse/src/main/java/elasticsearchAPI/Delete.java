package elasticsearchAPI;

import java.io.IOException;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;

/**
 * Cette classe Delete supprime une information par l'id dans un index dans l'ElasticSearch.
 */
public class Delete {
	private String index;
	public Delete(String index) {
		this.index = index;
	}
	
	/**
	 * Cette méthode est pour supprimer une information par l'id 
	 * @param id, String
	 */
	protected DeleteResponse deleteParId(String id) throws IOException {
        DeleteRequest request = new DeleteRequest(
                this.index,
                id);
        DeleteResponse deleteResponse = ESConstants.client.delete(
                request, RequestOptions.DEFAULT);
        return deleteResponse;
    }
	
	/**
	 * Cette méthode est pour supprimer une(des) information en cherchant une condition correspondante.
	 * @param queryField, String
	 * @param contenu, String
	 */
	protected BulkByScrollResponse deleteParQuery(String queryField, String contenu) throws IOException {
		DeleteByQueryRequest request =
				new DeleteByQueryRequest(this.index); 
		MatchQueryBuilder match = new MatchQueryBuilder(queryField, contenu);
		request.setQuery(match);
		BulkByScrollResponse bulkResponse =
				ESConstants.client.deleteByQuery(request, RequestOptions.DEFAULT);
		return bulkResponse;
		
	}

}
