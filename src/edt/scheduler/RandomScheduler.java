package edt.scheduler;

import edt.activity.Activity;
import edt.constraints.Constraint;
import edt.constraints.utils.Verifier;

import java.util.Random;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class RandomScheduler {
	private List<Constraint> constraints;
	private Set<Activity> activities;
	private Random randomGenerator;
	private HashMap<Activity, GregorianCalendar> emploiDuTemps;
	private ArrayList<Integer> randomTimeUsed;
	private Verifier verification;

	public RandomScheduler() {
		this.activities = new HashSet<>();
		this.constraints = new ArrayList<>();
		this.randomGenerator = new Random();
		this.verification = new Verifier();
	}

	public HashMap<Activity, GregorianCalendar> randomSchedulerMap() {
		//créer un tableau dynamique pour sauvegarder les valeurs déja utilisées
		this.randomTimeUsed = new ArrayList<>();
		//créer un emploi du temps par défault
		this.emploiDuTemps = new HashMap<>();

		for(Activity act : activities) {
			/*on cherche un horaire aléatoire de 8h à 19h(non compris)
			par trainche de quart d'heure qui n'est pas déja ete utilisé par une autre activité
			*/
			int randomTime = 0;
			do {
				randomTime = randomGenerator.nextInt((19-8)*4);
			} while(randomTimeUsed.contains(randomTime));

			//on ajoute ce nouvel horaire aléatoire au tableau
			randomTimeUsed.add(randomTime);

			//on lui ajoute 8 pour que l'horaire commence à 8h et non à minuit
			randomTime += 8;
			int hour = randomTime/4;
			int minutes = (randomTime%4)*15;

			//on ajoute une nouvelle activité et son horaire dans l'emploi du temps par défault
			emploiDuTemps.put(act, new GregorianCalendar(2019, 9, 15, hour, minutes, 0));
		}
		//on retourne cet emploi du temps
		return emploiDuTemps;
	}

	public int numberOfSatisfiedConstraint(HashMap<Activity, GregorianCalendar> edt) {
		//On test la liste des contraintes et on récupère celles qui ne sont aps respectées
		List<Constraint> notSatisfiedConstraint = verification.listOfFailConstraint(edt);
		//on compte le nombre de contraintes non respectées
		int numberOfNotSatisfied = notSatisfiedConstraint.size();

		//on retourne le nombre de contraintes respectées (en soustrayant le nombre de contraintes non respectées au nombre total des contraintes)
		return constraints.size() - numberOfNotSatisfied;
	}

	public HashMap<Activity, GregorianCalendar> edtWithMostSatisfiedConstraint(int numberRandomEdt) {
		//on créer une HashMap representant une activité et son horaire
		HashMap<Activity, GregorianCalendar> bestScheduler = new HashMap<>();
		//on créer un meilleur score par défault
		int bestScore = 0;

		//pour chaque nouvelle génération d'emploi du temps aléatoire
		for(int i = 0 ; i <= numberRandomEdt ; i++){
			//on génère un emploi du temps
			HashMap<Activity, GregorianCalendar> scheduler = randomSchedulerMap();
			//on affecte un score (correspondant au nombre de contraintes satisfaites)
			int score = numberOfSatisfiedConstraint(scheduler);

			//si ce score est plus grand que le meileur score actuel
			if(score >= bestScore){
				//on verifie si le nombre de contraintes est déja maximal
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

	public void addActivity(Activity activity) {
		this.activities.add(activity);
	}

	public void addConstraint(Constraint constraint) {
		this.constraints.add(constraint);
		this.verification.addConstraint(constraint);
	}
}
