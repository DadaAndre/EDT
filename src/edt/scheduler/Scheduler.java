package edt.constraints;

import edt.constraints.PrecedenceConstraint;
import edt.activity.Activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scheduler {

	private HashMap<Activity, Integer> initNbPreds(List<PrecedenceConstraint> contraintes) {
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
	}

	//ajoute l'activité dans l'edt et réduit de -1 ses prédécesseurs
	private void scheduleActivity(Activity act, int heure, List<PrecedenceConstraint> contraintes, HashMap<Activity, Integer> edt, HashMap<Activity, Integer> nbrPredecesseurs) {
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
	}
}
