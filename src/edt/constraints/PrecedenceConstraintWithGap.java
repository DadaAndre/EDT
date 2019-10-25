package edt.constraints;

import edt.activity.Activity;
import java.util.HashMap;

public class PrecedenceConstraintWithGap extends PrecedenceConstraint {

  private int gap = 0;

  public PrecedenceConstraintWithGap(Activity activite1, Activity activite2, int gap){
    super(activite1, activite2);

    this.gap = gap;
  }

  @Override
  public boolean isSatisfied(int dateDebutAct1, int dateDebutAct2){
      return dateDebutAct1*60 + this.getFirstActivity().getDuree() + this.gap <= dateDebutAct2*60;
  }

  @Override
  public String toString() {
    return this.getFirstActivity() + " avec une pause de " + gap + "minutes avant " + this.getSecondActivity();
  }

  @Override
  public boolean isSatisfiedBySchedule(HashMap<Activity, Integer> edt){
     return isSatisfied(edt.get(this.getFirstActivity()),edt.get(this.getSecondActivity()));
  }

}
