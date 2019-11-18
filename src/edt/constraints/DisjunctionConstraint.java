package edt.constraints;

import edt.activity.Activity;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
* Prend en compte deux contraintes et vérifie si au moins l'une des deux est satisfaite
*/
public class DisjunctionConstraint implements Constraint {

	/**
	* Première contrainte
	*/
	private Constraint c1;

	/**
	* Deuxième contrainte
	*/
	private Constraint c2;


	/**
	* @param c1 Première contrainte
	* @param c2 Deuxième contrainte
	*/
	public DisjunctionConstraint(Constraint c1, Constraint c2) {
		this.c1 = c1;
		this.c2 = c2;
	}


	public boolean isSatisfiedBySchedule(HashMap<Activity, GregorianCalendar> edt) {
		return this.c1.isSatisfiedBySchedule(edt) || this.c2.isSatisfiedBySchedule(edt);
	}


	/**
	* Récupère la première contrainte
	*
	* @return La première contrainte
	*/
	public Constraint getFirstConstraint() {
		return this.c1;
	}


	/**
	* Récupère la deuxième contrainte
	*
	* @return La deuxième contrainte
	*/
	public Constraint getSecondConstraint() {
		return this.c2;
	}


	@Override
	public String toString() {
		return "(" + this.c1 + ") ou (" + this.c2 + ")";
	}
}
