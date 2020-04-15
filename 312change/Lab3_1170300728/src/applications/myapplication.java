package applications;

import java.util.Scanner;

public class myapplication {

	public static void main(String[] args) {
		System.out.println("选择应用：1、运动员；2、元素模型；3、人际关系");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();
		if (choice == 1)
			trytrackgame();
		else if (choice == 2) {
			tryAtomStructure();
		} else if (choice == 3) {
			trySocialNetworkCircle();
		}
	}

	public static void trytrackgame() {
		TrackGame game = new TrackGame("game");
		String filePath = "src/TrackGame.txt";
		game.readfromfile(filePath);

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		System.out.println("请选择：1、乱序比赛；2、顺序比赛；3、乱序四人接力；4、顺序四人接力");
		int type = sc.nextInt();
		autogame(game,type);
		while (true) {
			System.out.println("请选择：1、输出同心圆图；\n2、加一条跑道；\n3、在一条跑道上加一个运动员；\n4、减一条跑道；\n5、在一条跑道上减一个运动员；\n6、计算熵值；\n0、退出");
			int choice = sc.nextInt();
			if (choice == 1) {
				game.drawgraph();
			} else if (choice == 2) {
				game.addtrack();
				autogame(game,type);
			} else if (choice == 3) {
				float r = sc.nextFloat();
				String n = sc.next();
				game.addtotrack(r, n);
				autogame(game,type);
			} else if (choice == 4) {
				game.removetrack();
				autogame(game,type);
			} else if (choice == 5) {
				float r = sc.nextFloat();
				String n = sc.next();
				game.removeEfromtrack(r, n);
				autogame(game,type);
			} else if (choice == 6) {
				game.getEntropy();
			} else if (choice == 0) {
				return;
			}
			System.out.println();
		}
	}
	public static void autogame(TrackGame game,int type) {
		if (type == 1) {
			game.autogame1();
		}
		if (type == 2) {
			game.autogame2();
		}
		if (type == 3) {
			game.autogame3();
		}
		if (type == 4) {
			game.autogame4();
		}
	}

	public static void tryAtomStructure() {
		AtomStructure atoms = new AtomStructure("atom");
		String filePath = "src/AtomicStructure.txt";
		atoms.readfromfile(filePath);
		atoms.createcir();

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println(
					"请选择：1、输出同心圆图；\n2、加一条轨道；\n3、在一条轨道上上加一个电子；\n4、减一条轨道；\n5、在一条轨道上减一个电子；\n6、计算熵值；\n7、电子跃迁；\n0、退出");
			int choice = sc.nextInt();
			if (choice == 1) {
				atoms.drawgraph();
			} else if (choice == 2) {
				float r = sc.nextFloat();
				atoms.addtrack(r);
			} else if (choice == 3) {
				float r = sc.nextFloat();
				atoms.addtotrack(r);
			} else if (choice == 4) {
				float r = sc.nextFloat();
				atoms.removetrack(r);
			} else if (choice == 5) {
				float r = sc.nextFloat();
				atoms.removeEfromtrack(r);
			} else if (choice == 6) {
				atoms.getEntropy();
			} else if (choice == 7) {
				float r1 = sc.nextFloat();
				float r2 = sc.nextFloat();
				atoms.moveatob(r1, r2);
			} else if (choice == 0) {
			
				return;
			}
			System.out.println();
		}
	}

	public static void trySocialNetworkCircle() {
		SocialNetworkCircle soci = new SocialNetworkCircle("social");
		String filePath = "src/SocialNetworkCircle.txt";
		soci.readfromfile(filePath);
		soci.makemap();
		soci.createcir();

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println(
					"请选择：1、输出同心圆图；\n2、加一条轨道；\n3、在一条轨道上上加一个朋友；\n4、减一条轨道；\n5、在一条轨道上减一个朋友；\n6、计算熵值；\n7、添加一个关系；\n8、删除一个关系\n9、计算两人之间的逻辑距离；\n0、退出");
			int choice = sc.nextInt();
			if (choice == 1) {
				soci.drawgraph();
			} else if (choice == 2) {
				float r = sc.nextFloat();
				soci.addtrack(r);
			} else if (choice == 3) {
				float r = sc.nextFloat();
				String n = sc.next();
				int a = sc.nextInt();
				String s = sc.next();
				soci.addtotrack(r, n, a, s);
			} else if (choice == 4) {
				float r = sc.nextFloat();
				soci.removetrack(r);
			} else if (choice == 5) {
				float r = sc.nextFloat();
				String n = sc.next();
				soci.removeEfromtrack(r, n);
			} else if (choice == 6) {
				soci.getEntropy();
			} else if (choice == 7) {
				String n1 = sc.next();
				String n2 = sc.next();
				String r = sc.next();
				soci.addrelation(n1, n2, r);
			} else if (choice == 8) {
				String n1 = sc.next();
				String n2 = sc.next();
				soci.removerelation(n1, n2);
			}else if(choice==9){
				String n1 = sc.next();
				String n2 = sc.next();
				soci.LogicalDistance(n1, n2);
			} else if (choice == 0) {
				return;
			}
			System.out.println(soci.getTrackMap().keySet().size());
		}
	}
}
