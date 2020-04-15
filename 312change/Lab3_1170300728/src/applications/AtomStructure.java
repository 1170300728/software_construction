package applications;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import APIs.CircularOrbitAPIs;
import APIs.CircularOrbitHelper;
import centralObject.atomic;
import circularOrbit.CircularOrbit;
import circularOrbit.ConcreteCircularOrbit;
import physicalObject.electron;
import track.Track;

public class AtomStructure extends ConcreteCircularOrbit<atomic, electron> {

	public AtomStructure(String t) {
		super(t);
		// TODO Auto-generated constructor stub
	}

	private String elementname;
	private int numoftracks;
	private String[] electrontracks;

	public void readfromfile(String FilePath) {
		File f = new File(FilePath);
		String pattern1 = ".*ElementName.*";
		String pattern2 = ".*NumberOfTracks.*";
		String pattern3 = ".*NumberOfElectron.*";
		String pattern4 = "(?<=(?:ElementName::=)).*";
		String pattern5 = "(?<=(?:NumberOfTracks::=))\\d*";
		String pattern6 = "(?<=(?:NumberOfElectron::=)).*";

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
							elementname = matcher.group();
						}
					} else if (Pattern.matches(pattern2, lineTxt)) {
						Pattern pattern = Pattern.compile(pattern5);
						Matcher matcher = pattern.matcher(lineTxt);
						if (matcher.find()) {
							numoftracks = Integer.valueOf(matcher.group());
						}
					} else if (Pattern.matches(pattern3, lineTxt)) {
						Pattern pattern = Pattern.compile(pattern6);
						Matcher matcher = pattern.matcher(lineTxt);
						if (matcher.find()) {
							electrontracks = matcher.group().split(";");
							System.out.println(matcher.group());
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

	public String[] getelectrontracks() {
		return electrontracks;
	}

	public int getnumoftracks() {
		return numoftracks;
	}

	public String getelementname() {
		return elementname;
	}

	public void drawgraph() {
		CircularOrbitHelper.visualize((CircularOrbit<atomic, electron>) this);
	}

	public void addtrack(float r) {
		addTrack(r);
		numoftracks = getTrackMap().size();
	}

	public void removetrack(float r) {
		removeTrack(r);
		numoftracks = getTrackMap().size();
	}

	public void addtotrack(float r) {
		electron e = new electron(1);
		addToTrack(e, r);
	}

	public void removeEfromtrack(float r) {
		for (Track t : getTrackMap().keySet()) {
			if (t.GetRadius() == r) {
				if (!getTrackMap().get(t).isEmpty()) {
					for (electron e : getTrackMap().get(t).keySet()) {
						getTrackMap().get(t).remove(e);
						return;
					}
				}
			}
		}
	}

	public void getEntropy() {
		System.out.print("熵值为：");
		System.out.println(CircularOrbitAPIs.getObjectDistributionEntropy(this));
	}

	public void createcir() {
		clean();
		for (int i = 1; i <= numoftracks; i++) {
			addTrack(i);
		}
		for (int i = 0; i < numoftracks; i++) {
			if (!electrontracks[i].isEmpty()) {
				int num = Integer.valueOf(electrontracks[i].split("/")[1]);
				for (int j = 0; j < num; j++) {
					electron e = new electron(1);
					addToTrack(e, i + 1);
				}
			}
		}
		for(int i=0;i<sourceMap().keySet().size();i++)
		addL(new atomic(1,"质子"));
	}

	public void moveatob(float r1, float r2) {
		removeEfromtrack(r1);
		addtotrack(r2);
	}	
	public void addzhongzi() {
		addL(new atomic(1,"中子"));
	}
}