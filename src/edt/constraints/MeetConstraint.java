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
		// On récupère la fin de la première activité
		GregorianCalendar dateFinAct1 = new GregorianCalendar();
		dateFinAct1.setTime(dateDebutAct1.getTime());
		dateFinAct1.add(GregorianCalendar.MINUTE, this.getFirstActivity().getDuree());

		// On vérifie si la fin de l'activité se passe en même temps que le début de la seconde
		return dateFinAct1.compareTo(dateDebutAct2) == 0;
	}


	@Override
	public String toString() {
		return this.getSecondActivity().getDesc() + " quand " + this.getFirstActivity().getDesc() + " se termine";
	}
}
