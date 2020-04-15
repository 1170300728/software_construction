package centralObject;

public class atomic extends centralobjects{
	float heavy;
	String name;
	
	public atomic(float h,String n) {
		heavy=h;
		name=n;
	}
	public float getheavy() {
		return heavy;
	}
	public String getName() {
		return name;
	}
}
