package P3;

public class Board {
	public int width;
	public Position[][] positions;

	public Board(String type) {
		if (type.equals("Go")) {
			width = 19;
			MakeBoard();
		} else if (type.equals("Chess")) {
			width = 8;
			MakeBoard();
		}
	}

	public void MakeBoard() {
		positions = new Position[width][width];
		for (int i = 0; i < width; i++)
			for (int j = 0; j < width; j++)
				positions[i][j] = new Position(i, j, false);
	}

	public void scan(String name1, String name2) {
		int n1 = 0, n2 = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < width; j++) 
			if(positions[i][j].used){
				if (positions[i][j].here.whose.equals(name1))
					n1++;
				if (positions[i][j].here.whose.equals(name2))
					n2++;
			}
		}
		System.out.println(name1 + "共有" + n1 + "个子");
		System.out.println(name2 + "共有" + n2 + "个子");
	}
}
