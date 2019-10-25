package edt.constraints;
import edt.activity.Activity;

public class MeetConstraint extends BinaryConstraint{

    public MeetConstraint(Activity first, Activity second){
        super(first, second);
    }

    @Override
    public boolean isSatisfied(int dateDebutAct1, int dateDebutAct2){
        return dateDebutAct1*60 + this.getFirstActivity().getDuree() == dateDebutAct2*60;
    }
}
