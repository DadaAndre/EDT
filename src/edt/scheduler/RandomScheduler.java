package edt.scheduler;

import edt.activity.Activity;
import edt.constraints.Constraint;
import edt.constraints.utils.Verifier;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Génère plusieurs emplois du temps et renvoie le meilleur (celui qui satisfait le plus de contraintes)
 */
public class RandomScheduler {

	/**
	* Liste des contraintes à satisfaire
	*/
	private List<Constraint> constraints;

	/**
	* Liste des activités à avoir dans l'emploi du temps
	*/
	private Set<Activity> activities;

	/**
	* Génère des nombres aléatoires
	*/
	private Random randomGenerator;

	/**
	* Permet de calculer le score d'un edt
	*/
	private Verifier verification;


	public RandomScheduler() {
		this.activities = new HashSet<>();
		this.constraints = new ArrayList<>();
		this.randomGenerator = new Random();
		this.verification = new Verifier();
	}


	/**
	* Planifie un emploi du temps aléatoirement
	*
	* @return L'emploi du temps généré
	*/
	public HashMap<Activity, GregorianCalendar> randomSchedulerMap() {
		// Créer un tableau dynamique pour sauvegarder les créneaux horaires déja utilisées
		ArrayList<Integer> randomTimeUsed = new ArrayList<>();
		// Créer un emploi du temps vide
		HashMap<Activity, GregorianCalendar> emploiDuTemps = new HashMap<>();

		for(Activity act : this.activities) {
			int randomTime = 0;
			do {
				// Pour chaque activité on sélectionne un créneau horaire aléatoire de 8h à 19h (non compris), d'où le 19-8
				// Les créneaux horaires possibles se font toutes les 15 mins soit 4 créneaux possibles par heure, d'où le (19-8)*4
				randomTime = this.randomGenerator.nextInt((19-8)*4);
			} while(randomTimeUsed.contains(randomTime));

			// On ajoute ce nouveau créneau au tableau des créneau déjà pris
			randomTimeUsed.add(randomTime);

			// On décompose le créneau obtenu en heures et minutes
			int hour = randomTime / 4;
			int minutes = (randomTime % 4) * 15;

			// On ajoute 8h pour que l'horaire commence à 8h et non à minuit
			hour += 8;

			// On ajoute l'activité et son horaire dans l'emploi du temps
			emploiDuTemps.put(act, new GregorianCalendar(2019, 9, 15, hour, minutes, 0));
		}

		// On retourne l'emploi du temps généré
		return emploiDuTemps;
	}


	/**
	* Compte le nombre de contraintes satisfaites pour l'emploi du temps
	*
	* @param edt L'emploi du temps à tester
	* @return Le nombre de contraintes satisfaites
	*/
	public int numberOfSatisfiedConstraint(HashMap<Activity, GregorianCalendar> edt) {
		// On récupère la liste des contraintes qui ne sont pas satisfaites sur l'edt
		List<Constraint> notSatisfiedConstraint = this.verification.listOfFailConstraint(edt);
		// On compte le nombre de contraintes non satisfaites
		int numberOfNotSatisfied = notSatisfiedConstraint.size();

		// On retourne le nombre de contraintes satisfaites (en soustrayant le nombre de contraintes non satisfaites au nombre total des contraintes)
		return this.constraints.size() - numberOfNotSatisfied;
	}


	/**
	* Génère un nombre de fois un emploi du temps et retourne le meilleur
	*
	* @param numberRandomEdt Le nombre d'emploi du temps à générer
	* @return Le meilleur emploi du temps généré
	*/
	public HashMap<Activity, GregorianCalendar> edtWithMostSatisfiedConstraint(int numberRandomEdt) {
		HashMap<Activity, GregorianCalendar> bestScheduler = new HashMap<>();
		// On compte le nombre de contraintes satisfaites pour le meilleur edt
		int bestScore = 0;

		for(int i = 0 ; i <= numberRandomEdt ; i++) {
			// On génère un emploi du temps
			HashMap<Activity, GregorianCalendar> scheduler = randomSchedulerMap();
			// On récupère son score (correspondant au nombre de contraintes satisfaites)
			int score = numberOfSatisfiedConstraint(scheduler);

			// Si ce score est plus grand que le meileur score actuel
			if(score >= bestScore) {
				// Si le score est égale au nombre de contrainte il s'agit du meilleur edt possible
				if(score == this.constraints.size()) {
					return scheduler;
				}

				// On actualise le score et le meilleur emploi du temps
				bestScore = score;
				bestScheduler = scheduler;
			}
		}

		// On retourne le meilleur emploi du temps généré
		return bestScheduler;
	}


	/**
	* Ajout d'une activité à la liste d'activité
	*
	* @param activity L'activité à ajouter
	*/
	public void addActivity(Activity activity) {
		this.activities.add(activity);
	}


	/**
	* Ajout d'une contrainte à la liste de contrainte
	*
	* @param constraint La contrainte à ajouter
	*/
	public void addConstraint(Constraint constraint) {
		this.constraints.add(constraint);
		this.verification.addConstraint(constraint);
	}
}
