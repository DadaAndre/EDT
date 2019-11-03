package edt.constraints;

import edt.activity.Activity;
import java.util.HashMap;
import java.util.GregorianCalendar;

/**
* Représente une contrainte binaire concernant deux {@link Activity}.
*/
public abstract class BinaryConstraint implements Constraint {

	/**
	* La première activité
	*/
	private Activity firstActivity;
	/**
	* La deuxième activité
	*/
	private Activity secondActivity;


	/**
	* @param first La première activité
	* @param second La deuxième activité
	*/
	public BinaryConstraint(Activity first, Activity second) {
		this.firstActivity = first;
		this.secondActivity = second;
	}


	/**
	* Vérifie si la contrainte est satisfaite, sachant que la première activité commence à dateDebutAct1 et la deuxième commence à dateDebutAct2
	*
	* @param dateDebutAct1 Le début de la première activité
	* @param dateDebutAct2 Le début de la deuxième activité
	* @return True si la contrainte est satisfaite false sinon
	*/
	public abstract boolean isSatisfied(GregorianCalendar dateDebutAct1, GregorianCalendar dateDebutAct2);


	@Override
	public boolean isSatisfiedBySchedule(HashMap<Activity, GregorianCalendar> edt) {
		return isSatisfied(edt.get(this.getFirstActivity()),edt.get(this.getSecondActivity()));
	}


	/**
	* Récupère la première activité
	*
	* @return La première activité
	*/
	public Activity getFirstActivity() {
		return this.firstActivity;
	}


	/**
	* Récupère la seconde activité
	*
	* @return La seconde activité
	*/
	public Activity getSecondActivity() {
		return this.secondActivity;
	}
}
