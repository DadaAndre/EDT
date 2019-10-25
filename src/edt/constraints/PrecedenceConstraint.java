package edt.constraints;

import java.util.HashMap;
import edt.activity.Activity;

public class PrecedenceConstraint extends BinaryConstraint {

    public PrecedenceConstraint(Activity first, Activity second){
        super(first, second);
    }

    public boolean isSatisfied(int dateDebutAct1, int dateDebutAct2){
        return dateDebutAct1*60 + this.getFirstActivity().getDuree() <= dateDebutAct2*60;
    }

    @Override
    public String toString() {
        return this.getFirstActivity() + " avant " + this.getSecondActivity();
    }
}
