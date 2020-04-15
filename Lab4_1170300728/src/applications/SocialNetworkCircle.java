package applications;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import APIs.CircularOrbitAPIs;
import APIs.CircularOrbitHelper;
import MyException.MyException;
import centralObject.CentralUser;
import circularOrbit.CircularOrbit;
import circularOrbit.ConcreteCircularOrbit;
import physicalObject.FriendUser;
import track.Track;

public class SocialNetworkCircle extends ConcreteCircularOrbit<CentralUser, FriendUser> {
	public SocialNetworkCircle(String t) {
		super(t);
		// TODO Auto-generated constructor stub
	}

	private CentralUser central;
	private Map<FriendUser, Boolean> friends = new HashMap<>();
	private List<String> ties = new ArrayList<>();
	private Map<FriendUser, String> cmap = new HashMap<>();
	private Map<FriendUser, Map<FriendUser, String>> fmap = new HashMap<>();
	public Logger logger;

	/**
	 * getters
	 * 
	 * @return central等
	 */
	public CentralUser getcentral() {
		return central;
	}

	public Map<FriendUser, Boolean> getfriends() {
		return friends;
	}

	public List<String> getties() {
		return ties;
	}

	public Map<FriendUser, String> getcmap() {
		return cmap;
	}

	public Map<FriendUser, Map<FriendUser, String>> getfmap() {
		return fmap;
	}

	/**
	 * 从文件读入
	 * 
	 * @param FilePath 文件路径的字符串
	 */
	public Boolean readfromfile(String FilePath) {
		File f = new File(FilePath);
		String pattern1 = ".*CentralUser.*";
		String pattern2 = ".*SocialTie.*";
		String pattern3 = ".*Friend.*";
		String pattern4 = "(?<=(?:CentralUser::=<)).*(?=(?:>))";
		String pattern5 = "(?<=(?:SocialTie::=<)).*(?=(?:>))";
		String pattern6 = "(?<=(?:Friend::=<)).*(?=(?:>))";

		friends.clear();
		ties.clear();
		cmap.clear();
		fmap.clear();
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
								String[] u = matcher.group().split(",");
								if (u.length == 3)
									central = new CentralUser(u[0], Integer.valueOf(u[1]), u[2]);
								else
									throw new MyException("中心用户信息数目不正确！");
							} else {
								throw new MyException("中心用户信息格式有严重问题！");
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
								ties.add(matcher.group());
							} else {
								throw new MyException("用户关系信息格式有严重问题！");
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
								String[] u = matcher.group().split(",");
								if (u.length == 3) {
									for (FriendUser f1 : friends.keySet()) {
										if (f1.getName().equals(u[0]))
											throw new MyException("好友用户信息重复！");
									}
									FriendUser fu = new FriendUser(u[0], Integer.valueOf(u[1]), u[2]);
									friends.put(fu, false);
								} else
									throw new MyException("好友用户信息数目不正确！");
							} else {
								throw new MyException("好友用户信息格式有严重问题！");
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
		CircularOrbitHelper.visualize((CircularOrbit<CentralUser, FriendUser>) this);
	}

	/**
	 * 向特定轨道添加一个轨道物体
	 * 
	 * @param r    目标轨道半径
	 * @param name 用户名称
	 * @param age  用户年龄
	 * @param sex  用户性别
	 */
	public void addtotrack(float r, String name, int age, String sex) {
		FriendUser fu = new FriendUser(name, age, sex);
		addToTrack(fu, r);
	}

