package edt;

import java.util.HashMap;
import edt.activity.Activity;
import edt.constraints.*;

public class Test{
	private PrecedenceConstraint contrainte;
	private int heure1;
	private int heure2;
	private int heure3;

	public Test(PrecedenceConstraint contrainte,int heure1, int heure2, int heure3){
		this.contrainte = contrainte;
		this.heure1 = heure1;
		this.heure2 = heure2;
		this.heure3 = heure3;

		afficherTest();
	}
	

	public void afficherTest(){
		System.out.println("test2:");

        	// Test avec une programmation censée satisfaire la contrainte
		if (! contrainte.isSatisfied(heure1, heure3) ) {
                	System.out.println("Mon programme ne marche pas.");
        		System.out.println("Il aurait dû trouver que la contrainte n'est pas satisfaite.");
   		 
		} else {
        		System.out.println("Mon programme passe le troisième test avec succès.");
    		}

		// Test avec une programmation censée ne pas satisfaire la contrainte
    		if ( contrainte.isSatisfied(heure2, heure1) ) {
        		System.out.println("Mon programme ne marche pas.");
       			System.out.println("Il aurait dû trouver que la contrainte n'est pas satisfaite.");
    		} else {
        		System.out.println("Mon programme passe le deuxième test avec succès.");
    		}

		// Test avec une programmation censée ne pas satisfaire la contrainte (car la première
		// activité finirait après le début de la seconde)
		if ( contrainte.isSatisfied(heure1, heure2) ) {
			System.out.println("Mon programme ne marche pas.");
			System.out.println("Il aurait dû trouver que la contrainte n'est pas satisfaite.");
		} else {
			System.out.println("Mon programme passe le troisième test avec succès.");
		}

    }

}
