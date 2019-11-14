package edt;

import edt.activity.Activity;
import edt.constraints.*;

import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class InteractiveScheduling {

	private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

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

	/* ==================================================
							Activités
	   ================================================== */
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
		if(index-1 >= InteractiveScheduling.activities.size() || index < 1) {
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

		if(activityIndex-1 >= InteractiveScheduling.activities.size() || activityIndex < 1) {
			System.out.println("Cette activité n'existe pas");
			System.out.println();
			return;
		}

		Activity activityToDelete = InteractiveScheduling.activities.get(activityIndex-1);

		InteractiveScheduling.schedule.remove(activityToDelete);

		List<Constraint> constraintsToDelete = new ArrayList<>();
		for(Constraint c : InteractiveScheduling.constraints) {
			if(c instanceof BinaryConstraint) {
				BinaryConstraint binConstraint = (BinaryConstraint) c;
				if(binConstraint.getFirstActivity() == activityToDelete || binConstraint.getSecondActivity() == activityToDelete)
					constraintsToDelete.add(c);
			}
		}

		for(Constraint c : constraintsToDelete) {
			InteractiveScheduling.constraints.remove(c);
		}

		InteractiveScheduling.activities.remove(activityIndex-1);
	}

	/* ==================================================
							Contraintes
	   ================================================== */
	private static void manageConstraintsMenu() {
		System.out.println("");
		int choice = 0;

		do {
			System.out.println("========== Modification des contraintes ==========");
			System.out.println("1. Afficher les contraintes");
			System.out.println("2. Ajouter une contrainte");
			System.out.println("3. Supprimer une contrainte");
			System.out.println("9. Retour");

			choice = InteractiveScheduling.scanner.nextInt();
			System.out.println("");

			if(choice == 1) {
				InteractiveScheduling.showConstraints();
			} else if(choice == 2) {
				InteractiveScheduling.addConstraint();
			} else if(choice == 3) {
				InteractiveScheduling.deleteConstraint();
			} else if(choice != 9) {
				System.out.println("Le choix fait est invalide");
			}

			System.out.println();
		} while(choice != 9);
	}

	private static void showConstraints() {
		if(InteractiveScheduling.constraints.size() == 0) {
			System.out.println("Aucune contrainte de disponible");
			return;
		}

		int i = 1;
		for(Constraint c : InteractiveScheduling.constraints) {
			System.out.println(i + " - " + c);
			i++;
		}
	}

	private static void addConstraint() {
		InteractiveScheduling.clearBuffer();

		if(InteractiveScheduling.activities.size() < 2) {
			System.out.println("Vous devez d'abord ajouter au moins 2 activités");
			return;
		}

		System.out.println("Quelle contrainte voulez vous ajouter : ");
		System.out.println("1 - MeetConstraint");
		System.out.println("2 - PrecedenceConstraint");
		System.out.println("3 - PrecedenceConstraintWithGap");
		int choix = InteractiveScheduling.scanner.nextInt();

		if(choix < 1 || choix > 3) {
			System.out.println("Cette option n'existe pas");
			return;
		}

		System.out.println("Choisissez 2 activités");
		System.out.println();
		InteractiveScheduling.showActivities();
		System.out.println();

		int activite1Index = InteractiveScheduling.scanner.nextInt();
		int activite2Index = InteractiveScheduling.scanner.nextInt();

		if(activite1Index-1 > InteractiveScheduling.activities.size() || activite1Index-1 < 0) {
			System.out.println("L'activité n'existe pas");
			return;
		}

		if(activite2Index-1 > InteractiveScheduling.activities.size() || activite2Index-1 < 0) {
			System.out.println("L'activité n'existe pas");
			return;
		}

		Activity activity1 = InteractiveScheduling.activities.get(activite1Index-1);
		Activity activity2 = InteractiveScheduling.activities.get(activite2Index-1);

		if(choix == 1) {
			InteractiveScheduling.constraints.add(new MeetConstraint(activity1, activity2));
		} else if(choix == 2) {
			InteractiveScheduling.constraints.add(new PrecedenceConstraint(activity1, activity2));
		} else if(choix == 3) {
			System.out.println("Temps entre les 2 activités (mins) :");
			int gap = InteractiveScheduling.scanner.nextInt();

			if(gap < 0) {
				System.out.println("Le temps entre les 2 activités ne peut pas être négatif");
			}

			InteractiveScheduling.constraints.add(new PrecedenceConstraintWithGap(activity1, activity2, gap));
		}
	}

	private static void deleteConstraint() {
		if(InteractiveScheduling.activities.size() == 0) {
			System.out.println("Aucune contrainte de disponible");
			return;
		}

		InteractiveScheduling.showConstraints();
		System.out.println("");

		InteractiveScheduling.clearBuffer();
		System.out.println("Quelle contrainte voulez vous supprimer ?");

		int contrainteIndex = InteractiveScheduling.scanner.nextInt();

		if(contrainteIndex-1 >= InteractiveScheduling.activities.size() || contrainteIndex < 1) {
			System.out.println("Cette contrainte n'existe pas");
			System.out.println();
			return;
		}

		InteractiveScheduling.constraints.remove(contrainteIndex-1);
	}

	/* ==================================================
						Emploi du temps
	   ================================================== */
	private static void manageScheduleMenu() {
		System.out.println("");
		int choice = 0;

		do {
			System.out.println("========== Modification de l'emploi du temps ==========");
			System.out.println("1. Afficher l'emploi du temps");
			System.out.println("2. Changer une heure");
			System.out.println("3. Enlever une activité plannifié");
			System.out.println("9. Retour");

			choice = InteractiveScheduling.scanner.nextInt();
			System.out.println("");

			if(choice == 1) {
				InteractiveScheduling.showSchedule();
			} else if(choice == 2) {
				InteractiveScheduling.scheduleActivity();
			} else if(choice == 3) {
				InteractiveScheduling.deleteScheduledActivity();
			} else if(choice != 9) {
				System.out.println("Le choix fait est invalide");
			}

			System.out.println();
		} while(choice != 9);
	}

	private static void showSchedule() {
		if(InteractiveScheduling.activities.size() == 0) {
			System.out.println("Aucun emploi du temps n'existe car il n'y a aucune activité");
			return;
		}

		int i = 1;
		for(Activity a : InteractiveScheduling.activities) {
			if(InteractiveScheduling.schedule.containsKey(a))
				System.out.println(i + " - " + a + " : " + InteractiveScheduling.dateFormat.format(InteractiveScheduling.schedule.get(a).getTime()));
			else
				System.out.println(i + " - " + a + " : Non plannifié");

			i++;
		}
	}

	private static void scheduleActivity() {
		if(InteractiveScheduling.activities.size() == 0) {
			System.out.println("Aucune plannification n'est possible car il n'y a aucune activité");
			return;
		}

		InteractiveScheduling.showSchedule();
		System.out.println();

		System.out.println("Quelle activité voulez vous plannifier : ");
		int choix = InteractiveScheduling.scanner.nextInt();

		if(choix < 1 || choix-1 >= InteractiveScheduling.activities.size()) {
			System.out.println("L'activité n'existe pas");
			return;
		}

		InteractiveScheduling.clearBuffer();
		System.out.println("Entrez la date au format dd/MM/yyyy :");
		String date = InteractiveScheduling.scanner.nextLine();
		System.out.println("Entrez l'heure au format HH:mm :");
		String heure = InteractiveScheduling.scanner.nextLine();

		GregorianCalendar calendar = new GregorianCalendar();
		try {
			calendar.setTime(InteractiveScheduling.dateFormat.parse(date + " " + heure));
		} catch(ParseException e) {
			System.out.println("Le format de la date ou l'heure n'a pas été respecté");
			return;
		}

		InteractiveScheduling.schedule.put(InteractiveScheduling.activities.get(choix-1), calendar);
	}

	private static void deleteScheduledActivity() {
		if(InteractiveScheduling.activities.size() == 0) {
			System.out.println("Aucune plannification n'est possible car il n'y a aucune activité");
			return;
		}

		InteractiveScheduling.showSchedule();
		System.out.println();

		System.out.println("Quelle activité voulez vous enlever du planning : ");
		int choix = InteractiveScheduling.scanner.nextInt();

		if(choix < 1 || choix-1 >= InteractiveScheduling.activities.size()) {
			System.out.println("L'activité n'existe pas");
			return;
		}

		InteractiveScheduling.schedule.remove(InteractiveScheduling.activities.get(choix-1));
	}


	/* ==================================================
					Verification emploi du temps
	   ================================================== */
	private static void verify() {
		int i = 1;
		boolean isScheduleValid = true;
		for(Constraint c : constraints) {
			if(!c.isSatisfiedBySchedule(InteractiveScheduling.schedule)) {
				if(isScheduleValid) {
					System.out.println("Contraintes non satisfaites : ");
				}

				System.out.println(i + " - " + c);
				isScheduleValid = false;
			}

			i++;
		}

		if(isScheduleValid) {
			System.out.println("L'emploi du temps est valide");
		} else {
			System.out.println("L'emploi du temps n'est pas valide");
		}
	}

	private static void clearBuffer() {
		InteractiveScheduling.scanner.nextLine();
	}

}
