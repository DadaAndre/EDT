package edt.constraints.utils;

import java.util.ArrayList;
import java.util.HashMap;
import edt.activity.Activity;
import edt.constraints.*;
import java.util.GregorianCalendar;

/**
* Permet de verifier qu'un ensemble de contraintes est satisfait par un emploi du temps
*/
public class Verifier {

	/**
	* Liste des contraintes à verifier
	*/
	private ArrayList<Constraint> listOfConstraint;


	public Verifier() {
		this.listOfConstraint = new ArrayList<>(); //Création d'une ArrayList contenant la liste des contraintes
	}


	/**
	* Permet d'ajouter une contrainte à verifier
	*
	* @param constraint La contrainte à ajouter
	*/
	public void addConstraint(Constraint constraint) {
		this.listOfConstraint.add(constraint);
	}


	/**
	* Verifie que chaque contraintes est satisfaite par l'emploi du temps fourni
	*
	* @param edt L'emploi du temps
	* @return True si toute les contraintes sont satisfaites, False sinon
	*/
	public boolean verify(HashMap<Activity,GregorianCalendar> edt) {
		for(Constraint c : listOfConstraint) { //on essaye chaque contraintes

			if(c.isSatisfiedBySchedule(edt) == false) { //si une seule condition est fausse, on retourne false
				return false;
			}
		}

		return true;
	}


}
