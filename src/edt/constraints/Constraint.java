package edt.constraints;

import java.util.HashMap;
import edt.activity.Activity;
import java.util.GregorianCalendar;

public interface Constraint{

  public boolean isSatisfiedBySchedule(HashMap<Activity, GregorianCalendar> edt);

}
