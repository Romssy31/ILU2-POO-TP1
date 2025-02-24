package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	private int nbEtalsMarche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtalsMarche) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtalsMarche);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}
	
	public boolean chefPresent() {
		if(chef==null) 
			return false;
		else 
			return true;
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

	public String afficherVillageois() throws VillageSansChefException {
		
		if(chefPresent()==false) {
			throw new VillageSansChefException("Le village ne peut pas exister sans chef ! \n");
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	

	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int touverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() == false) {
					return i;
				}
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {
			int nbEtalsAvecProd = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit))
					nbEtalsAvecProd++;
			}
			Etal[] etalsAvecProd = new Etal[nbEtalsAvecProd];
			int indiceTab = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsAvecProd[indiceTab] = etals[i];
					indiceTab++;
				}
			}
			return etalsAvecProd;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if(etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		private void afficherMarche() {
			int nbEtalVide = 0;
			for (int i = 0; i< etals.length; i++) {
				if(etals[i].isEtalOccupe()) {
					etals[i].afficherEtal();
				}
				else {
					nbEtalVide ++;
				}
			}
			System.out.println("Il reste " +
					nbEtalVide + " �tals non utilis�s dans le march�.\n�.");
		}
		
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom()+" cherche un endroit pour vendre "+nbProduit+" "+produit+". \n");
		int etalLibre = marche.touverEtalLibre();
		marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
		int numeroEtal = etalLibre+1; //Car Numero different d'indice
		chaine.append("Le vendeur "+vendeur.getNom()+" vend des "+produit+" à l'étal n°"+numeroEtal+". \n");
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		
		Etal[] tabVendeurs = marche.trouverEtals(produit);
		int nbProduits = tabVendeurs.length;
		if(nbProduits==0)
			chaine.append("Il n'y a pas de vendeur qui propose des fleurs au marché. \n");
		else if(nbProduits==1)
			chaine.append("Seul le vendeur "+tabVendeurs[0].getVendeur().getNom()+" propose des "+produit+" au marché. \n");
		else {
			chaine.append("Les vendeurs qui proposent des fleurs sont : \n");
			for(int i=0;i<nbProduits;i++) {
				chaine.append("- "+tabVendeurs[i].getVendeur().getNom()+"\n");
			}
		}
		
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etalVendeur = marche.trouverVendeur(vendeur);
		return etalVendeur.libererEtal();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		int nbEtalsVide=0;
		int nbEtalsOccupe=0;
		int nbEtals = nbEtalsMarche;
		for(int i=0;i<nbEtals;i++) {
			if(marche.etals[i].isEtalOccupe()==false)
				nbEtalsVide+=1;
			else {
				nbEtalsOccupe+=1;
			}
		}
		if(nbEtalsOccupe>0) {
			chaine.append("Le marché du village "+nom+" possède plusieurs étals : \n");
			for(int i=0;i<nbEtals;i++) {
				if(marche.etals[i].isEtalOccupe()) {
					chaine.append(marche.etals[i].getVendeur().getNom()+" vend "+marche.etals[i].getQuantite()+" "+marche.etals[i].getProduit()+"\n");
				}
			}
		}
		if(nbEtalsVide>0) {
			chaine.append("Il reste "+nbEtalsVide+" étals non utilisés dans le marché. \n");
		}
		return chaine.toString();
	}

}