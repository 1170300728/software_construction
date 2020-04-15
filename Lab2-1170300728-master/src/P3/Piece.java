package P3;

public class Piece {
	public String target;
	public String whose;
	public int number;
	public Boolean used;

	public Piece(String t, int n, String w) {
		this.target = t;
		this.whose = w;
		this.number = n;
		this.used = false;
	}
}
