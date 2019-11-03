package edt.constraints;

import java.util.HashMap;
import edt.activity.Activity;

public interface Constraint{

  public boolean isSatisfiedBySchedule(HashMap<Activity, Integer> edt);

}
