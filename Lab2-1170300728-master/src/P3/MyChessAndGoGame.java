package P3;

import java.util.Scanner;

public class MyChessAndGoGame {
	public static Game thisgame;
	public static Action thisaction;

	static String type;
	static String name1;
	static String name2;
	static Scanner sc;

	public static void main(String args[]) {
		StartGame();
		if (type.equals("Chess"))
			Chesstime();
		else if (type.equals("Go"))
			Gotime();
		System.out.println("真是一场精彩的对局！");
		thisaction.printlist(name1);
		thisaction.printlist(name2);
	}

	public static void StartGame() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("请玩家就位，并选择游戏类型（输入Chess或者Go）：");
		type = sc.nextLine();
		System.out.println("请玩家们输入各自的名字（用空格分割）：");
		name1 = sc.next();
		name2 = sc.next();
		thisgame = new Game(name1, name2, type);
		thisaction = new Action(name1, name2);
	}

	public static void Chesstime() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("请玩家输入当前玩家的姓名\n" + "或者输入“scan”查询双方落子数量\n" + "或者输入“find”查找某给位置的棋子信息\n" + "或者输入“skip”跳过\n"
					+ "或者输入“end”结束游戏：");
			String name = sc.next();
			if (name.equals("end")) {
				return;
			} else if (name.equals("scan")) {
				thisgame.board.scan(name1, name2);
			} else if (name.equals("find")) {
				System.out.println("请玩家输入查找位置的横纵坐标，用空格分割");
				int x = sc.nextInt();
				int y = sc.nextInt();
				if (x < 0 || x >= thisgame.board.width || y < 0 || y >= thisgame.board.width)
					System.out.println("请输入正确的位置！");
				Position po = thisgame.board.positions[x][y];
				if (po.used)
					System.out.println("这个位置的棋子是" + po.here.whose + "的" + po.here.target + "子");
				else
					System.out.println("这个位置没有棋子");
			} else if (name.equals("skip")) {
				continue;
			} else {
				System.out.println("请玩家输入移动棋子的第一个位置的横纵坐标、第二个位置的横纵坐标，用空格分割");
				int x1 = sc.nextInt();
				int y1 = sc.nextInt();
				int x2 = sc.nextInt();
				int y2 = sc.nextInt();
				thisgame.ReplacePiece(name, x1, y1, x2, y2);
				String s = " " + x1 + " " + y1 + " " + x2 + " " + y2;
				thisaction.add(name, s);
			}
			System.out.println();
		}
	}

	public static void Gotime() {

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("请玩家输入名字和数字来选择一个操作（用空格分割）：\n" + "1、放棋；\n" + "2、提子；\n" + "3、查询双方落子数量\n"
					+ "4、查找某给位置的棋子信息\n" + "5、跳过\n" + "或者输入“end”结束游戏：");
			String name = sc.next();
			if (name.equals("end")) {
				return;
			}
			Piece p = thisgame.whosePiece(name);
			if (p.equals(null)) {
				System.out.println("请输入正确的名字！");
				System.out.println();
				continue;
			}
			int choice = sc.nextInt();
			if (choice == 1) {
				System.out.println("请玩家输入落子位置（用空格分割）：");
				int x = sc.nextInt();
				int y = sc.nextInt();
				thisgame.PutPiece(name, x, y, p);
				String s = " 1 " + x + " " + y;
				thisaction.add(name, s);
			} else if (choice == 2) {
				System.out.println("请玩家输入提子位置（用空格分割）：");
				int x = sc.nextInt();
				int y = sc.nextInt();
				String s = " 2 " + x + " " + y;
				thisaction.add(name, s);
				thisgame.RemovePiece(name, x, y);
			} else if (choice == 3) {
				thisgame.board.scan(name1, name2);
			} else if (choice == 4) {
				System.out.println("请玩家输入查找位置的横纵坐标，用空格分割");
				int x = sc.nextInt();
				int y = sc.nextInt();
				if (x < 0 || x >= thisgame.board.width || y < 0 || y >= thisgame.board.width)
					System.out.println("请输入正确的位置！");
				Position po = thisgame.board.positions[x][y];
				if (po.used)
					if (po.here.whose.equals(name1))
						System.out.println("这个位置的棋子是" + po.here.whose + "的黑子");
					else if (po.here.whose.equals(name1))
						System.out.println("这个位置的棋子是" + po.here.whose + "的白子");
					else
						System.out.println("这个位置没有棋子");
			}
			System.out.println();
		}
	}
}
