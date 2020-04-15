package P3;

public class Game {
	public Player player1, player2;
	public Board board;
	public String type;

	public Game(String n1, String n2, String t) {
		type = t;
		player1 = new Player(n1, type);
		player2 = new Player(n2, type);
		board = new Board(type);
		if (type.equals("Chess")) {
			for (int i = 0; i < 8; i++) {
				board.positions[i][0].SetPiece(player1.pieces[i + 8]);
				board.positions[i][1].SetPiece(player1.pieces[i]);
				board.positions[7 - i][7].SetPiece(player2.pieces[i + 8]);
				board.positions[i][6].SetPiece(player2.pieces[i]);
			}
		}
	}

	public Boolean PutPiece(String n, int x, int y, Piece p) {
		if (x < 0 || x >= board.width || y < 0 || y >= board.width) {
			System.out.println("请输入正确的位置！");
			return false;
		}
		Position po = board.positions[x][y];
		if (po.used || p.used) {
			System.out.println("这个位置已经有子了！");
			return false;
		}
		board.positions[x][y].SetPiece(p);
		p.used = true;
		whoseturn(n).getNext();
		return true;
	}

	public Boolean RemovePiece(String n, int x, int y) {
		if (x < 0 || x >= board.width || y < 0 || y >= board.width) {
			System.out.println("请输入正确的位置！");
			return false;
		}
		Position po = board.positions[x][y];
		if (!po.used) {
			System.out.println("这个位置没有子！");
			return false;
		}
		Piece p = po.here;
		if (p.whose.equals(n)) {
			System.out.println("不能移除自己的子！");
			return false;
		}
		p.used = false;
		board.positions[x][y].RemovePiece();
		return true;
	}

	public Boolean ReplacePiece(String n, int x1, int y1, int x2, int y2) {
		if (x1 == x2 && y1 == y2) {
			System.out.println("两个位置相同了！");
			return false;
		}
		if (x1 < 0 || x1 >= board.width || y1 < 0 || y1 >= board.width) {
			System.out.println("请输入正确的位置！");
			return false;
		}
		if (x2 < 0 || x2 >= board.width || y2 < 0 || y2 >= board.width) {
			System.out.println("请输入正确的位置！");
			return false;
		}
		Position po1 = board.positions[x1][y1];
		Position po2 = board.positions[x2][y2];
		if (!po1.used) {
			System.out.println("初始位置没有子！");
			return false;
		}
		Piece p = po1.here;
		if (!p.whose.equals(n)) {
			System.out.println("请移动自己的子！");
			return false;
		}
		if (po2.used) {
			po2.here.used = false;
		}
		po1.used = false;
		po2.used = true;
		po2.here = p;
		return true;
	}

	public Piece whosePiece(String name) {
		if (name.equals(player1.name))
			return player1.pieces[player1.next];
		else if (name.equals(player2.name))
			return player2.pieces[player2.next];
		else
			return null;
	}

	public Player whoseturn(String name) {
		if (name.equals(player1.name))
			return player1;
		else if (name.equals(player2.name))
			return player2;
		else
			return null;
	}

	public Player whoseturn2(String name) {
		if (name.equals(player1.name))
			return player2;
		else if (name.equals(player2.name))
			return player1;
		else
			return null;
	}
}
