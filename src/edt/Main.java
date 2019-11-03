package edt;

import edt.activity.*;
import edt.constraints.*;
import edt.tests.UnitTest;

public class Main {
	public static void main(String[] args){

		// Objets
		Activity options = new Activity ("Choisir mes options", 90);
		Activity sport = new Activity("Sport", 120);
		Activity ip = new Activity ("Inscription pédagogique", 30);

		int neufHeure = 9;
		int dixHeure = 10;
		int onzeHeure = 11;
		int douzeHeure = 12;

		PrecedenceConstraint contrainte = new PrecedenceConstraint (sport, ip);
		PrecedenceConstraintWithGap contrainteWithGap = new PrecedenceConstraintWithGap(options, ip, 60);
		PrecedenceConstraintWithGap contrainteWithGap2 = new PrecedenceConstraintWithGap(options, ip, 30);
		MeetConstraint meetConstraint = new MeetConstraint(sport, ip);

		UnitTest.setTestLabel("PrecedenceConstraint");
		UnitTest.isFalse(contrainte.isSatisfied(neufHeure, dixHeure));
		UnitTest.isTrue(contrainte.isSatisfied(neufHeure, onzeHeure));
		UnitTest.isTrue(contrainte.isSatisfied(neufHeure, douzeHeure));

		UnitTest.setTestLabel("PrecedenceConstraintWithGap");
		UnitTest.isFalse(contrainteWithGap.isSatisfied(neufHeure, dixHeure)); //2nde activité pendant la 1ère
		UnitTest.isFalse(contrainteWithGap.isSatisfied(neufHeure, onzeHeure)); //2nde activité pendant la pause
		UnitTest.isTrue(contrainteWithGap2.isSatisfied(neufHeure, onzeHeure)); //2nde activité pile poil après la pause
		UnitTest.isTrue(contrainteWithGap.isSatisfied(neufHeure, douzeHeure)); //2nde activité après la pause

		UnitTest.setTestLabel("MeetConstraint");
		UnitTest.isFalse(meetConstraint.isSatisfied(neufHeure, dixHeure)); //2nde activité trop tôt
		UnitTest.isTrue(meetConstraint.isSatisfied(neufHeure, onzeHeure)); //2nde activité en même temps
		UnitTest.isFalse(meetConstraint.isSatisfied(neufHeure, douzeHeure)); //2nde activité trop tard
		UnitTest.summary();
	}

}
