package elasticsearchAPI;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.bulk.BulkItemResponse.Failure;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;



public class TestJunit {
	
	/*
	 * il faut modifier le chemin du filePath 
	 * il faut modifier le chemin du fileXML
	 */
	static final String filePath = "/Users/shenxinyi/Documents/java/ESprojet-eclipse/data/recettes";
	static final String fileXml = "/Users/shenxinyi/Documents/java/ESprojet-eclipse/data/bibliotheque.xml";
	
	/*
	 * testIndexer() est la méthode pour tester l'indexation des données
	 */
	@Test
	void testIndexer() throws IOException, InterruptedException {
		Indexer indexTest = new Indexer(filePath, "recettes");
		ArrayList<IndexResponse> listIndexResponse = new ArrayList<IndexResponse>();
		listIndexResponse = indexTest.addSource();

		for (IndexResponse indexresponse : listIndexResponse) {
			int success = indexresponse.getShardInfo().getSuccessful();
			assertTrue("This will succeed.", success==1);
		}
		
	}
	
	/*
	 * testSearch() est la méthode pour tester la recherche des données
	 */
	@Test
	void testSearch() throws IOException {
		Search search = new Search("recettes");
		SearchResponse searchresponse = search.searchParField("titre_recette", "BEIGNETS");
		TotalHits total = searchresponse.getHits().getTotalHits();
		assertEquals(9,total.value);	
	}
	
	/*
	 * testUpdate() est la méthode pour tester la mise à jour des données
	 */
	@Test
	void testUpdate() throws IOException {
		/*
		 * il faut changer un id qui existe dans votre base de données de elasticearch
		 */
		String id = "mVWyqHgBTVU9zBAsKfOR";
		
		Update updateRecettes = new Update("recettes");
		Map<String, Object> jsonMapRecette = new HashMap<>();
		
		jsonMapRecette.put("titre_recette", "BEIGNETS");
		jsonMapRecette.put("texte_recette", "Apple and lemon");
		UpdateResponse updateresponse = updateRecettes.updateParId(id, jsonMapRecette);
		
		int success = updateresponse.getShardInfo().getSuccessful();
		assertTrue(success==1);
	}
	
	/*
	 * testUpdateByQuery() est la méthode pour tester la mise à jour des données par recherche une information
	 */
	@Test
	void testUpdateByQuery() throws IOException {
		String queryField = "id_recette";
		String contenue = "BISQUEDÉCREVISSES";
		String updateField = "titre_recette";
		String update = "BISQUE DÉCREVISSES AA";		
		Update updateRecettes = new Update("recettes");
		BulkByScrollResponse updateresponse = updateRecettes.UpdateByQuery(queryField, contenue, updateField,update);
		List<Failure> listFailure = updateresponse.getBulkFailures();
		assertTrue(listFailure.isEmpty());
	}
	
	
	/*
	 * testDelete() est la méthode pour tester la suppression des données
	 */
	@Test
	void testDelete() throws IOException {
		/*
		 * il faut changer un id qui existe dans votre base de données de elasticearch
		 */
		String id = "G6nOqHgBtCrBG5s5bJKm";
		Search search = new Search("recettes");
		search.getInformationParId(id);
		
		Delete deleteRecette = new Delete("recettes");
		DeleteResponse deleteResponse = deleteRecette.deleteParId(id);
		int success = deleteResponse.getShardInfo().getSuccessful();
		assertTrue(success==1);
		assertNull(search.getInformationParId(id).getSource());

	}
	
	/*
	 * testDeleteParQuery() est la méthode pour tester la suppression des données en recherchant une information
	 */
	@Test
	void testDeleteParQuery() throws IOException {
		String queryField = "id_recette";
		String contenue = "BISQUEDÉCREVISSES"; 
		Delete deleteRecette = new Delete("recettes");
		BulkByScrollResponse deleteResponse = deleteRecette.deleteParQuery(queryField, contenue);
		List<Failure> listFailure = deleteResponse.getBulkFailures();
		assertTrue(listFailure.isEmpty());
	}
	
	/*
	 * testIndexerBibliotheque() est la méthode pour tester l'indexation des données du fichier bibliotheque.xml
	 */
	@Test
	void testIndexerBibliotheque() throws IOException, InterruptedException {
		IndexerBibliotheque indexBi = new IndexerBibliotheque(fileXml, "bibliotheque");
		ArrayList<IndexResponse> listIndexResponse = new ArrayList<IndexResponse>();

		listIndexResponse = indexBi.addSource();
		for (IndexResponse indexresponse : listIndexResponse) {
			int success = indexresponse.getShardInfo().getSuccessful();
			assertTrue("This will succeed.", success==1);
		
		}
		
	}
	
	
	/*
	 * testSearchBibliotheque() est la méthode pour tester la recherche des données du fichier bibliotheque.xml
	 */
	@Test
	void testSearchBibliotheque() throws IOException {		
		Search searchBibliotheque = new Search("bibliotheque");
		SearchResponse searchresponse = searchBibliotheque.searchParField("auteur", "J. K. Rowling");
		TotalHits total = searchresponse.getHits().getTotalHits();
		assertEquals(1,total.value);
		
	}
	
	/*
	 * testAjoutBibliotheque() est la méthode pour tester l'ajout des données
	 */
	@Test
	void testAjoutBibliotheque() throws IOException {
		IndexerBibliotheque ajoutBi = new IndexerBibliotheque("bibliotheque");
		Map<String, Object> jsonMapEribon = new HashMap<>();
		jsonMapEribon.put("titre", "Retour à Reims");
		jsonMapEribon.put("auteur", "Didier Eribon");
		jsonMapEribon.put("date_publication", "2009");
		jsonMapEribon.put("resume", "Retour à Reims est un essai autobiographique du sociologue et philosophe Didier Eribon paru le 30 septembre 2009 chez Fayard. "
				+ "Après la mort de son père, Didier Eribon retourne à Reims, sa ville natale, et retrouve son milieu d'origine, "
				+ "avec lequel il avait plus ou moins rompu trente ans auparavant.");
		IndexResponse indexResponse = ajoutBi.addinformation(jsonMapEribon);
		int success = indexResponse.getShardInfo().getSuccessful();
		assertTrue("This will succeed.", success==1);
	}
	
	/*
	 * testUpdateByQueryBibliotheque() est la méthode pour tester la mise à jour des données en recherchant une information
	 */
	@Test
	void testUpdateParQueryBibliotheque() throws IOException {
		String queryField = "titre";
		String contenue = "Harry Potter";
		String updateField = "auteur";
		String update = "JK Rowling";		
		Update updateRecettes = new Update("bibliotheque");
		BulkByScrollResponse updateresponse = updateRecettes.UpdateByQuery(queryField, contenue, updateField,update);
		List<Failure> listFailure = updateresponse.getBulkFailures();
		assertTrue(listFailure.isEmpty());	
		
	}
	
	/*
	 * testDeleteByQueryBibliotheque() est la méthode pour tester la suppression des données
	 */
	@Test
	void testDeleteByQueryBibliotheque() throws IOException {
		String queryField = "auteur";
		String contenu = "Victor Hugo";
		Delete deleteByQueryBibliotheque = new Delete("bibliotheque");
		BulkByScrollResponse deleteResponse = deleteByQueryBibliotheque.deleteParQuery(queryField, contenu);
		List<Failure> listFailure = deleteResponse.getBulkFailures();
		assertTrue(listFailure.isEmpty());
		
	}
	

}
