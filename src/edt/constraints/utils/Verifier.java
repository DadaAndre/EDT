package edt.constraints.utils;

import edt.activity.Activity;
import edt.constraints.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
* Permet de verifier qu'un ensemble de contraintes est satisfait par un emploi du temps
*/
public class Verifier {

	/**
	* Liste des contraintes à verifier
	*/
	private ArrayList<Constraint> listOfConstraint;


	public Verifier() {
		this.listOfConstraint = new ArrayList<>();
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
	public boolean verify(HashMap<Activity, GregorianCalendar> edt) {
		for(Constraint c : this.listOfConstraint) {
			// Si une seule condition est fausse, on retourne false
			if(c.isSatisfiedBySchedule(edt) == false) {
				return false;
			}
		}

		// Si on arrive ici c'est que toutes les contraintes sont satisfaites
		return true;
	}


	/**
	* Récupère la liste des contraintes non statisfaites
	*
	* @param edt L'emploi du temps
	* @return La liste des contraintes non statisfaites
	*/
	public List<Constraint> listOfFailConstraint(HashMap<Activity, GregorianCalendar> edt) {
		List<Constraint> failConstraint = new ArrayList<>();

		for(Constraint c : this.listOfConstraint) {
			if(c.isSatisfiedBySchedule(edt) == false) {
				// Si la contrainte n'est pas satisfaite on l'ajoute a la liste
				failConstraint.add(c);
			}
		}

		return failConstraint;
	}

}
