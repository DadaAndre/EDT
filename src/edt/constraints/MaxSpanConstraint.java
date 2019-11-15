package edt.constraints;

import edt.activity.Activity;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 * Vérifie qu'il ne sécoule pas plus d'un certains temps en minute entre le début et la fin d'une série d'activités
 */
public class MaxSpanConstraint implements Constraint {

	/**
	 * Dure maximum pour faire les activités
	 */
	private int dureeMax;

	/**
	 * Liste des activités
	 */
	private ArrayList<Activity> liste;


	public MaxSpanConstraint(int dureeMax) {
		this.dureeMax = dureeMax;
		this.liste = new ArrayList<>();
	}


	public boolean isSatisfiedBySchedule(HashMap<Activity, GregorianCalendar> edt) {
		Activity plusTot = null;
		Activity plusTard = null;
		GregorianCalendar finPlusTard = new GregorianCalendar();

		for(Activity a : liste) {
			// On regarde quand se finit l'activité en cours
			GregorianCalendar finA = new GregorianCalendar();
			finA.setTime(edt.get(a).getTime());
			finA.add(GregorianCalendar.MINUTE, a.getDuree());

			// Si plusTot vaut null, c'est le 1er tour de boucle, on change l'activité la plus tard et la plus tot
			if(plusTot == null) {
				plusTot = a;
				plusTard = a;
				finPlusTard = finA;

				continue;
			}

			if(edt.get(a).compareTo(edt.get(plusTot)) < 0) {
				plusTot = a;
			}

			if(finA.compareTo(finPlusTard) > 0) {
				plusTard = a;
				finPlusTard = finA;
			}
		}

		GregorianCalendar debutPlusTot = edt.get(plusTot);

		// Récupère la différence de temps en millisecondes entre le début de l'activité la plus tot et la fin de la plus tard
		// On convertit ensuite en minutes (= diviser par 1000*60)
		return (finPlusTard.getTime().getTime() - debutPlusTot.getTime().getTime())/(1000*60) <= this.dureeMax;
	}


	/**
 	 * Ajoute une activite a la liste
	 *
	 * @param a L'activité à ajouter
 	 */
	public void add(Activity a) {
		this.liste.add(a);
	}


	/**
 	 * Change l'intervalle max pour réaliser les activités
	 *
	 * @param dureeMax La nouvelle intervalle maximum pour réaliser les activités
	*/
	public void setDureeMax(int dureeMax) {
		this.dureeMax = dureeMax;
	}

}
