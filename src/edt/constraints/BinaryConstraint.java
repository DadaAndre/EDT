package edt.constraints;

import edt.activity.Activity;
import java.util.HashMap;

public abstract class BinaryConstraint {

    private Activity firstActivity;
    private Activity secondActivity;

    public BinaryConstraint(Activity first, Activity second){
       this.firstActivity = first;
       this.secondActivity = second;
    }
    public String toString() {
      return this.firstActivity + " avant " + this.secondActivity;
    }

    public Activity getFirstActivity(){
      return this.firstActivity;
    }
    public Activity getSecondActivity(){
      return this.secondActivity;
    }
    public abstract boolean isSatisfied(int dateDebutAct1, int dateDebutAct2);

    public abstract boolean isSatisfiedBySchedule(HashMap<Activity, Integer> edt);
}
