package edt;

import edt.activity.Activity;
import edt.constraints.Constraint;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class InteractiveScheduling {

	private static Scanner scanner;

	private static List<Activity> activities;
	private static List<Constraint> constraints;
	private static HashMap<Activity, GregorianCalendar> schedule;

	public static void main(String[] args) {
		InteractiveScheduling.showMenu();
	}

	public static void showMenu() {
		InteractiveScheduling.activities = new ArrayList<>();
		InteractiveScheduling.constraints = new ArrayList<>();
		InteractiveScheduling.schedule = new HashMap<>();

		InteractiveScheduling.scanner = new Scanner(System.in);

		int choice = 0;

		do {
			System.out.println("========== InteractiveScheduling ==========");
			System.out.println("1. Modification des activités");
			System.out.println("2. Modification des contraintes");
			System.out.println("3. Modification de l'emploi du temps");
			System.out.println("4. Vérification de l'emploi du temps");
			System.out.println("9. Quitter");

			choice = InteractiveScheduling.scanner.nextInt();
			System.out.println("");

			if(choice == 1) {
				InteractiveScheduling.manageActivitiesMenu();
			} else if(choice == 2) {
				InteractiveScheduling.manageConstraintsMenu();
			} else if(choice == 3) {
				InteractiveScheduling.manageScheduleMenu();
			} else if(choice == 4) {
				InteractiveScheduling.verify();
			} else if(choice != 9) {
				System.out.println("Le choix fait est invalide");
			}

			System.out.println("");
		} while(choice != 9);

		scanner.close();
	}

	private static void manageActivitiesMenu() {
		System.out.println("");
		int choice = 0;

		do {
			System.out.println("========== Modification des activités ==========");
			System.out.println("1. Afficher les activités");
			System.out.println("2. Ajouter une activité");
			System.out.println("3. Modifier une activité");
			System.out.println("4. Supprimer une activité");
			System.out.println("9. Retour");

			choice = InteractiveScheduling.scanner.nextInt();
			System.out.println("");

			if(choice == 1) {
				InteractiveScheduling.showActivities();
			} else if(choice == 2) {
				InteractiveScheduling.addActivity();
			} else if(choice == 3) {
				InteractiveScheduling.updateActivity();
			} else if(choice == 4) {
				InteractiveScheduling.deleteActivity();
			} else if(choice != 9) {
				System.out.println("Le choix fait est invalide");
			}

			System.out.println();
		} while(choice != 9);
	}

	private static void showActivities() {
		if(InteractiveScheduling.activities.size() == 0) {
			System.out.println("Aucune activité de disponible");
			return;
		}

		int i = 1;
		for(Activity a : InteractiveScheduling.activities) {
			System.out.println(i + " - " + a.getDesc() + ", " + a.getDuree() + "mins");
			i++;
		}
	}

	private static void addActivity() {
		InteractiveScheduling.clearBuffer();

		System.out.print("Nom de l'activité : ");
		String desc = InteractiveScheduling.scanner.nextLine();

		System.out.print("Durée de l'activité (min): ");
		int duree = InteractiveScheduling.scanner.nextInt();

		if(duree < 0) {
			System.out.println("La durée ne peut pas être négative");
			return;
		}

		activities.add(new Activity(desc, duree));
	}

	private static void updateActivity() {
		if(InteractiveScheduling.activities.size() == 0) {
			System.out.println("Aucune activité de disponible");
			return;
		}

		InteractiveScheduling.showActivities();
		System.out.println("");

		System.out.println("Quelle activité souhaitez vous modifier ?");
		int index = InteractiveScheduling.scanner.nextInt();
		if(index-1 >= InteractiveScheduling.activities.size()) {
			System.out.println("Cette activité n'existe pas");
			return;
		}

		Activity selectedActivity = InteractiveScheduling.activities.get(index-1);

		InteractiveScheduling.clearBuffer();

		System.out.print("Nom de l'activité ["+selectedActivity.getDesc()+"] : ");
		String desc = InteractiveScheduling.scanner.nextLine();

		System.out.print("Durée de l'activité (min) ["+selectedActivity.getDuree()+"]: ");
		Integer duree = null;
		try {
			duree = Integer.parseInt(InteractiveScheduling.scanner.nextLine());
		} catch(NumberFormatException e) {}

		if(duree != null && duree < 0) {
			System.out.println("La durée ne peut pas être négative");
			return;
		}

		if(!desc.isEmpty()) {
			selectedActivity.setDesc(desc);
		}

		if(duree != null) {
			selectedActivity.setDuree(duree);
		}
	}

	private static void deleteActivity() {
		if(InteractiveScheduling.activities.size() == 0) {
			System.out.println("Aucune activité de disponible");
			return;
		}

		InteractiveScheduling.showActivities();
		System.out.println("");

		InteractiveScheduling.clearBuffer();
		System.out.println("Quelle activité voulez vous supprimer ?");

		int activityIndex = InteractiveScheduling.scanner.nextInt();

		if(activityIndex-1 >= InteractiveScheduling.activities.size()) {
			System.out.println("Cette activité n'existe pas");
			System.out.println();
			return;
		}

		InteractiveScheduling.activities.remove(activityIndex-1);
	}

	private static void manageConstraintsMenu() {

	}

	private static void manageScheduleMenu() {

	}

	private static void verify() {
		for(Constraint c : constraints) {

		}
	}

	private static void clearBuffer() {
		InteractiveScheduling.scanner.nextLine();
	}

}
