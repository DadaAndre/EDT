package edt.constraints;

import edt.activity.Activity;

import java.util.GregorianCalendar;
import java.util.HashMap;

/**
* Contrainte binaire satisfaite lorsque la première activité se termine avant que la deuxième ne commence
*/
public class PrecedenceConstraint extends BinaryConstraint {

	/**
	* @param first La première activité
	* @param second La deuxième activité
	*/
	public PrecedenceConstraint(Activity first, Activity second) {
		super(first, second);
	}


	@Override
	public boolean isSatisfied(GregorianCalendar dateDebutAct1, GregorianCalendar dateDebutAct2) {
		// On récupère la fin de la première activité
		GregorianCalendar dateFinAct1 = new GregorianCalendar();
		dateFinAct1.setTime(dateDebutAct1.getTime());
		dateFinAct1.add(GregorianCalendar.MINUTE, this.getFirstActivity().getDuree());

		// On vérifie si la fin de l'activité se passe avant le début de la seconde
		return dateFinAct1.compareTo(dateDebutAct2) <= 0;
	}


	@Override
	public String toString() {
		return this.getFirstActivity().getDesc() + " avant " + this.getSecondActivity().getDesc();
	}
}
