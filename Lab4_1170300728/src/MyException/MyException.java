package MyException;

public class MyException extends Exception {
	private String what;
	
	public MyException(String s) {
		super();
		what=s;
	}
	
	public String getWhat() {
		return what;
	}
}
