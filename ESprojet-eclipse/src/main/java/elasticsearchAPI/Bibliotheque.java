package elasticsearchAPI;

import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

/**
 * Cette classe Bibliotheque lit un fichier xml et le stocke dans une liste de classe Livre.
 */

public class Bibliotheque {
	
	protected ArrayList<Livre> lists = new ArrayList<Livre>();
	private String file;
	
	
	/**
	 * Constructeur de la classe Bibliotheque
	 * @param filePath, un string
	 */
	public Bibliotheque(String filePath) {
		this.file = filePath;
		this.readXml();
	}
	

	private void readXml(){
		
		 try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(this.file);
				NodeList livreList = doc.getElementsByTagName("livre");
				
				for (int i = 0; i < livreList.getLength(); i++) {
					Node livreNode = livreList.item(i);
					if (livreNode.getNodeType() == Node.ELEMENT_NODE) {
						Element livreElement = (Element) livreNode;
						NamedNodeMap attrs = livreNode.getAttributes();
						Livre livre = new Livre();
						Element titreElement = (Element)livreElement.getElementsByTagName("titre").item(0);
						livre.setTitre(titreElement.getTextContent());
						Element auteurElement = (Element)livreElement.getElementsByTagName("auteur").item(0);
						livre.setAuteur(auteurElement.getTextContent());
						try {
						Element nbElement = (Element)livreElement.getElementsByTagName("nb_tomes").item(0);						
						livre.setNbTomes(nbElement.getTextContent());
						
						for (int j =0; j<attrs.getLength(); j++) {
							Node langue = attrs.item(j);
							livre.setLangue(langue.getNodeValue());
						}
						}
						catch(NullPointerException n) {}					
						Element dateElement = (Element)livreElement.getElementsByTagName("date_publication").item(0);
						livre.setDatePublication(dateElement.getTextContent());
						Element resumeElement = (Element)livreElement.getElementsByTagName("resume").item(0);
						livre.setResume(resumeElement.getTextContent());
						
						lists.add(livre);					
						
					}
				}
		 }
		 catch (Exception e) {
			 e.printStackTrace();
		 
	}
		 
	}
}
