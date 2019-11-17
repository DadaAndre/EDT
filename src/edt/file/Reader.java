package edt.file;

import edt.activity.Activity;
import edt.constraints.*;

import scheduleio.ActivityDescription;
import scheduleio.ActivityReader;
import scheduleio.ConstraintDescription;
import scheduleio.ConstraintReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Permet d'obtenir la liste des activités et des contraintes en fonction de 2 fichiers fournit en paramètres
*/
public class Reader {

	/**
	* Associe une activité à un identifiant (utile pour constituer les contraintes)
	*/
	private Map<String, Activity> activities;

	/**
	* Le chemin vers le fichier des contraintes
	*/
	private String constraintsFilePath;


	/**
	* @param activitiesFilePath Chemin vers le fichier des activités
	* @param constraintsFilePath Chemin vers le fichier des contraintes
	*
	* @throws FileNotFoundException Le fichier des activités n'existe pas
	* @throws IOException La lecture du fichier des activités a rencontré une erreur
	*/
	public Reader(String activitiesFilePath, String constraintsFilePath) throws FileNotFoundException, IOException {
		this.activities = new HashMap<>();
		this.constraintsFilePath = constraintsFilePath;

		ActivityReader activityReader = new ActivityReader(activitiesFilePath);
		Map<String, ActivityDescription> activitiesFile = activityReader.readAll();

		for(Map.Entry<String, ActivityDescription> entry : activitiesFile.entrySet()) {
			ActivityDescription activityDescription = entry.getValue();
			Activity a = new Activity(activityDescription.getName(), activityDescription.getDuration());

			this.activities.put(entry.getKey(), a);
		}
	}


	/**
	* Lit le fichier des contraintes et retourne la liste
	*
	* @throws FileNotFoundException Le fichier des contraintes n'existe pas
	* @throws IOException La lecture du fichier des contraintes a rencontré une erreur
	* @return La liste des contraintes qui viennent d'être lues
	*/
	public List<Constraint> readConstraints() throws FileNotFoundException, IOException {
		List<Constraint> constraints = new ArrayList<>();
		ConstraintReader constraintReader = new ConstraintReader(this.constraintsFilePath);

		List<ConstraintDescription> constraintsFile = constraintReader.readAll();
		for(ConstraintDescription cd : constraintsFile) {
			switch(cd.getKeyword()) {
				case "PRECEDENCE":
					constraints.add(getPrecedenceConstraint(cd.getArguments()));
				break;

				case "PRECEDENCE_GAP":
					constraints.add(getPrecedenceConstraintWithGap(cd.getArguments()));
				break;

				case "MEET":
					constraints.add(getMeetConstraint(cd.getArguments()));
				break;

				case "MAX_SPAN":
					constraints.add(getMaxSpanConstraint(cd.getArguments()));
				break;

				default:
					System.out.println("Impossble d'instancier la contrainte " + cd.getKeyword() + " car le programme ne le permet pas");
			}
		}

		return constraints;
	}


	/**
	* Permet la création d'une contrainte PrecedenceConstraint
	*
	* @param args La liste des arguments pour la création de la contrainte
	* @throws IllegalArgumentException L'un des arguments pour la création de la contrainte est incorrect
	* @return La contrainte
	*/
	public PrecedenceConstraint getPrecedenceConstraint(String[] args) throws IllegalArgumentException {
		if(args.length != 2) {
			throw new IllegalArgumentException("Un tableau contenant l'identifiants des 2 activités est attendu");
		}

		if(!this.activities.containsKey(args[0])) {
			throw new IllegalArgumentException("L'activité avec l'identifiant " + args[0] + " n'existe pas");
		}

		if(!this.activities.containsKey(args[1])) {
			throw new IllegalArgumentException("L'activité avec l'identifiant " + args[1] + " n'existe pas");
		}

		Activity firstActivity = this.activities.get(args[0]);
		Activity secondActivity = this.activities.get(args[1]);

		return new PrecedenceConstraint(firstActivity, secondActivity);
	}


	/**
	* Permet la création d'une contrainte PrecedenceConstraintWithGap
	*
	* @param args La liste des arguments pour la création de la contrainte
	* @throws IllegalArgumentException L'un des arguments pour la création de la contrainte est incorrect
	* @return La contrainte
	*/
	public PrecedenceConstraint getPrecedenceConstraintWithGap(String[] args) throws IllegalArgumentException {
		if(args.length != 3) {
			throw new IllegalArgumentException("Un tableau contenant l'identifiants des 2 activités et la durée d'attente entre les 2 est attendu");
		}

		if(!this.activities.containsKey(args[1])) {
			throw new IllegalArgumentException("L'activité avec l'identifiant " + args[1] + " n'existe pas");
		}

		if(!this.activities.containsKey(args[2])) {
			throw new IllegalArgumentException("L'activité avec l'identifiant " + args[2] + " n'existe pas");
		}

		Activity firstActivity = this.activities.get(args[1]);
		Activity secondActivity = this.activities.get(args[2]);

		Integer gap = null;
		try {
			gap = Integer.valueOf(args[0]);
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException(args[0] + " n'est pas un nombre");
		}

		return new PrecedenceConstraintWithGap(firstActivity, secondActivity, gap);
	}


	/**
	* Permet la création d'une contrainte MeetConstraint
	*
	* @param args La liste des arguments pour la création de la contrainte
	* @throws IllegalArgumentException L'un des arguments pour la création de la contrainte est incorrect
	* @return La contrainte
	*/
	public MeetConstraint getMeetConstraint(String[] args) throws IllegalArgumentException {
		if(args.length != 2) {
			throw new IllegalArgumentException("Un tableau contenant l'identifiants des 2 activités est attendu");
		}

		if(!this.activities.containsKey(args[0])) {
			throw new IllegalArgumentException("L'activité avec l'identifiant " + args[0] + " n'existe pas");
		}

		if(!this.activities.containsKey(args[1])) {
			throw new IllegalArgumentException("L'activité avec l'identifiant " + args[1] + " n'existe pas");
		}

		Activity firstActivity = this.activities.get(args[0]);
		Activity secondActivity = this.activities.get(args[1]);

		return new MeetConstraint(firstActivity, secondActivity);
	}


	/**
	* Permet la création d'une contrainte MaxSpanConstraint
	*
	* @param args La liste des arguments pour la création de la contrainte
	* @throws IllegalArgumentException L'un des arguments pour la création de la contrainte est incorrect
	* @return La contrainte
	*/
	public MaxSpanConstraint getMaxSpanConstraint(String[] args) throws IllegalArgumentException {
		if(args.length < 3) {
			throw new IllegalArgumentException("Un tableau contenant l'identifiants des activités et la durée max est attendu");
		}

		Integer dureeMax = null;
		try {
			dureeMax = Integer.valueOf(args[0]);
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException(args[0] + " n'est pas un nombre");
		}

		MaxSpanConstraint maxSpanConstraint = new MaxSpanConstraint(dureeMax);
		for(int i = 1 ; i < args.length ; i++) {
			if(!this.activities.containsKey(args[i])) {
				throw new IllegalArgumentException("L'activité avec l'identifiant " + args[i] + " n'existe pas");
			}

			maxSpanConstraint.add(this.activities.get(args[i]));
		}

		return maxSpanConstraint;
	}


	/**
	* Récupère la liste des activités
	*
	* @return La liste des activités
	*/
	public Collection<Activity> getActivities() {
		return this.activities.values();
	}

}
