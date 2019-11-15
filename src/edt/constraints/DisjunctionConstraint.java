package edt.constraints;

import edt.activity.Activity;
import java.util.HashMap;
import java.util.GregorianCalendar;
import java.util.Map;

public class DisjunctionConstraint implements Constraint{

	PrecedenceConstraint c1;
	PrecedenceConstraint c2;

    public DisjunctionConstraint(PrecedenceConstraint c1, PrecedenceConstraint c2){
        this.c1 = c1;
        this.c2 = c2;
    }

    public boolean isSatisfiedBySchedule(HashMap<Activity,GregorianCalendar> edt){;
		return c1.isSatisfiedBySchedule(edt) || c2.isSatisfiedBySchedule(edt);
	}




}
