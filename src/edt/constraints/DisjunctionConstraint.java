package edt.constraints;

import edt.activity.Activity;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
* Prend en compte deux contraintes et regarde si au moins une est satisfaite
*/

public class DisjunctionConstraint implements Constraint {

	/**
	* Première contrainte
	*/
	Constraint c1;

	/**
	* Deuxième contrainte
	*/
	Constraint c2;


	/**
	* @param c1 Première contrainte
	* @param c2 Deuxième contrainte
	*/
    public DisjunctionConstraint(Constraint c1, Constraint c2) {
        this.c1 = c1;
        this.c2 = c2;
    }


	/**
	* Prend un emploi du temps et regarde si au moins une des contrainte est satisfaite
	*
	* @param edt emploi du temps
	* @return Retourne false si aucune des contraintes n'est satisfaites, true si au moins une des contraintes est satisfaite
	*/
    public boolean isSatisfiedBySchedule(HashMap<Activity,GregorianCalendar> edt) {
		return c1.isSatisfiedBySchedule(edt) || c2.isSatisfiedBySchedule(edt);
	}
}