	/**
	 * 从指定轨道上移除一个轨道物体
	 * 
	 * @param r    目标轨道半径
	 * @param name 轨道物体名称
	 */
	public void removeEfromtrack(float r, String name) {
		for (Track t : getTrackMap().keySet()) {
			if (t.GetRadius() == r) {
				if (!getTrackMap().get(t).isEmpty())
					getTrackMap().get(t).remove(Findf(name));
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
	 * 初始化文件读入信息为可用图表形式
	 */
	public void makemap() {
		for (FriendUser f : friends.keySet()) {
			Map<FriendUser, String> fl = new HashMap<>();
			fmap.put(f, fl);
		}
		for (String s : ties) {
			try {
				String[] tie = s.split(",");
				if (tie[0].equals(tie[1]))
					throw new MyException("关系连接了同一个人！");
				if (tie[0].equals(central.getName())) {
					for (FriendUser fu : cmap.keySet()) {
						if (fu.getName().equals(tie[1]))
							throw new MyException("关系重复！");
					}
					cmap.put(Findf(tie[1]), tie[2]);
				} else if (tie[1].equals(central.getName())) {
					for (FriendUser fu : cmap.keySet()) {
						if (fu.getName().equals(tie[0]))
							throw new MyException("关系重复！");
					}
					cmap.put(Findf(tie[0]), tie[2]);
				} else {
					if (fmap.containsKey(Findf(tie[0])) && fmap.containsKey(Findf(tie[1]))) {
						for (FriendUser fu : fmap.get(Findf(tie[0])).keySet()) {
							if (fu.getName().equals(tie[0]))
								throw new MyException("关系重复！");
						}
						fmap.get(Findf(tie[0])).put(Findf(tie[1]), tie[2]);
						fmap.get(Findf(tie[1])).put(Findf(tie[0]), tie[2]);
					} else {
						throw new MyException("关系中的用户不在用户名单中！");
					}
				}
			} catch (MyException e) {
				System.out.println(e.getWhat());
			}
		}
	}

	/**
	 * 根据初始化后的图表信息建立轨道模型
	 */
	public void createcir() {
		int i = 2;
		clean();
		addL(central);
		addTrack(1);
		for (FriendUser fu : cmap.keySet()) {
			addToTrack(fu, 1);
			addLToE(central, fu);
			friends.put(fu, true);
		}
		while (true) {
			Boolean ifout = true;
			for (FriendUser fu : friends.keySet()) {
				if (friends.get(fu)) {
					for (FriendUser f : fmap.get(fu).keySet()) {
						if (!friends.get(f)) {
							ifout = false;
						}
					}
				}
			}
			if (ifout)
				break;
			addTrack(i);
			for (FriendUser fu : fmap.keySet()) {
				if (friends.get(fu)) {
					for (FriendUser f : fmap.get(fu).keySet()) {
						if (!friends.get(f)) {
							addToTrack(f, i);
							addEAndE(fu, f);
							friends.put(f, true);
						} else {
							addEAndE(fu, f);
						}
					}
				}
			}
			i++;
		}
		checkrep();
	}

	/**
	 * 在图表中查询某用户是否存在
	 * 
	 * @param name 用户名字
	 * @return 若存在返回用户；否则为null
	 */
	public FriendUser Findf(String name) {
		for (FriendUser f : friends.keySet())
			if (name.equals(f.getName()))
				return f;
		System.out.println("没有这个人");
		return null;
	}

	/**
	 * 添加一组关系
	 * 
	 * @param name1  某一个用户的名字
	 * @param name2  某另一个用户的名字
	 * @param relate 关系亲密度
	 */
	public void addrelation(String name1, String name2, String relate) {
		if (Findf(name1) != null && Findf(name2) != null) {
			ties.add(name1 + "," + name2 + "," + relate);
			makemap();
			createcir();
		} else {
			System.out.println("没有这个人");
		}
	}

	/**
	 * 移除某两人的关系
	 * 
	 * @param name1 某一个用户的名字
	 * @param name2 某另一个用户的名字
	 */
	public void removerelation(String name1, String name2) {
		if (Findf(name1) != null && Findf(name2) != null) {
			Iterator<String> siter = ties.iterator();
			while (siter.hasNext()) {
				if (siter.next().contains(name1) && siter.next().contains(name2)) {
					siter.remove();
				}
			}
			makemap();
			createcir();
		} else {
		}
	}

	/**
	 * 获取两人之间的逻辑距离
	 * 
	 * @param name1 某一个用户的名字
	 * @param name2 某另一个用户的名字
	 * @return 逻辑距离
	 */
	public int LogicalDistance(String name1, String name2) {
		Boolean flag1 = false, flag2 = false;
		int dis;
		for (FriendUser f : friends.keySet()) {
			if (name1.equals(f.getName())) {
				flag1 = true;
			}
			if (name2.equals(f.getName())) {
				flag2 = true;
			}
		}
		if (flag1 && flag2) {
			dis = CircularOrbitAPIs.getLogicalDistancefromEtoE(this, Findf(name1), Findf(name2));
			System.out.println("逻辑距离为" + dis);
			return dis;
		} else {
			return -1;
		}
	}
	
	public void checkrep() {
		for(Track t:getTrackMap().keySet()) 
			for(FriendUser f:getTrackMap().get(t).keySet()) 
				assert CircularOrbitAPIs.getLogicalDistancefromLtoE(this, getcentral(), f)==t.GetRadius();
	}
}
