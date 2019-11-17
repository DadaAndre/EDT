package edt.constraints;

import edt.activity.Activity;

import java.util.GregorianCalendar;
import java.util.HashMap;

/**
* Contrainte binaire satisfaite lorsque qu'il s'écoule au minimum un certain délai entre la fin de la première activité et le début de la deuxième
*/
public class PrecedenceConstraintWithGap extends PrecedenceConstraint {

	/**
	* Le temps minimum entre la fin de la première activité et le début de la deuxième (en minutes)
	*/
	private int gap = 0;


	/**
	* @param first La première activité
	* @param second La deuxième activité
	* @param gap Le temps minimum entre la fin de la première activité et le début de la deuxième (en minutes)
	*/
	public PrecedenceConstraintWithGap(Activity first, Activity second, int gap) {
		super(first, second);

		this.gap = gap;
	}


	@Override
	public boolean isSatisfied(GregorianCalendar dateDebutAct1, GregorianCalendar dateDebutAct2) {
		// On récupère la fin de la première activité + le gap
		GregorianCalendar dateFinAct1 = new GregorianCalendar();
		dateFinAct1.setTime(dateDebutAct1.getTime());
		dateFinAct1.add(GregorianCalendar.MINUTE, this.getFirstActivity().getDuree() + this.gap);


		// On vérifie si la fin de l'activité et du gap se passe avant le début de la seconde activité
		return dateFinAct1.compareTo(dateDebutAct2) <= 0;
	}


	@Override
	public String toString() {
		return this.getFirstActivity().getDesc() + " avec une pause de " + this.gap + " minutes avant " + this.getSecondActivity().getDesc();
	}

}
