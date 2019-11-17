package edt.activity;

/**
* Représente des créneaux non positionnés dans le temps
*/
public class Activity {

	/**
	* Description de l'activité
	*/
	private String desc;

	/**
	* Durée de l'activité
	*/
	private int duree;


	/**
	* @param desc Description de l'activité
	* @param duree Durée de l'activité
	*/
	public Activity(String desc, int duree) {
		this.desc = desc;
		this.duree = duree;
	}


	/**
	* Récupère la description de l'activité
	*
	* @return La description de l'activité
	*/
	public String getDesc() {
		return this.desc;
	}


	/**
	* Change la description de l'activité
	*
	* @param desc La description de l'activité
	*/
	public void setDesc(String desc) {
		this.desc = desc;
	}


	/**
	* Récupère la durée de l'activité
	*
	* @return La durée de l'activité
	*/
	public int getDuree() {
		return this.duree;
	}


	/**
	* Change la durée de l'activité
	*
	* @param duree La durée de l'activité
	*/
	public void setDuree(int duree) {
		this.duree = duree;
	}


	@Override
	public String toString() {
		return "L'activité " + this.desc + " dure " + this.duree + " mins";
	}
}
