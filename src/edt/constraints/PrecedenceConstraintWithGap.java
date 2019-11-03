package edt.constraints;

import edt.activity.Activity;
import java.util.HashMap;
import java.util.GregorianCalendar;

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
	public PrecedenceConstraintWithGap(Activity activite1, Activity activite2, int gap) {
		super(activite1, activite2);

		this.gap = gap;
	}


	@Override
	public boolean isSatisfied(GregorianCalendar dateDebutAct1, GregorianCalendar dateDebutAct2) {
		GregorianCalendar dateFinAct1 = new GregorianCalendar(); //création d'un nouveau calendrier.
		dateFinAct1.setTime(dateDebutAct1.getTime()); // mettre ce nouveau calendrier à la même date que la première activité.
		dateFinAct1.add(GregorianCalendar.MINUTE, this.getFirstActivity().getDuree()+this.gap); //on ajoute à l'heure de la première activité, sa durée et la pause.

		/* on compare les deux activités: si la deuxième activité
		* commence après la fin de la première activité (et le gap), alors le compareTo est un nombre inferieur à "0".
		*/
		return dateFinAct1.compareTo(dateDebutAct2) <= 0;
	}


	@Override
	public String toString() {
		return this.getFirstActivity() + " avec une pause de " + this.gap + "minutes avant " + this.getSecondActivity();
	}

}
