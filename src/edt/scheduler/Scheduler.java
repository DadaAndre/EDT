package fil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Regarde si un plan peut être créer en prenant en compte plusieurs contraintes
*/

public class Scheduler {

	private HashMap<Activity, Integer> initNbPreds(List<PrecedenceConstraint> contraintes) {
		/**
		* Créer une liste de contrainte afin de compter le nombre de predecesseurs de chaque activités.
		*
		* @param contraintes liste de PrecedenceConstraint créer à partir d'activité definie
		*/
		HashMap<Activity, Integer> nbrPredecesseurs = new HashMap<>();

		for(PrecedenceConstraint precedConstraint : contraintes) {
			if (! nbrPredecesseurs.containsKey(precedConstraint.getFirstActivity())) {
				nbrPredecesseurs.put(precedConstraint.getFirstActivity(),0);
			}

			if (nbrPredecesseurs.containsKey(precedConstraint.getSecondActivity())) {
				Integer valeur = nbrPredecesseurs.get(precedConstraint.getSecondActivity());
				nbrPredecesseurs.put(precedConstraint.getSecondActivity(), valeur+1);
			} else {
				nbrPredecesseurs.put(precedConstraint.getSecondActivity(), 1);
			}
		}

		return nbrPredecesseurs;
		/**
		* @return nbrPredecesseurs retourne une HashMap comprenant une activité et le nombre de ses prédécesseurs
		*/
	}

	//ajoute l'activité dans l'edt et réduit de -1 ses prédécesseurs
	private void scheduleActivity(Activity act, int heure, List<PrecedenceConstraint> contraintes, HashMap<Activity, Integer> edt, HashMap<Activity, Integer> nbrPredecesseurs) {
		/**
		* ajoute les activités un par une dans l'emploi du temps en fonction de son nombre de prédecesseurs.
		*
		* @param act activité  à ajouter dans l'emploi du temps dont le nombre de prédécesseur est à zéro
		* @param heure nombre de minutes écoulé depuis la toute premiere activité ajouté dans l'emploi du temps
		* @param contraintes liste de contrainte
		* @param edt emploi du temps où les activités sont ajouté
		* @param nbrPredecesseurs HashMap dont les activités, non encore implenté dans l'edt, sont enregistré avec le nombre de leurs predecesseurs (valeur > 0: pas encore ajouter dans l'edt, valeur = 0: activité qui est en train d'être rajouter dans l'edt)
		*/

		edt.put(act, heure);

		for (PrecedenceConstraint precedConstraint : contraintes) {
			if (precedConstraint.getFirstActivity() == act) {
				Integer valeur = nbrPredecesseurs.get(precedConstraint.getSecondActivity());
				nbrPredecesseurs.put(precedConstraint.getSecondActivity(), valeur-1);
			}
		}

		nbrPredecesseurs.remove(act);

	}

	public HashMap<Activity, Integer> computeSchedule(List<PrecedenceConstraint> contraintes) {
		/**
		* méthode qui construit l'emploi du temps en fonction des contraintes
		* @param contraintes liste de de contraintes
		*/
		HashMap<Activity, Integer> nbrPredecesseurs = initNbPreds(contraintes);
		HashMap<Activity, Integer> edt = new HashMap<Activity, Integer>();
		int heure = 0;

		while(nbrPredecesseurs.size() > 0) {
			Activity actZero = null;

			for (Map.Entry<Activity, Integer> entry : nbrPredecesseurs.entrySet()) {
				if (entry.getValue() == 0){ // si l'activité a zero prédécesseur
					actZero = entry.getKey(); // met l'activité dans une variable (= on la sélectionne)
					break; // arréte la boucle si il trouve une activité à 0 prédécesseur
				}
			}

			// Si on a aucune activité avec 0 prédécesseur on arrête car aucune plan n'est possible
			if (actZero == null){
				return null;
			}

			scheduleActivity(actZero, heure, contraintes, edt, nbrPredecesseurs);
			heure += actZero.getDuree();
		}

		return edt;
		/**
		* @return retourne l'emploi du temps complet
		*/
	}
}
