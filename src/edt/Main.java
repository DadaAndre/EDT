package edt;

import edt.activity.*;
import edt.constraints.*;
import edt.tests.UnitTest;
import java.util.GregorianCalendar;

public class Main {
	public static void main(String[] args){

		// Objets
		Activity options = new Activity ("Choisir mes options", 90);
		Activity sport = new Activity("Sport", 120);
		Activity ip = new Activity ("Inscription pédagogique", 30);

		GregorianCalendar date1_9h = new GregorianCalendar(2019, 9, 15, 9, 0, 0);  //Année/Mois/Jour/Heure/Minute/Seconde
		GregorianCalendar date1_10h = new GregorianCalendar(2019, 9, 15, 10, 0, 0);
		GregorianCalendar date1_11h = new GregorianCalendar(2019, 9, 15, 11, 0, 0);
		GregorianCalendar date1_12h = new GregorianCalendar(2019, 9, 15, 12, 0, 0);

		PrecedenceConstraint contrainte = new PrecedenceConstraint (sport, ip);
		PrecedenceConstraintWithGap contrainteWithGap = new PrecedenceConstraintWithGap(options, ip, 60);
		PrecedenceConstraintWithGap contrainteWithGap2 = new PrecedenceConstraintWithGap(options, ip, 30);
		MeetConstraint meetConstraint = new MeetConstraint(sport, ip);

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
		UnitTest.summary();
	}

}
