package edt;

import edt.activity.Activity;
import edt.constraints.Constraint;
import edt.file.Reader;
import edt.scheduler.RandomScheduler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	// Constantes pour les erreurs
	private static final int ERROR_INVALID_COMMAND_USAGE = 1;
	private static final int ERROR_FILE_NOT_FOUND = 2;
	private static final int ERROR_IO_ERROR = 3;

	public static void main(String[] args) {
		if(args.length < 2 || args.length > 3) {
			showCommandUsage();
		}

		// On lit les activités
		RandomScheduler randomScheduler = new RandomScheduler();
		Reader reader = null;
		try {
			reader = new Reader(args[0], args[1]);
		} catch(FileNotFoundException e) {
			System.out.println("Le fichier " + args[0] + " n'existe pas");
			System.exit(ERROR_FILE_NOT_FOUND);
		} catch(IOException e) {
			System.out.println("Le fichier " + args[0] + " a eu une erreur de lecture");
			System.exit(ERROR_IO_ERROR);
		}

		// On lit les contraintes
		Collection<Constraint> constraints = null;
		try {
			constraints = reader.readConstraints();
		} catch(FileNotFoundException e) {
			System.out.println("Le fichier " + args[1] + " n'existe pas");
			System.exit(ERROR_FILE_NOT_FOUND);
		} catch(IOException e) {
			System.out.println("Le fichier " + args[1] + " a eu une erreur de lecture");
			System.exit(ERROR_IO_ERROR);
		}

		// On ajoute les activités à randomScheduler
		for(Activity a : reader.getActivities()) {
			randomScheduler.addActivity(a);
		}

		// On ajoute les contraintes à randomScheduler
		for(Constraint c : constraints) {
			randomScheduler.addConstraint(c);
		}

		// On génère 50 edt par défaut, on vérifie si le 3ème paramètre est présent
		int nbGenerationEdt = 50;
		if(args.length == 3) {
			try {
				nbGenerationEdt = Integer.valueOf(args[2]);
			} catch(NumberFormatException e) {
				throw new IllegalArgumentException(args[2] + " n'est pas un nombre");
			}
		}

		// On récupère le meilleur emploi du temps
		HashMap<Activity, GregorianCalendar> edt = randomScheduler.edtWithMostSatisfiedConstraint(nbGenerationEdt);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		// On affiche l'edt
		for(Map.Entry<Activity, GregorianCalendar> entry : edt.entrySet()) {
			System.out.println(entry.getKey() + " : " + dateFormat.format(entry.getValue().getTime()));
		}

		System.out.println();
		System.out.println("L'emploi du temps satisfait " + randomScheduler.numberOfSatisfiedConstraint(edt) + " contraintes");
		System.out.println("Contraintes non satisfaites : " + (constraints.size() - randomScheduler.numberOfSatisfiedConstraint(edt)));

	}

	// On affiche la commande à utiliser
	public static void showCommandUsage() {
		System.out.println("Usage : ");
		System.out.println("java ... <fichierActivités> <fichierContraintes> [nombreGenerationEdt]");
		System.exit(ERROR_INVALID_COMMAND_USAGE);
	}

}
