package edt.activity;

public class Activity {

	private String desc;
	private int duree;

	public Activity(String desc, int duree) {
		this.desc = desc;
		this.duree = duree;
	}

	public int getDuree() {
		return this.duree;
	}

	public String getDesc() {
		return this.desc;
	}

	@Override
	public String toString() {
		return "L'activité " + this.desc + " dure " + this.duree;
	}
}
