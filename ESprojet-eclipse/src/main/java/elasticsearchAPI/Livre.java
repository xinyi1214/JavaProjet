package elasticsearchAPI;

public class Livre {
		
		private String titre;
		private String auteur;
		private String nbTomes;
		private String datePublication;
		private String resume;
		private String langue;
		
		
		public String getLangue() {
			return langue;
		}

		public void setLangue(String langue) {
			this.langue = langue;
		}

		public String getTitre() {
			return titre;
		}

		public void setTitre(String titre) {
			this.titre = titre;
		}

		public String getAuteur() {
			return auteur;
		}

		public void setAuteur(String auteur) {
			this.auteur = auteur;
		}

		public String getNbTomes() {
			return nbTomes;
		}

		public void setNbTomes(String nbTomes) {
			this.nbTomes = nbTomes;
		}

		public String getDatePublication() {
			return datePublication;
		}

		public void setDatePublication(String datePublication) {
			this.datePublication = datePublication;
		}

		public String getResume() {
			return resume;
		}

		public void setResume(String resume) {
			this.resume = resume;
		}

	}



