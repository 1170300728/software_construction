package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class MyChessAndGoGameTest {
	String name1 = "ttn";
	String name2 = "TTN";
	String name3 = "lalala";

	String target1 = "black";
	String target2 = "white";
	String target3 = "soldier";
	String target4 = "queen";
	String target5 = "king";

	String type1 = "Chess";
	String type2 = "Go";

	int i0 = 0;
	int i1 = 1;
	int i2 = 2;

	int x1 = 0;
	int x2 = 1;
	int x3 = 7;
	int x4 = 18;

	int y1 = 0;
	int y2 = 1;
	int y3 = 7;
	int y4 = 18;

	@Test
	public void PositionTest() {
		Piece p1 = new Piece(target1, i0, name1);
		Piece p2 = new Piece(target2, i1, name2);

		Position po = new Position(x1, y1, false);
		po.SetPiece(p1);
		assertEquals(true, po.used);
		po.RemovePiece();
		assertEquals(false, po.used);
		po.SetPiece(p2);
		assertEquals(p2, po.here);
	}

	@Test
	public void BoardTest() {
		Board b1 = new Board(type1);
		assertEquals(8, b1.width);

		Board b2 = new Board(type2);
		assertEquals(19, b2.width);
	}

	@Test
	public void PlayerTest() {
		Player p1 = new Player(name1, type1);
		assertEquals(16, p1.pieces.length);
		Player p2 = new Player(name2, type2);
		assertEquals(2000, p2.pieces.length);
		p1.getNext();
		p1.getNext();
		p1.getNext();
		p1.getNext();
		p1.getNext();
		assertEquals(5, p1.next);
	}

	@Test
	public void GameTest() {
		Piece p1 = new Piece(target2, i0, name2);
		Piece p2 = new Piece(target2, i1, name2);
		Piece p3 = new Piece(target1, i0, name1);

		Game g1 = new Game(name1, name2, type1);
		assertEquals(name2, g1.board.positions[x3][y3].here.whose);
		g1.ReplacePiece(name1, x1, y1, x3, y3);
		assertEquals(name1, g1.board.positions[x3][y3].here.whose);

		Game g2 = new Game(name1, name2, type2);
		assertEquals(false, g2.board.positions[x1][y1].used);
		g2.PutPiece(name2, x1, y1, p1);
		assertEquals(p1, g2.board.positions[x1][y1].here);
		g2.PutPiece(name2, x4, y4, p2);
		assertEquals(p2, g2.board.positions[x4][y4].here);
		g2.RemovePiece(name1, x1, y1);
		assertEquals(false, g2.board.positions[x1][y1].used);
		g2.PutPiece(name2, x1, y1, p3);
		assertEquals(p3, g2.board.positions[x1][y1].here);
	}

	@Test
	public void ActionTest() {
		Action a = new Action(name1, name2);

		assertEquals(0, a.List1.size());
		assertEquals(0, a.List2.size());

		a.add(name1, target1);
		a.add(name1, target2);
		a.add(name1, target3);
		a.add(name1, target4);
		a.add(name1, target5);
		a.add(name2, target1);
		a.add(name2, target2);
		a.add(name2, target3);
		a.add(name3, target1);
		a.add(name3, target2);
		assertEquals(5, a.List1.size());
		assertEquals(3, a.List2.size());
	}
}
