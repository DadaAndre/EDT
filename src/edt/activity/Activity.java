package edt.activity;

public class Activity {

	private String desc;
	private int duree;

	public Activity(String desc, int duree) {
		this.desc = desc;
		this.duree = duree;
	}

	public String getDesc() {
		return this.desc;
	}

	public int getDuree() {
		return this.duree;
	}

	@Override
	public String toString() {
		return "L'activité " + this.desc + " dure " + this.duree;
	}
}
