package edt.constraints;

import edt.activity.Activity;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
/**
 * 
 */
public class MaxSpanConstraint implements Constraint  {
	/**
	 * Intervalle  de dure maximum pour faire les activités 
	 */
	private int dureeMax;
	
	/**
	 * La liste des activités
	 */
	private ArrayList<Activity> liste;
	
	
	public MaxSpanConstraint(int dureeMax){ 
		this.dureeMax=dureeMax;
		this.liste = new ArrayList<>();
	}

	/**
	 * fonction qui regarde si l'ensemble des activités rentre dans l'intervalle de temps
	 * @param edt l'emploi du temps
	 * @return false si l'ensemble des activités prend plus de temps que l'intervalle de durée, sinon true 
	 */
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
	/**
 	 * ajoute une activite a la liste
 	 */
	public void add(Activity a) {
		this.liste.add(a);
	}
	/**
     * permet de choisir l'intervalle max pour faire les activités
     */
	
	public void setDureeMax(int dureeMax) {
		this.dureeMax = dureeMax;
	}

}
