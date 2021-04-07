package elasticsearchAPI;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * Cette classe ESConstants permet de lier la base de données ElasticSearch par HttpHost.
 */
public class ESConstants {
	public static RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("localhost", 9200, "http")
            ));
	

}
