package applications;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import APIs.CircularOrbitAPIs;
import APIs.CircularOrbitHelper;
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

	public void readfromfile(String FilePath) {
		File f = new File(FilePath);
		String pattern1 = ".*CentralUser.*";
		String pattern2 = ".*SocialTie.*";
		String pattern3 = ".*Friend.*";
		String pattern4 = "(?<=(?:CentralUser::=<)).*(?=(?:>))";
		String pattern5 = "(?<=(?:SocialTie::=<)).*(?=(?:>))";
		String pattern6 = "(?<=(?:Friend::=<)).*(?=(?:>))";

		try {
			String encoding = "UTF-8";
			if (f.isFile() && f.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(f), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;

				while ((lineTxt = bufferedReader.readLine()) != null) {
					lineTxt = lineTxt.replaceAll(" ", "");
					if (Pattern.matches(pattern1, lineTxt)) {
						Pattern pattern = Pattern.compile(pattern4);
						Matcher matcher = pattern.matcher(lineTxt);
						if (matcher.find()) {
							String[] u = matcher.group().split(",");
							central = new CentralUser(u[0], Integer.valueOf(u[1]), u[2]);
						}
					} else if (Pattern.matches(pattern2, lineTxt)) {
						Pattern pattern = Pattern.compile(pattern5);
						Matcher matcher = pattern.matcher(lineTxt);
						if (matcher.find()) {
							ties.add(matcher.group());
							System.out.println(matcher.group());
						}
					} else if (Pattern.matches(pattern3, lineTxt)) {
						Pattern pattern = Pattern.compile(pattern6);
						Matcher matcher = pattern.matcher(lineTxt);
						if (matcher.find()) {
							String[] u = matcher.group().split(",");
							FriendUser fu = new FriendUser(u[0], Integer.valueOf(u[1]), u[2]);
							friends.put(fu, false);
						}
					}
				}
				read.close();
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
	}

	public void drawgraph() {
		CircularOrbitHelper.visualize((CircularOrbit<CentralUser, FriendUser>) this);
	}

	public void addtrack(float r) {
		addTrack(r);
	}

	public void removetrack(float r) {
		removeTrack(r);
	}

	public void addtotrack(float r, String name, int age, String sex) {
		FriendUser fu = new FriendUser(name, age, sex);
		addToTrack(fu, r);
	}

	public void removeEfromtrack(float r, String name) {
		for (Track t : getTrackMap().keySet()) {
			if (t.GetRadius() == r) {
				if (!getTrackMap().get(t).isEmpty())
					getTrackMap().get(t).remove(Findf(name));
			}
		}
	}

	public void getEntropy() {
		System.out.print("熵值为：");
		System.out.println(CircularOrbitAPIs.getObjectDistributionEntropy(this));
	}

	public void makemap() {
		for (FriendUser f : friends.keySet()) {
			Map<FriendUser, String> fl = new HashMap<>();
			fmap.put(f, fl);
		}
		for (String s : ties) {
			String[] tie = s.split(",");
			if (tie[0].equals(central.getName())) {
				cmap.put(Findf(tie[1]), tie[2]);
			} else if (tie[1].equals(central.getName())) {
				cmap.put(Findf(tie[0]), tie[2]);
			} else {
				fmap.get(Findf(tie[0])).put(Findf(tie[1]), tie[2]);
			}
		}
	}

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
							addEToE(fu, f);
							friends.put(f, true);
						System.out.println(fu.getName()+f.getName());
						}else {
							addEToE(fu,f);
						}
					}
				}
			}
			i++;
		}
	}

	public FriendUser Findf(String name) {
		for (FriendUser f : friends.keySet())
			if (name.equals(f.getName()))
				return f;
		System.out.println("没有这个人");
		return null;
	}

	public void addrelation(String name1, String name2, String relate) {
		if (Findf(name1) != null && Findf(name2) != null) {
			ties.add(name1 + "," + name2 + "," + relate);
			makemap();
			createcir();
		} else {
			System.out.println("没有这个人");
		}
	}

	public void removerelation(String name1, String name2) {
		if (fmap.containsKey(Findf(name1)) && fmap.containsKey(Findf(name2))) {
			if (fmap.get(Findf(name1)).containsKey(Findf(name2))) {
				fmap.get(Findf(name1)).remove(Findf(name2));
			}
		}
		createcir();
	}

	public int LogicalDistance(String name1, String name2) {
		Boolean flag1 = false, flag2 = false;
		int dis;
		for (FriendUser f : friends.keySet()) {
			if (name1.equals(f.getName())) {
				System.out.println(Findf(name1).getName());
				flag1 = true;
			}
			if (name2.equals(f.getName())) {
				System.out.println(Findf(name2).getName());
				flag2 = true;
			}
		}
		if (flag1 && flag2) {
			dis=CircularOrbitAPIs.getLogicalDistance(this, Findf(name1), Findf(name2));
			System.out.println("逻辑距离为" + dis);
			return dis;			
		}
		else {
			System.out.println("没有这个人");
			return 0;
		}
	}
}
