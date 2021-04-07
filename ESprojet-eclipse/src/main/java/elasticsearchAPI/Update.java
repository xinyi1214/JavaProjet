package elasticsearchAPI;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;


/**
 * Cette classe Update modifier et mise à jour une information par l'id dans un index dans l'ElasticSearch.
 */
public class Update {
	private String index;
	public Update(String index) {
		this.index = index;
	}
	
	/**
	 * Cette méthods mise à jour une information par l'id dans un index dans l'ElasticSearch.
	 * @param id, String
	 * @param jsonMap, Map
	 */
	protected UpdateResponse updateParId(String id, Map<String, Object> jsonMap) throws IOException {
        UpdateRequest request = new UpdateRequest(this.index, id)
                .doc(jsonMap);
        UpdateResponse updateResponse = ESConstants.client.update(request, RequestOptions.DEFAULT);
        return updateResponse;
    }
	
	/**
	 * Cette méthods mise à jour une/des information(s) en recherchant ceux qui sont correspondants
	 * @param queryField, String
	 * @param contenu, String
	 * @param updateField, String
	 * @param updateContenu, String
	 */
	protected BulkByScrollResponse UpdateByQuery(String queryField, String contenu, String updateField, String updateContenu) throws IOException {
		UpdateByQueryRequest request = new UpdateByQueryRequest(this.index);
		MatchQueryBuilder match = new MatchQueryBuilder(queryField, contenu);
		request.setQuery(match);
		String script = String.format("ctx._source.%s = '%s';", updateField, updateContenu);
		request.setScript(new Script(script));
		BulkByScrollResponse bulkResponse =
				ESConstants.client.updateByQuery(request, RequestOptions.DEFAULT);
		return bulkResponse;
	}

}
