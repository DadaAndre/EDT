package edt.constraints.utils;

import java.util.ArrayList;
import java.util.HashMap;
import edt.activity.Activity;
import edt.constraints.*;
import java.util.GregorianCalendar;

public class Verifier {

	private ArrayList<Constraint> listOfConstraint;

	public Verifier() {
		this.listOfConstraint = new ArrayList<>(); //Création d'une ArrayList contenant la liste des contraintes
	}

	public void addConstraint(Constraint constraint) { //ajout d'une contrainte
		this.listOfConstraint.add(constraint);
	}

	public boolean verify(HashMap<Activity,GregorianCalendar> edt) {
		for(Constraint c : listOfConstraint) { //on essaye chaque contraintes

			//on regarde que l'emploi du temps satisfait la contrainte actuelle
			if(c.isSatisfiedBySchedule(edt) == false) { //si une seule condition est fausse, on retourne false
				return false;
			}
		}

		return true;
	}


}
