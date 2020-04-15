package P3;

public class Player {
	public String name;
	Piece[] pieces;
	int next;

	public Player(String n, String type) {
		this.name = n;
		if (type.equals("Go")) {
			pieces = new Piece[2000];
			for (int i = 0; i < 2000; i++) {
				pieces[i] = new Piece("Go", i, n);
			}
			next = 0;
		} else if (type.equals("Chess")) {
			pieces = new Piece[16];
			for (int i = 0; i < 8; i++) {
				pieces[i] = new Piece("soldier", i, n);
			}
			pieces[8] = new Piece("car", 8, n);
			pieces[9] = new Piece("horse", 9, n);
			pieces[10] = new Piece("bishop", 10, n);
			pieces[11] = new Piece("queen", 11, n);
			pieces[12] = new Piece("king", 12, n);
			pieces[13] = new Piece("bishop", 13, n);
			pieces[14] = new Piece("horse", 14, n);
			pieces[15] = new Piece("car", 15, n);
			for (Piece p : pieces) {
				p.used = true;
			}
		}
	}

	public int getNext() {
		next++;
		return next - 1;
	}

}
