package physicalObject;

public final class Athlete {
	private String name;
	private int number;
	private String nation;
	private int age;
	private float best;
	
	public Athlete(String na,int nu,String n,int a,float b) {
		name=na;
		number=nu;
		nation=n;
		age=a;
		best=b;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumber() {
		return number;
	}
	
	public String getNation() {
		return nation;
	}
	
	public int getAge() {
		return age;
	}
	
	public float getBest() {
		return best;
	}
}
