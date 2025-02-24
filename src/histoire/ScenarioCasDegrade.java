package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;

public class ScenarioCasDegrade {
	
	public static void main(String[] args) {
		
		Village village = new Village("le village des irréductibles", 10, 5);
		Chef abraracourcix = new Chef("Abraracourcix", 10, village);
		village.setChef(abraracourcix);
		Gaulois obelix = new Gaulois("Obélix", 25);
		village.ajouterHabitant(obelix);
		
		Etal etal = new Etal();
		etal.libererEtal();
		//On a un NullPointeurException qui est une RuntimeException 
		// => on a le choix entre modifier l'algo ou gérer l'exception
		
		//etal.acheterProduit(1, null); //NullPointeurException aussi
		
		//Est-ce grave que les try ne soient pas imbriqués ?
		try {
			etal.acheterProduit(0,obelix);
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		Etal etalVide = new Etal();
		try {
			etalVide.acheterProduit(2,obelix);
		}catch(IllegalStateException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("Fin du test \n");
		
	}
}
