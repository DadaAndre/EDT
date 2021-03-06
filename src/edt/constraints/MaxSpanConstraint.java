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
	* Durée maximum pour faire les activités
	*/
	private int dureeMax;

	/**
	 * Liste des activités
	 */
	private ArrayList<Activity> activities;


	public MaxSpanConstraint(int dureeMax) {
		this.dureeMax = dureeMax;
		this.activities = new ArrayList<>();
	}


	public boolean isSatisfiedBySchedule(HashMap<Activity, GregorianCalendar> edt) {
		Activity plusTot = null;
		Activity plusTard = null;
		GregorianCalendar finPlusTard = new GregorianCalendar();

		for(Activity a : this.activities) {
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

			// L'activité "a" commence plus tôt que "plusTot"
			if(edt.get(a).compareTo(edt.get(plusTot)) < 0) {
				plusTot = a;
			}

			// L'activité "a" se termine plus tard que "plusTard"
			if(finA.compareTo(finPlusTard) > 0) {
				plusTard = a;
				finPlusTard = finA;
			}
		}


		// Récupère la différence de temps en millisecondes entre le début de l'activité la plus tot et la fin de la plus tard
		GregorianCalendar debutPlusTot = edt.get(plusTot);
		long diffTimeMilis = finPlusTard.getTime().getTime() - debutPlusTot.getTime().getTime();

		// On convertit ensuite en minutes (= diviser par 1000*60)
		return diffTimeMilis/(1000*60) <= this.dureeMax;
	}


	/**
 	* Ajoute une activite à la liste
	*
	* @param a L'activité à ajouter
 	*/
	public void add(Activity a) {
		this.activities.add(a);
	}


	/**
 	 * Permet de savoir si une activité est dans la liste
	 *
	 * @param a L'activité à vérifier
	 * @return True si l'activité est dans la liste, false sinon
 	 */
	public boolean contains(Activity a) {
		return this.activities.contains(a);
	}


	/**
 	* Change l'intervalle max pour réaliser les activités
	*
	* @param dureeMax La nouvelle intervalle maximum pour réaliser les activités
	*/
	public void setDureeMax(int dureeMax) {
		this.dureeMax = dureeMax;
	}


	@Override
	public String toString() {
		String str = "";
		for(Activity a : this.activities) {
			str += a.getDesc() + ", ";
		}

		str = str.substring(0, str.length() - 2);
		str += " en moins de " + this.dureeMax + " mins";

		return str;
	}
}
