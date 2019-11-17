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

public class Test {
	public static void main(String[] args) {
		// Création des activités
		Activity options = new Activity ("Choisir mes options", 90);
		Activity sport = new Activity("Sport", 120);
		Activity ip = new Activity ("Inscription pédagogique", 30);
		Activity marche = new Activity("Balade", 75);
		Activity devoirs = new Activity("Devoirs", 60);

		// Création des horaires (Année, Mois, Jour, Heure, Minute, Seconde)
		GregorianCalendar date1_9h = new GregorianCalendar(2019, 9, 15, 9, 0, 0);
		GregorianCalendar date1_10h = new GregorianCalendar(2019, 9, 15, 10, 0, 0);
		GregorianCalendar date1_11h = new GregorianCalendar(2019, 9, 15, 11, 0, 0);
		GregorianCalendar date1_12h = new GregorianCalendar(2019, 9, 15, 12, 0, 0);
		GregorianCalendar date1_15h = new GregorianCalendar(2019, 9, 15, 15, 0, 0);
		GregorianCalendar date1_17h = new GregorianCalendar(2019, 9, 15, 17, 0, 0);
		GregorianCalendar date1_18h = new GregorianCalendar(2019, 9, 15, 18, 0, 0);

		// Initialisation de contraintes
		PrecedenceConstraint contrainte = new PrecedenceConstraint (sport, ip);
		PrecedenceConstraintWithGap contrainteWithGap = new PrecedenceConstraintWithGap(options, ip, 60);
		PrecedenceConstraintWithGap contrainteWithGap2 = new PrecedenceConstraintWithGap(options, ip, 30);
		MeetConstraint meetConstraint = new MeetConstraint(sport, ip);
		PrecedenceConstraint contrainte2 = new PrecedenceConstraint (marche, devoirs);
		PrecedenceConstraint contrainte3 = new PrecedenceConstraint (ip, devoirs);

		// Contrainte qui renvoit toujours true
		Constraint constraintTrue = new Constraint() {
			@Override
			public boolean isSatisfiedBySchedule(HashMap<Activity, GregorianCalendar> edt) {
				return true;
			}
		};

		// Contrainte qui renvoit toujours false
		Constraint constraintFalse = new Constraint() {
			@Override
			public boolean isSatisfiedBySchedule(HashMap<Activity, GregorianCalendar> edt) {
				return false;
			}
		};

		NegationConstraint negationConstraintTrue = new NegationConstraint(constraintTrue);
		NegationConstraint negationConstraintFalse = new NegationConstraint(constraintFalse);

		// Création d'un emploi du temps et ajout des activités avec l'heure à laquelle elles commencent
		HashMap<Activity, GregorianCalendar> emploiDuTemps = new HashMap<Activity, GregorianCalendar>();
		emploiDuTemps.put(sport, date1_9h);
		emploiDuTemps.put(options, date1_11h);
		emploiDuTemps.put(marche, date1_15h);
		emploiDuTemps.put(ip, date1_17h);
		emploiDuTemps.put(devoirs, date1_18h);

		// Création de Verifier pour vérifier un ensemble de contraintes
		Verifier verif = new Verifier();

		// On ajoute des contraintes à vérifier
		verif.addConstraint(new MeetConstraint(sport, options));
		verif.addConstraint(new PrecedenceConstraintWithGap(options, marche, 120));
		verif.addConstraint(new PrecedenceConstraint(marche, ip));
		verif.addConstraint(new PrecedenceConstraint(ip, devoirs));

		// Création d'un RandomScheduler pour générer un emploi du temps aléatoire
		RandomScheduler edtAleatoire = new RandomScheduler();

		// Ajout des activités à edtAleatoire
		edtAleatoire.addActivity(sport);
		edtAleatoire.addActivity(options);
		edtAleatoire.addActivity(marche);
		edtAleatoire.addActivity(ip);
		edtAleatoire.addActivity(devoirs);

		// Ajout des contraintes à edtAleatoire
		edtAleatoire.addConstraint(contrainte);
		edtAleatoire.addConstraint(contrainte2);
		edtAleatoire.addConstraint(contrainte3);
		edtAleatoire.addConstraint(contrainteWithGap);
		edtAleatoire.addConstraint(contrainteWithGap2);

		// Création de MaxSpanConstraint
		MaxSpanConstraint contrainteEnsemble = new MaxSpanConstraint(90);
		contrainteEnsemble.add(sport);
		contrainteEnsemble.add(marche);
		contrainteEnsemble.add(devoirs);

		// Création de Scheduler
		Scheduler sheduler = new Scheduler();

		// Contrainte de précédence pour Scheduler
		PrecedenceConstraint schedulerConstraint1 = new PrecedenceConstraint (ip, devoirs);
		PrecedenceConstraint schedulerConstraint2 = new PrecedenceConstraint (devoirs, marche);
		PrecedenceConstraint schedulerConstraint3 = new PrecedenceConstraint (options, marche);

		// Creation de liste de contrainte
		List<PrecedenceConstraint> allConstraints = Arrays.asList (schedulerConstraint1, schedulerConstraint2, schedulerConstraint3);
		// Comme ci-dessus mais avec l'inverse de schedulerConstraint3
		List<PrecedenceConstraint> allConstraintsFail = Arrays.asList (schedulerConstraint1, schedulerConstraint2, schedulerConstraint3, new PrecedenceConstraint (marche, options));

		/* ==================================================
							PrecedenceConstraint
		   ================================================== */
		UnitTest.setTestLabel("PrecedenceConstraint");
		// 2nde activité avant la 1ère
		UnitTest.isFalse(contrainte.isSatisfied(date1_11h, date1_9h));
		// 2nde activité pendant la 1ère
		UnitTest.isFalse(contrainte.isSatisfied(date1_9h, date1_10h));
		// 2nde activité dirrectement après la 1ère
		UnitTest.isTrue(contrainte.isSatisfied(date1_9h, date1_11h));
		// 2nde activité bien après la 1ère
		UnitTest.isTrue(contrainte.isSatisfied(date1_9h, date1_12h));

		/* ==================================================
						PrecedenceConstraintWithGap
		   ================================================== */
		UnitTest.setTestLabel("PrecedenceConstraintWithGap");
		// 2nde activité avant la 1ère
		UnitTest.isFalse(contrainteWithGap.isSatisfied(date1_11h, date1_9h));
		// 2nde activité pendant la 1ère
		UnitTest.isFalse(contrainteWithGap.isSatisfied(date1_9h, date1_10h));
		// 2nde activité pendant la pause
		UnitTest.isFalse(contrainteWithGap.isSatisfied(date1_9h, date1_11h));
		// 2nde activité pile poil après la pause
		UnitTest.isTrue(contrainteWithGap2.isSatisfied(date1_9h, date1_11h));
		// 2nde activité après la pause
		UnitTest.isTrue(contrainteWithGap.isSatisfied(date1_9h, date1_12h));

		/* ==================================================
								MeetConstraint
		   ================================================== */
		UnitTest.setTestLabel("MeetConstraint");
		// Debut de la 2nde activité trop tôt que la fin de la 1ère
		UnitTest.isFalse(meetConstraint.isSatisfied(date1_9h, date1_10h));
		// Debut de la 2nde activité en même temps que la fin de la 1ère
		UnitTest.isTrue(meetConstraint.isSatisfied(date1_9h, date1_11h));
		// Debut de la 2nde activité trop tard que la fin de la 1ère
		UnitTest.isFalse(meetConstraint.isSatisfied(date1_9h, date1_12h));

		/* ==================================================
							NegationConstraint
		   ================================================== */
		UnitTest.setTestLabel("NegationConstraint");
		// Négation de true, false attendu
		UnitTest.isFalse(negationConstraintTrue.isSatisfiedBySchedule(emploiDuTemps));
		// Négation de false, true attendu
		UnitTest.isTrue(negationConstraintFalse.isSatisfiedBySchedule(emploiDuTemps));

		/* ==================================================
								Verifier
		   ================================================== */
		UnitTest.setTestLabel("Verifier");
		// Emploi du temps qui respecte les contraintes
		UnitTest.isTrue(verif.verify(emploiDuTemps));
		// On ajoute une contrainte non valable (l'inverse de contrainte3)
		verif.addConstraint(new PrecedenceConstraint(devoirs, ip));
		// L'emploi du temps ne satisfait pas toutes les contraintes
		UnitTest.isFalse(verif.verify(emploiDuTemps));

		/* ==================================================
								Scheduler
		   ================================================== */
		UnitTest.setTestLabel("Scheduler");
		// Aucun cycle parmis les contraintes, un emploi du temps est possible
		UnitTest.isTrue(sheduler.computeSchedule(allConstraints) != null);
		// Cycle parmis les contraintes, aucun emploi du temps n'est possible
		UnitTest.isTrue(sheduler.computeSchedule(allConstraintsFail) == null);

		/* ==================================================
						DisjunctionConstraint
		   ================================================== */
		UnitTest.setTestLabel("DisjunctionConstraint");
		// Les deux contraintes sont fausses
		DisjunctionConstraint disjunctionConstraint = new DisjunctionConstraint(constraintFalse, constraintFalse);
		UnitTest.isFalse(disjunctionConstraint.isSatisfiedBySchedule(emploiDuTemps));

		// Les deux contraintes sont vrais
		disjunctionConstraint = new DisjunctionConstraint(constraintTrue, constraintTrue);
		UnitTest.isTrue(disjunctionConstraint.isSatisfiedBySchedule(emploiDuTemps));

		// Première contrainte vrai, deuxième fausse
		disjunctionConstraint = new DisjunctionConstraint(constraintTrue, constraintFalse);
		UnitTest.isTrue(disjunctionConstraint.isSatisfiedBySchedule(emploiDuTemps));

		// Deuxième contrainte vrai, première fausse
		disjunctionConstraint = new DisjunctionConstraint(constraintFalse, constraintTrue);
		UnitTest.isTrue(disjunctionConstraint.isSatisfiedBySchedule(emploiDuTemps));

		/* ==================================================
							MaxSpanConstraint
		   ================================================== */
		UnitTest.setTestLabel("MaxSpanConstraint");
		// NB : L'ensemble des activités met 10h à se dérouler.
		// La durée max de contrainteEnsemble est de 90min (cf : instanciation)
		UnitTest.isFalse(contrainteEnsemble.isSatisfiedBySchedule(emploiDuTemps));
		// On met la durée max à 10h pile
		contrainteEnsemble.setDureeMax(10*60);
		UnitTest.isTrue(contrainteEnsemble.isSatisfiedBySchedule(emploiDuTemps));
		// On met la durée max à 9h et 59 minutes.
		// Ce test permet de vérifier que la conversion réalisé dans MaxSpanConstraint est bien en minutes
		contrainteEnsemble.setDureeMax(10*60-1);
		UnitTest.isFalse(contrainteEnsemble.isSatisfiedBySchedule(emploiDuTemps));

		UnitTest.summary();
	}

}
