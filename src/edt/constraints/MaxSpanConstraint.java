package edt.constraints;

import edt.activity.Activity;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class MaxSpanConstraint implements Constraint  {
	
	private int dureeMax;
	private ArrayList<Activity> liste;
	
	public MaxSpanConstraint(int dureeMax){ 
		this.dureeMax=dureeMax;
		this.liste = new ArrayList<>();
	}

	 
	public boolean isSatisfiedBySchedule(HashMap<Activity, GregorianCalendar> edt) {
		Activity plusTot = null;
		Activity plusTard = null;
		GregorianCalendar finPlusTard = new GregorianCalendar();
		
		for(Activity a : liste) {
		
			GregorianCalendar finA = new GregorianCalendar();
			finA.setTime(edt.get(a).getTime());
			finA.add(GregorianCalendar.MINUTE, a.getDuree());
				 
			if(plusTot == null) {
				plusTot = a;
				plusTard = a;
				finPlusTard = finA;
				
				continue;
			}
			
			if(edt.get(a).compareTo(edt.get(plusTot)) < 0) {
				plusTot = a;
			}
			
			if(finA.compareTo(finPlusTard) > 0) {
				plusTard = a;
				finPlusTard = finA;
			}			
		}
		
		GregorianCalendar debutPlusTot = edt.get(plusTot);	 
		
		return (finPlusTard.getTime().getTime() - debutPlusTot.getTime().getTime())/(1000*60) <= this.dureeMax;
	}

	public void add(Activity a) {
		this.liste.add(a);
	}
	
	public void setDureeMax(int dureeMax) {
		this.dureeMax = dureeMax;
	}

}
