package edt.constraints;

import edt.activity.Activity;
import java.util.HashMap;
import java.util.GregorianCalendar;

public abstract class BinaryConstraint implements Constraint{

    private Activity firstActivity;
    private Activity secondActivity;

    public BinaryConstraint(Activity first, Activity second){
       this.firstActivity = first;
       this.secondActivity = second;
    }

    public abstract boolean isSatisfied(GregorianCalendar dateDebutAct1, GregorianCalendar dateDebutAct2);

    public boolean isSatisfiedBySchedule(HashMap<Activity, GregorianCalendar> edt){
       return isSatisfied(edt.get(this.getFirstActivity()),edt.get(this.getSecondActivity()));
    }

    public Activity getFirstActivity(){
      return this.firstActivity;
    }

    public Activity getSecondActivity(){
      return this.secondActivity;
    }
}
