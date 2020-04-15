package applications;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import APIs.CircularOrbitAPIs;
import APIs.CircularOrbitHelper;
import MyException.MyException;
import circularOrbit.CircularOrbit;
import circularOrbit.ConcreteCircularOrbit;
import physicalObject.Athlete;
import track.Track;

public class TrackGame extends ConcreteCircularOrbit<String, Athlete> {

	public TrackGame(String t) {
		super(t);
		// TODO Auto-generated constructor stub
	}

	private String game;
	private int numoftrack;
	private List<Athlete> athletes = new ArrayList<>();
	private List<Athlete[]> groups = new ArrayList<>();
	Logger logger;

	/**
	 * getters
	 * 
	 * @return game\numoftrack等
	 */
	public String getgame() {
		return game;
	}

	public int getnumoftrack() {
		return numoftrack;
	}

	public List<Athlete> getathletes() {
		return athletes;
	}

	/**
	 * 从文件读入
	 * 
	 * @param FilePath 文件路径的字符串
	 */
	public Boolean readfromfile(String FilePath) {
		File f = new File(FilePath);
		String pattern1 = ".*Athlete.*";
		String pattern2 = ".*Game.*";
		String pattern3 = ".*NumOfTrack.*";
		String pattern4 = "(?<=(?:Athlete::=<)).*(?=(?:>))";
		String pattern5 = "(?<=(?:Game::=))\\d*";
		String pattern6 = "(?<=(?:NumOfTracks::=))\\d*";

		athletes.clear();
		groups.clear();
		logger=Logger.getLogger(getTag());
		try {
			String encoding = "UTF-8";
			if (f.isFile() && f.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(f), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;

				while ((lineTxt = bufferedReader.readLine()) != null) {
					lineTxt = lineTxt.replaceAll(" ", "");
					if (Pattern.matches(pattern1, lineTxt)) {
						try {
							Pattern pattern = Pattern.compile(pattern4);
							Matcher matcher = pattern.matcher(lineTxt);
							if (matcher.find()) {
								String[] athlete = matcher.group().split(",");
								if (athlete.length == 5) {
									for (Athlete a : athletes) {
										if (a.getName().equals(athlete[0])) {
											throw new MyException("运动员信息重复");
										}
									}
									athletes.add(new Athlete(athlete[0], Integer.valueOf(athlete[1]), athlete[2],
											Integer.valueOf(athlete[3]), Float.valueOf(athlete[4])));
								} else if (athlete.length < 5)
									throw new MyException("运动员信息缺失");
								else
									throw new MyException("运动员信息数量过多");
							} else {
								throw new MyException("运动员信息格式有严重问题！");
							}
						} catch (MyException e) {
							logger.info(e.getWhat());
							System.out.println(e.getWhat());
							return false;
						}
					} else if (Pattern.matches(pattern2, lineTxt)) {
						try {
							Pattern pattern = Pattern.compile(pattern5);
							Matcher matcher = pattern.matcher(lineTxt);
							if (matcher.find()) {
								game = matcher.group();
							} else {
								throw new MyException("赛事信息格式有严重问题！");
							}
						} catch (MyException e) {
							logger.info(e.getWhat());
							System.out.println(e.getWhat());
							return false;
						}
					} else if (Pattern.matches(pattern3, lineTxt)) {
						try {
							Pattern pattern = Pattern.compile(pattern6);
							Matcher matcher = pattern.matcher(lineTxt);
							if (matcher.find()) {
								numoftrack = Integer.valueOf(matcher.group());
							} else {
								throw new MyException("赛道信息格式有严重问题！");
							}
						} catch (MyException e) {
							logger.info(e.getWhat());
							System.out.println(e.getWhat());
							return false;
						}
					}
				}
				read.close();
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 画图
	 */
	public void drawgraph() {
		CircularOrbitHelper.visualize((CircularOrbit<String, Athlete>) this);
	}

	/**
	 * 添加一条轨道
	 */
	public void addtrack() {
		addTrack(50);
		numoftrack = getTrackMap().size();
	}

	/**
	 * 删除一条轨道
	 */
	public void removetrack() {
		removeTrack(100);
		numoftrack = getTrackMap().size();
	}

	/**
	 * 向特定轨道添加一个轨道物体
	 * 
	 * @param r    目标轨道半径
	 * @param name 轨道物体名称
	 */
	public void addtotrack(float r, String name) {
		Athlete a = null;
		Boolean ifa = false;
		for (Athlete athlete : athletes) {
			if (athlete.getName().equals(name)) {
				a = athlete;
				ifa = true;
			}
		}
		if (ifa) {
			addToTrack(a, r);
		}
	}

	/**
	 * 从指定轨道上移除一个轨道物体
	 * 
	 * @param r    目标轨道半径
	 * @param name 轨道物体名称
	 */
	public void removeEfromtrack(float r, String name) {
		Athlete a = null;
		for (Athlete athlete : athletes) {
			if (athlete.getName().equals(name)) {
				a = athlete;
			}
		}
		for (Track t : getTrackMap().keySet()) {
			if (t.GetRadius() == r) {
				getTrackMap().get(t).remove(a);
			} else {
				for (Athlete ath : getTrackMap().get(t).keySet()) {
					for (Athlete athlete : getTrackMap().get(t).get(ath)) {
						if (athlete.equals(a)) {
							getTrackMap().get(t).get(ath).remove(athlete);
						}
					}
				}
			}
		}
	}

	/**
	 * 计算轨道系统熵值
	 */
	public void getEntropy() {
		System.out.print("熵值为：");
		System.out.println(CircularOrbitAPIs.getObjectDistributionEntropy(this));
	}

	/**
	 * 按照单人随机排序赛制制图
	 */
	public void autogame1() {
		int nownum = 0;
		Collections.shuffle(athletes);
		if (!groups.isEmpty())
			groups.clear();
		clean();
		System.out.print("\t");
		for (int i = 1; i <= numoftrack; i++) {
			addTrack(100 * i);
			System.out.print("赛道" + i + "\t");
		}
		System.out.println();
		int j = 1;
		while (nownum < athletes.size()) {
			System.out.print("组" + j + "\t");
			j++;
			Athlete[] group = new Athlete[numoftrack];
			for (int i = 1; i <= numoftrack; i++) {
				if (nownum < athletes.size()) {
					System.out.print(athletes.get(nownum).getName() + "\t");
					addToTrack(athletes.get(nownum), 100 * i);
					group[i - 1] = athletes.get(nownum);
				} else {
					System.out.print("无\t");
					group[i - 1] = null;
				}
				nownum++;
			}
			groups.add(group);
			System.out.println();
		}
		System.out.println();
		checkRep();
	}

	/**
	 * 按照单人顺序排序赛制制图
	 */
	public void autogame2() {
		Athlete[] newathletes = new Athlete[athletes.size()];
		int i = 0;
		clean();
		if (!groups.isEmpty())
			groups.clear();

		for (Athlete a : athletes) {
			newathletes[i] = a;
			i++;
		}

		for (int j = 0; j < athletes.size(); j++) {
			for (int k = j + 1; k < athletes.size(); k++) {
				if (newathletes[j].getBest() > newathletes[k].getBest()) {
					Athlete temp = newathletes[j];
					newathletes[j] = newathletes[k];
					newathletes[k] = temp;
				}
			}
		}

		int nownum = 0, nowtrack = numoftrack / 2;

		System.out.print("\t");
		for (i = 1; i <= numoftrack; i++) {
			addTrack(i * 100);
			System.out.print("赛道" + i + "\t");
		}
		System.out.println();
		int j = 1;
		while (nownum < athletes.size()) {
			nowtrack = numoftrack / 2;
			j++;
			for (i = 1; i <= numoftrack; i++) {
				if (nownum < athletes.size()) {
					addToTrack(newathletes[nownum], (nowtrack + 1) * 100);
				}
				nownum++;
				if (i % 2 == 0)
					nowtrack += i;
				else
					nowtrack -= i;
			}
		}
		for (j = 1; j <= (int) Math.ceil((double) athletes.size() / (double) numoftrack); j++) {
			System.out.print("组" + j + "\t");
			nownum = numoftrack * j - 1;
			Boolean flag = true;
			Athlete[] group = new Athlete[numoftrack];
			for (i = numoftrack; i >= 1; i--) {
				if (nownum < athletes.size()) {
					System.out.print(athletes.get(nownum).getName() + "\t");
					group[i - 1] = athletes.get(nownum);
				} else {
					System.out.print("无\t");
					group[i - 1] = null;
				}
				if (flag) {
					if (nownum == numoftrack * (j - 1)) {
						nownum = 1 + numoftrack * (j - 1);
						flag = false;
					} else if (nownum == 1 + numoftrack * (j - 1)) {
						nownum = numoftrack * (j - 1);
						flag = false;
					} else {
						nownum -= 2;
					}
				} else {
					nownum += 2;
					if (nownum > numoftrack * j) {
						break;
					}
				}
			}
			groups.add(group);
			System.out.println();
		}
		System.out.println();
		checkRep();
	}
	 
	public void checkRep() {
		for(Athlete[] as:groups) {
			assert as.length<=numoftrack;
		}
	}

}
