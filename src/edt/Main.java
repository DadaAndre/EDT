package edt;

import edt.activity.*;
import edt.constraints.*;
import edt.constraints.utils.*;
import edt.tests.UnitTest;
import edt.scheduler.*;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class Main {
	public static void main(String[] args){

		//Création des activités
		Activity options = new Activity ("Choisir mes options", 90);
		Activity sport = new Activity("Sport", 120);
		Activity ip = new Activity ("Inscription pédagogique", 30);
		Activity marche = new Activity("Balade", 75);
		Activity devoirs = new Activity("Devoirs",60);

		//Création des horaires
		GregorianCalendar date1_9h = new GregorianCalendar(2019, 9, 15, 9, 0, 0);  //Année/Mois/Jour/Heure/Minute/Seconde
		GregorianCalendar date1_10h = new GregorianCalendar(2019, 9, 15, 10, 0, 0);
		GregorianCalendar date1_11h = new GregorianCalendar(2019, 9, 15, 11, 0, 0);
		GregorianCalendar date1_12h = new GregorianCalendar(2019, 9, 15, 12, 0, 0);
		GregorianCalendar date1_15h = new GregorianCalendar(2019, 9, 15, 15, 0, 0);
		GregorianCalendar date1_17h = new GregorianCalendar(2019, 9, 15, 17, 0, 0);
		GregorianCalendar date1_18h = new GregorianCalendar(2019, 9, 15, 18, 0, 0);

		//initialisation de contraintes
		PrecedenceConstraint contrainte = new PrecedenceConstraint (sport, ip);
		PrecedenceConstraintWithGap contrainteWithGap = new PrecedenceConstraintWithGap(options, ip, 60);
		PrecedenceConstraintWithGap contrainteWithGap2 = new PrecedenceConstraintWithGap(options, ip, 30);
		MeetConstraint meetConstraint = new MeetConstraint(sport, ip);

		//Création d'un emploi du temps et ajout des activités avec l'heure à laquelle elles commencent
		HashMap<Activity, GregorianCalendar> emploiDuTemps = new HashMap<Activity, GregorianCalendar>();
		emploiDuTemps.put(sport, date1_9h);
		emploiDuTemps.put(options, date1_11h);
		emploiDuTemps.put(marche, date1_15h);
		emploiDuTemps.put(ip, date1_17h);
		emploiDuTemps.put(devoirs, date1_18h);

		// initialisation du Verifier
		Verifier verif = new Verifier();

		//On ajoute des contraintes dans la HashMap se trouvant dans la classe Verifier
		verif.addConstraint(new MeetConstraint(sport, options));
		verif.addConstraint(new PrecedenceConstraintWithGap(options, marche, 120));
		verif.addConstraint(new PrecedenceConstraint(marche, ip));
		verif.addConstraint(new PrecedenceConstraint(ip, devoirs));

		//--------------------------------------- TESTS ---------------------------------------
		UnitTest.setTestLabel("PrecedenceConstraint");
		UnitTest.isFalse(contrainte.isSatisfied(date1_11h, date1_9h)); //2nde activité avant la 1ère
		UnitTest.isFalse(contrainte.isSatisfied(date1_9h, date1_10h)); //2nde activité pendant la 1ère
		UnitTest.isTrue(contrainte.isSatisfied(date1_9h, date1_11h)); //2nde activité dirrectement après la 1ère
		UnitTest.isTrue(contrainte.isSatisfied(date1_9h, date1_12h)); //2nde activité bien après la 1ère

		UnitTest.setTestLabel("PrecedenceConstraintWithGap");
		UnitTest.isFalse(contrainteWithGap.isSatisfied(date1_11h, date1_9h)); //2nde activité avant la 1ère
		UnitTest.isFalse(contrainteWithGap.isSatisfied(date1_9h, date1_10h)); //2nde activité pendant la 1ère
		UnitTest.isFalse(contrainteWithGap.isSatisfied(date1_9h, date1_11h)); //2nde activité pendant la pause
		UnitTest.isTrue(contrainteWithGap2.isSatisfied(date1_9h, date1_11h)); //2nde activité pile poil après la pause
		UnitTest.isTrue(contrainteWithGap.isSatisfied(date1_9h, date1_12h)); //2nde activité après la pause

		UnitTest.setTestLabel("MeetConstraint");
		UnitTest.isFalse(meetConstraint.isSatisfied(date1_9h, date1_10h)); //debut de la 2nde activité trop tôt que la fin de la 1ère
		UnitTest.isTrue(meetConstraint.isSatisfied(date1_9h, date1_11h)); //debut de la 2nde activité en même temps que la fin de la 1ére
		UnitTest.isFalse(meetConstraint.isSatisfied(date1_9h, date1_12h)); //debut de la 2nde activité trop tard que la fin de la 1ère

		UnitTest.setTestLabel("Verifier");
		UnitTest.isTrue(verif.verify(emploiDuTemps)); //emploi du temps conforme avec les contraintes
		verif.addConstraint(new PrecedenceConstraint(devoirs, ip)); // On ajoute une contrainte non valable
		UnitTest.isFalse(verif.verify(emploiDuTemps)); //emploi du temps conforme avec les contraintes


		// Pour aller plus loin: ordonnancement d'activité
		PrecedenceConstraint c1 = new PrecedenceConstraint (ip, devoirs);
		PrecedenceConstraint c2 = new PrecedenceConstraint (devoirs, marche);
		PrecedenceConstraint c3 = new PrecedenceConstraint (options, marche);
		List<PrecedenceConstraint> allConstraints = Arrays.asList (c1, c2, c3); //creation d'une liste de contrainte

		Scheduler sheduler = new Scheduler();//class Scheduler

		UnitTest.setTestLabel("Schedule");
		UnitTest.isFalse(sheduler.computeSchedule(allConstraints) == null);//contraintes non organisable dans l'emploi du temps
		UnitTest.isTrue(sheduler.computeSchedule(allConstraints) != null);//contraintes organisable, creation de l'emploi du temps
		System.out.println("Un plan trouvé : " + sheduler.computeSchedule(allConstraints));//affichage de l'emploi du temps


		UnitTest.summary();
	}

}
