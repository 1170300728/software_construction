package centralObject;

public class CentralUser extends centralobjects {
	private String name;
	private int age;
	private String sex;
	
	public CentralUser(String n,int a,String s) {
		name=n;
		age=a;
		sex=s;
	}
	
	public String getName() {
		return name;
	}
	
	public int getAge() {
		return age;
	}
	
	public String getSex() {
		return sex;
	}
}
