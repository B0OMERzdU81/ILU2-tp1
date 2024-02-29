package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	public Village(String nom, int nbVillageoisMaximum, int nbEtalsMax) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche= new Marche(nbEtalsMax);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
//////////////////////// Méthodes permettant d'interagir avec le marché //////////////////////////////////////////////////////
	
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre "+ nbProduit + " " +produit +"\n");
		int k = marche.trouverEtalLibre();
		if(k==-1) {
			chaine.append("Malheureusement il n'y a pas d'étals disponible. \n");
			return chaine.toString();
		}
		else {
			k++;
			marche.utiliserEtal(k, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vends des "+ produit + " à l'étal n°"+ k +"\n");
			return chaine.toString();
		}
	}
	
	
	public String rechercherVendeursProduit(String produit) {
		
		Etal[] etals=marche.trouverEtals(produit);
		StringBuilder chaine = new StringBuilder();
		if (etals[0]==null) {
			chaine.append("Il n'y a pas de vendeur qui propose des "+produit+" au marché. \n");
			return chaine.toString();
		}
		
		if(etals[1]==null) {
			chaine.append("Il n'y a que " +etals[0].getVendeur().getNom()+ " qui propose des "+produit+" au marché \n");
			return chaine.toString();
		}
		
		chaine.append("Les vendeurs qui proposent des " + produit +" sont :\n");
		for (int i = 0; i < etals.length; i++) {
			chaine.append("- "+etals[i].getVendeur().getNom()+" \n");
		}
		
		return chaine.toString();
		
	}
	
	
	// classe interne privé statique Marche contenant des Etals
	private static class Marche {
		private Etal[] etals;

		private Marche(int nombreEtals) {
			etals = new Etal[nombreEtals];
			
			for (int i = 0; i < etals.length; i++) {
				etals[i]=new Etal();
				
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return (i);
				}
			}
			return (-1);
		}

		// Fonction qui renvoie une liste des Etals contenant le produit mit en
		// paramètre
		private Etal[] trouverEtals(String produit) {
			int longueur = 0;

			for (int k = 0; k < etals.length; k++) {
				if (etals[k].contientProduit(produit)) {
					longueur++;
				}
			}

			Etal[] etalProduit = new Etal[longueur];
			int compteur = 0;

			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalProduit[compteur] = (etals[i]);
					compteur++;
				}
			}
			return etalProduit;
		}

		private Etal trouverVendeur(Gaulois gaulois) {

			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}

		private String afficherMarche() {
			int i = 0;
			for (int k = 0; k < etals.length; k++) {
				if (!etals[k].isEtalOccupe()) {
					i++;
				} else {
					etals[k].afficherEtal();
				}
			}

			StringBuilder chaine = new StringBuilder();
			chaine.append("Il reste " + i + " etals non utilisés sur le marché. \n");
			return chaine.toString();
		}

	}

}