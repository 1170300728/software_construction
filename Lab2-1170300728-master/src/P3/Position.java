package P3;

public class Position {
	int axis;
	int ayis;
	public Piece here;
	public Boolean used;

	public Position(int x, int y, Boolean u) {
		axis = x;
		ayis = y;
		used = u;
	}

	public void RemovePiece() {
		used = false;
	}

	public void SetPiece(Piece p) {
		used = true;
		here = p;
	}
}
