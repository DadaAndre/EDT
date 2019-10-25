package edt;

import edt.activity.*;
import edt.constraints.*;

public class Main {
  public static void main(String[] args){

    // Objets
    Activity options = new Activity ("Choisir mes options", 70);
    Activity ip = new Activity ("Inscription pédagogique", 30);
    Activity options2 = new Activity ("Choisir mes options", 60);
    Activity ip2 = new Activity ("Inscription pédagogique", 30);
    PrecedenceConstraint contrainte = new PrecedenceConstraint (options, ip);
    PrecedenceConstraintWithGap contrainte2 = new PrecedenceConstraintWithGap(options, ip, 30);

    int neufHeure = 9;
    int dixHeure = 10;
    int onzeHeure = 11;
    int douzeHeure = 12;

    Test testSansGap = new Test(contrainte,neufHeure,dixHeure,onzeHeure);
    Test testAvecGap = new Test(contrainte2,neufHeure,dixHeure,douzeHeure);

    MeetConstraint meetConstraint = new MeetConstraint(options2, ip2);
    System.out.println(meetConstraint.isSatisfied(neufHeure, dixHeure));
    System.out.println(meetConstraint.isSatisfied(neufHeure, onzeHeure));

  }

}
