package edt.constraints;

import java.util.HashMap;
import edt.activity.Activity;
import java.util.GregorianCalendar;

public class PrecedenceConstraint extends BinaryConstraint {

    public PrecedenceConstraint(Activity first, Activity second){
        super(first, second);
    }

    public boolean isSatisfied(GregorianCalendar dateDebutAct1, GregorianCalendar dateDebutAct2){
		GregorianCalendar dateFinAct1 = new GregorianCalendar(); //création d'un nouveau calendrier.
		dateFinAct1.setTime(dateDebutAct1.getTime()); // mettre ce nouveau calendrier à la même date que la première activité.
		dateFinAct1.add(GregorianCalendar.MINUTE, this.getFirstActivity().getDuree()); //on ajoute à l'heure de la première activité, sa durée.

		/* on compare les deux activités: si la deuxième activité
		* commence après la fin de la première activité, alors le compareTo est un nombre inferieur à "0".
		*/
		return dateFinAct1.compareTo(dateDebutAct2) <= 0;
    }

    @Override
    public String toString() {
        return this.getFirstActivity() + " avant " + this.getSecondActivity();
    }
}
