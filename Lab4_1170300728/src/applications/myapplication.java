package applications;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class myapplication {
	static List<TrackGame> gs = new ArrayList<>();
	static List<AtomStructure> as = new ArrayList<>();
	static List<SocialNetworkCircle> ss = new ArrayList<>();
	static Logger logger = Logger.getLogger("1170300728");

	public static void main(String[] args) {
		while (true) {
			System.out.println("选择应用：1、运动员；2、元素模型；3、人际关系；4退出");
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			if (choice == 1) {
				logger.info("创建一个trackgame");
				trytrackgame();
			} else if (choice == 2) {
				logger.info("创建一个AtomStructure");
				tryAtomStructure();
			} else if (choice == 3) {
				logger.info("创建一个SocialNetworkCircle");
				trySocialNetworkCircle();
			} else if (choice == 4) {
				System.out.println("感谢您的使用！");
				sc.close();
				return;
			}
		}
	}

	/**
	 * 创建一个trackgame
	 */
	public static void trytrackgame() {
		TrackGame game = new TrackGame("game");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		String filePath = "src/TrackGame.txt";
		while (!game.readfromfile(filePath)) {
			System.out.println("读取文件出错请重新输入文件路径");
			filePath = sc.nextLine();
		}

		System.out.println("请选择：1、乱序比赛；2、顺序比赛");
		int type = sc.nextInt();
		logger.info("trackgame赛制选择");
		autogame(game, type);
		while (true) {
			System.out.println("请选择：1、输出同心圆图；\n2、加一条跑道；\n3、在一条跑道上加一个运动员；\n4、减一条跑道；\n5、在一条跑道上减一个运动员；\n6、计算熵值；\n0、退出");
			int choice = sc.nextInt();
			if (choice == 1) {
				logger.info("trackgame画图");
				game.drawgraph();
			} else if (choice == 2) {
				logger.info("trackgame添加一条跑道");
				game.addtrack();
				autogame(game, type);
			} else if (choice == 3) {
				float r = sc.nextFloat();
				logger.info("trackgame向轨道添加一个运动员");
				String n = sc.next();
				game.addtotrack(r, n);
				autogame(game, type);
			} else if (choice == 4) {
				logger.info("trackgame删除一条轨道");
				game.removetrack();
				autogame(game, type);
			} else if (choice == 5) {
				float r = sc.nextFloat();
				String n = sc.next();
				logger.info("trackgame从一条轨道上删除一个运动员");
				game.removeEfromtrack(r, n);
				autogame(game, type);
			} else if (choice == 6) {
				logger.info("trackgame获取熵值");
				game.getEntropy();
			} else if (choice == 0) {
				logger.info("trackgame退出");
				gs.add(game);
				return;
			}
			System.out.println();
		}
	}

	/**
	 * 根据所选赛制建立轨道模型
	 * 
	 * @param game 本次田径比赛模型
	 * @param type 赛制编号
	 */
	public static void autogame(TrackGame game, int type) {
		if (type == 1) {
			game.autogame1();
		}
		if (type == 2) {
			game.autogame2();
		}
	}

	/**
	 * 创建一个atomstructure
	 */
	public static void tryAtomStructure() {
		AtomStructure atoms = new AtomStructure("atom");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String filePath = "src/AtomicStructure.txt";
		while (!atoms.readfromfile(filePath)) {
			System.out.println("读取文件出错请重新输入文件路径");
			filePath = sc.nextLine();
		}
		atoms.createcir();

		while (true) {
			System.out.println(
					"请选择：1、输出同心圆图；\n2、加一条轨道；\n3、在一条轨道上上加一个电子；\n4、减一条轨道；\n5、在一条轨道上减一个电子；\n6、计算熵值；\n7、电子跃迁；\n0、退出");
			int choice = sc.nextInt();
			if (choice == 1) {
				logger.info("AtomicStructure画图");
				atoms.drawgraph();
			} else if (choice == 2) {
				float r = sc.nextFloat();
				logger.info("AtomicStructure添加一条轨道");
				atoms.addAtrack(r);
			} else if (choice == 3) {
				float r = sc.nextFloat();
				logger.info("AtomicStructure向一条轨道上添加一个电子");
				atoms.addtotrack(r);
			} else if (choice == 4) {
				float r = sc.nextFloat();
				logger.info("AtomicStructure删除一条轨道");
				atoms.removeAtrack(r);
			} else if (choice == 5) {
				float r = sc.nextFloat();
				logger.info("AtomicStructure从一个轨道上删除一个电子");
				atoms.removeEfromtrack(r);
			} else if (choice == 6) {
				logger.info("AtomicStructure获取熵值");
				atoms.getEntropy();
			} else if (choice == 7) {
				float r1 = sc.nextFloat();
				float r2 = sc.nextFloat();
				logger.info("AtomicStructure电子跃迁");
				atoms.moveatob(r1, r2);
			} else if (choice == 0) {
				logger.info("AtomicStructure退出");
				as.add(atoms);
				return;
			}
			System.out.println();
		}
	}

	/**
	 * 创建一个SocialNetworkCircle
	 */
	public static void trySocialNetworkCircle() {
		SocialNetworkCircle soci = new SocialNetworkCircle("social");
		String filePath = "src/SocialNetworkCircle.txt";
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		while (!soci.readfromfile(filePath)) {
			System.out.println("读取文件出错请重新输入文件路径");
			filePath = sc.nextLine();
		}
		soci.makemap();
		soci.createcir();

		while (true) {
			System.out.println(
					"请选择：1、输出同心圆图；\n2、加一条轨道；\n3、在一条轨道上上加一个朋友；\n4、减一条轨道；\n5、在一条轨道上减一个朋友；\n6、计算熵值；\n7、添加一个关系；\n8、删除一个关系\n9、计算两人之间的逻辑距离；\n0、退出");
			int choice = sc.nextInt();
			if (choice == 1) {
				logger.info("SocialNetworkCircle画图");
				soci.drawgraph();
			} else if (choice == 2) {
				float r = sc.nextFloat();
				logger.info("SocialNetworkCircle添加一条轨道");
				soci.addTrack(r);
			} else if (choice == 3) {
				float r = sc.nextFloat();
				String n = sc.next();
				int a = sc.nextInt();
				String s = sc.next();
				logger.info("SocialNetworkCircle向一条轨道上添加一个人");
				soci.addtotrack(r, n, a, s);
			} else if (choice == 4) {
				float r = sc.nextFloat();
				logger.info("SocialNetworkCircle删除一条轨道");
				soci.removeTrack(r);
			} else if (choice == 5) {
				float r = sc.nextFloat();
				String n = sc.next();
				logger.info("SocialNetworkCircle从一条轨道上删除一个人");
				soci.removeEfromtrack(r, n);
			} else if (choice == 6) {
				soci.getEntropy();
				logger.info("SocialNetworkCircle获取熵值");
			} else if (choice == 7) {
				String n1 = sc.next();
				String n2 = sc.next();
				String r = sc.next();
				logger.info("SocialNetworkCircle添加一组关系");
				soci.addrelation(n1, n2, r);
			} else if (choice == 8) {
				String n1 = sc.next();
				String n2 = sc.next();
				logger.info("SocialNetworkCircle删除一组关系");
				soci.removerelation(n1, n2);
			} else if (choice == 9) {
				String n1 = sc.next();
				String n2 = sc.next();
				logger.info("SocialNetworkCircle计算两个好友的逻辑距离");
				soci.LogicalDistance(n1, n2);
			} else if (choice == 0) {
				logger.info("SocialNetworkCircle退出");
				ss.add(soci);
				return;
			}
			System.out.println(soci.getTrackMap().keySet().size());
		}

	}
}
