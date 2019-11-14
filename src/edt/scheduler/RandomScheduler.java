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
 * Génération de plusieurs emplois du temps et renvoie le meilleur (celui qui satisfait le plus de contraintes)
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
	 * Hashmap associant une activité et son horaire de début
	 */
	private HashMap<Activity, GregorianCalendar> emploiDuTemps;	
	
	/**
	 * Liste d'entier pour sauvegarder les horaires utilisés
	 */
	private ArrayList<Integer> randomTimeUsed; 
	
	/**
	* Implémentation de la classe de vérification 
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
		//créer un tableau dynamique pour sauvegarder les créneaux horaires déja utilisées
		this.randomTimeUsed = new ArrayList<>();
		//créer un emploi du temps par défault, vide
		this.emploiDuTemps = new HashMap<>();

		for(Activity act : this.activities) {
			/*on cherche un horaire aléatoire de 8h à 19h(non compris)
			par tranche de quart d'heure qui n'est pas déja ete utilisé par une autre activité
			*/
			int randomTime = 0;
			do {
				randomTime = this.randomGenerator.nextInt((19-8)*4);
			} while(this.randomTimeUsed.contains(randomTime));

			//on ajoute ce nouvel horaire aléatoire au tableau
			this.randomTimeUsed.add(randomTime);

			//on lui ajoute 8 pour que l'horaire commence à 8h et non à minuit
			randomTime += 8;
			int hour = randomTime/4;
			int minutes = (randomTime%4)*15;

			//on ajoute une nouvelle activité et son horaire dans l'emploi du temps
			emploiDuTemps.put(act, new GregorianCalendar(2019, 9, 15, hour, minutes, 0));
		}
		//on retourne cet emploi du temps
		return emploiDuTemps;
	}
	
	
	/**
	 * Compte le nombre de contraintes satisfaites pour l'emploi du temps
	 *
	 * @param edt L'emploi du temps à tester
	 * @return Le nombre de contraintes satisfaites
	 */
	public int numberOfSatisfiedConstraint(HashMap<Activity, GregorianCalendar> edt) {
		//On test la liste des contraintes et on récupère celles qui ne sont pas respectées
		List<Constraint> notSatisfiedConstraint = this.verification.listOfFailConstraint(edt);
		//on compte le nombre de contraintes non respectées
		int numberOfNotSatisfied = notSatisfiedConstraint.size();

		//on retourne le nombre de contraintes respectées (en soustrayant le nombre de contraintes non respectées au nombre total des contraintes)
		return this.constraints.size() - numberOfNotSatisfied;
	}


	/**
	 * Génère un nombre de fois un emploi du temps et retourne le meilleur 
	 *
	 * @param numberRandomEdt Le nombre d'emploi du temps à générer
	 * @return Le meilleur emploi du temps généré 
	 */
	public HashMap<Activity, GregorianCalendar> edtWithMostSatisfiedConstraint(int numberRandomEdt) {
		//on créer une HashMap representant une activité et son horaire
		HashMap<Activity, GregorianCalendar> bestScheduler = new HashMap<>();
		//on créer un meilleur score par défault. La variable bestScore compte le nombre de contraintes satisfaites
		// et sauvegarde le maximum de contraintes satisfaites atteint. 
		int bestScore = 0;

		//pour chaque nouvelle génération d'emploi du temps aléatoire
		for(int i = 0 ; i <= numberRandomEdt ; i++){
			//on génère un emploi du temps
			HashMap<Activity, GregorianCalendar> scheduler = randomSchedulerMap();
			//on affecte un score (correspondant au nombre de contraintes satisfaites)
			int score = numberOfSatisfiedConstraint(scheduler);

			//si ce score est plus grand que le meileur score actuel
			if(score >= bestScore){
				//on verifie si toutes les contraintes sont maximales
				if(score == this.constraints.size()) {
					//
					return scheduler;
				}
				//on actualise le score et le meilleur emploi du temps
				bestScore = score;
				bestScheduler = scheduler;
			}
		}
		//on retourne le meilleur emploi du temps
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
	* Ajout d'une contrainte à la liste de contrainte et à la liste de contrainte dans la classe Verify
	*
	* @param constraint La contrainte à ajouter
	*/
	public void addConstraint(Constraint constraint) {
		this.constraints.add(constraint);
		this.verification.addConstraint(constraint);
	}
}
