package edt.constraints;

import edt.activity.Activity;
import edt.constraints.Constraint;

import java.util.HashMap;
import java.util.GregorianCalendar;

/**
* Contrainte satisfaite lorsqu'une contrainte C échoue
*/
public class NegationConstraint implements Constraint {

	/**
	* La contrainte C
	*/
	private Constraint constraint;


	/**
	* @param constraint La contrainte C
	*/
	public NegationConstraint(Constraint constraint) {
		this.constraint = constraint;
	}


	@Override
	public boolean isSatisfiedBySchedule(HashMap<Activity, GregorianCalendar> edt) {
		return !this.constraint.isSatisfiedBySchedule(edt);
	}


	/**
	* Récupère la contrainte
	*
	* @return La contrainte
	*/
	public Constraint getConstraint() {
		return this.constraint;
	}


	/**
	* Change la contrainte
	*
	* @param constraint La nouvelle contrainte
	*/
	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}


	@Override
	public String toString() {
		return "non(" +  this.constraint + ")";
	}

}
