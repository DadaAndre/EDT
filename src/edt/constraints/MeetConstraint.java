package edt.constraints;

import edt.activity.Activity;
import java.util.GregorianCalendar;

/**
* Contrainte binaire satisfaite lorsque la première activité se termine lorsque la deuxième commence
*/
public class MeetConstraint extends BinaryConstraint {

	/**
	* @param first La première activité
	* @param second La deuxième activité
	*/
	public MeetConstraint(Activity first, Activity second) {
		super(first, second);
	}


	@Override
	public boolean isSatisfied(GregorianCalendar dateDebutAct1, GregorianCalendar dateDebutAct2) {
		GregorianCalendar dateFinAct1 = new GregorianCalendar(); //création d'un nouveau calendrier.
		dateFinAct1.setTime(dateDebutAct1.getTime()); // mettre ce nouveau calendrier à la même date que la première activité.
		dateFinAct1.add(GregorianCalendar.MINUTE, this.getFirstActivity().getDuree()); //on ajoute à l'heure de la première activité, sa durée.

		/* on compare les deux activités: si la deuxième activité
		* commence dirrectement après la première activité, alors le compareTo vaut "0".
		*/
		return dateFinAct1.compareTo(dateDebutAct2) == 0;
	}


	@Override
	public String toString() {
		return this.getSecondActivity().getDesc() + " quand " + this.getFirstActivity().getDesc() + " se termine";
	}
}
