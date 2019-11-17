package edt.constraints;

import edt.activity.Activity;

import java.util.GregorianCalendar;
import java.util.HashMap;

/**
* Interface commune à toutes les contraintes
*/
public interface Constraint {

	/**
	* Permet de savoir si la constrainte est respectée selon l'emploi du temps fournit en paramètre.
	*
	* @param edt L'emploi du temps
	* @return True si la contrainte est respectée, false sinon
	*/
	public boolean isSatisfiedBySchedule(HashMap<Activity, GregorianCalendar> edt);

}
