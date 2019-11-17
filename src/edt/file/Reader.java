package edt.file;

import edt.constraints.*;
import edt.activity.Activity;

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

public class Reader {

	private Map<String, Activity> activities;
	private String constraintsFilePath;

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

	public PrecedenceConstraint getPrecedenceConstraint(String[] args) throws IllegalArgumentException {
		if(args.length != 2) {
			throw new IllegalArgumentException("Un tableau contenant l'identifiants des 2 activités est attendus");
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

	public PrecedenceConstraint getPrecedenceConstraintWithGap(String[] args) throws IllegalArgumentException {
		if(args.length != 3) {
			throw new IllegalArgumentException("Un tableau contenant l'identifiants des 2 activités et la durée d'attente entre les 2 est attendus");
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

	public MeetConstraint getMeetConstraint(String[] args) throws IllegalArgumentException {
		if(args.length != 2) {
			throw new IllegalArgumentException("Un tableau contenant l'identifiants des 2 activités est attendus");
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

	public MaxSpanConstraint getMaxSpanConstraint(String[] args) throws IllegalArgumentException {
		if(args.length < 3) {
			throw new IllegalArgumentException("Un tableau contenant l'identifiants des activités et la durée max est attendus");
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

	public Collection<Activity> getActivities() {
		return this.activities.values();
	}

}
