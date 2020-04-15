package applications;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import APIs.CircularOrbitAPIs;
import APIs.CircularOrbitHelper;
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

	public String getgame() {
		return game;
	}

	public int getnumoftrack() {
		return numoftrack;
	}

	public List<Athlete> getathletes() {
		return athletes;
	}

	public void readfromfile(String FilePath) {
		File f = new File(FilePath);
		String pattern1 = ".*Athlete.*";
		String pattern2 = ".*Game.*";
		String pattern3 = ".*NumOfTrack.*";
		String pattern4 = "(?<=(?:Athlete::=<)).*(?=(?:>))";
		String pattern5 = "(?<=(?:Game::=))\\d*";
		String pattern6 = "(?<=(?:NumOfTracks::=))\\d*";

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
							String[] athlete = matcher.group().split(",");
							athletes.add(new Athlete(athlete[0], Integer.valueOf(athlete[1]), athlete[2],
									Integer.valueOf(athlete[3]), Float.valueOf(athlete[4])));
						}
					} else if (Pattern.matches(pattern2, lineTxt)) {
						Pattern pattern = Pattern.compile(pattern5);
						Matcher matcher = pattern.matcher(lineTxt);
						if (matcher.find()) {
							game = matcher.group();
						}
					} else if (Pattern.matches(pattern3, lineTxt)) {
						Pattern pattern = Pattern.compile(pattern6);
						Matcher matcher = pattern.matcher(lineTxt);
						if (matcher.find()) {
							numoftrack = Integer.valueOf(matcher.group());
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
		CircularOrbitHelper.visualize((CircularOrbit<String, Athlete>) this);
	}

	public void addtrack() {
		addTrack(50);
		numoftrack = getTrackMap().size();
	}

	public void removetrack() {
		removeTrack(100);
		numoftrack = getTrackMap().size();
	}

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

	public void getEntropy() {
		System.out.print("熵值为：");
		System.out.println(CircularOrbitAPIs.getObjectDistributionEntropy(this));
	}

	public void autogame1() {
		int nownum = 0;
		Collections.shuffle(athletes);
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
			for (int i = 1; i <= numoftrack; i++) {
				if (nownum < athletes.size()) {
					System.out.print(athletes.get(nownum).getName() + "\t");
					addToTrack(athletes.get(nownum), 100 * i);
				} else {
					System.out.print("无\t");
				}
				nownum++;
			}
			System.out.println();
		}
		System.out.println();
	}

	public void autogame2() {
		Athlete[] newathletes = new Athlete[athletes.size()];
		int i = 0;
		clean();

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
				} else {
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
			for (i = numoftrack; i >= 1; i--) {
				if (nownum < athletes.size())
					System.out.print(athletes.get(nownum).getName() + "\t");
				else
					System.out.print("无\t");
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
			System.out.println();
		}
		System.out.println();
	}
}
