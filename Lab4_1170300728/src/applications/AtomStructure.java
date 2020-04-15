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
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import APIs.CircularOrbitAPIs;
import APIs.CircularOrbitHelper;
import MyException.MyException;
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
	Logger logger;

	/**
	 * 从文件读入
	 * 
	 * @param FilePath 文件路径的字符串
	 */
	public Boolean readfromfile(String FilePath) {
		File f = new File(FilePath);
		String pattern1 = ".*ElementName.*";
		String pattern2 = ".*NumberOfTracks.*";
		String pattern3 = ".*NumberOfElectron.*";
		String pattern4 = "(?<=(?:ElementName::=)).*";
		String pattern5 = "(?<=(?:NumberOfTracks::=))\\d*";
		String pattern6 = "(?<=(?:NumberOfElectron::=)).*";

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
								if (Character.isUpperCase(matcher.group().charAt(0))) {
									if (matcher.group().length() > 1)
										if (Character.isLowerCase(matcher.group().charAt(1))) {
											elementname = matcher.group();
										} else {
											throw new MyException("元素名称中的第二位不是小写写字母！");
										}
									else
										elementname = matcher.group();
								} else
									throw new MyException("元素名称中的第一位不是大写字母！");
							} else {
								throw new MyException("元素名称信息格式有严重问题！");
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
								numoftracks = Integer.valueOf(matcher.group());
							} else {
								throw new MyException("轨道数量信息格式有严重问题！");
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
								electrontracks = matcher.group().split(";");
							} else {
								throw new MyException("核外电子信息格式有严重问题！");
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
	 * getters
	 * @return numoftracks等
	 */
	public String[] getelectrontracks() {
		return electrontracks;
	}

	public int getnumoftracks() {
		return numoftracks;
	}

	public String getelementname() {
		return elementname;
	}

	/**
	 * 画图
	 */
	public void drawgraph() {
		CircularOrbitHelper.visualize((CircularOrbit<atomic, electron>) this);
	}

	/**
	 * 添加一条轨道
	 */
	public void addAtrack(float r) {
		addTrack(r);
		numoftracks = getTrackMap().size();
	}

	/**
	 * 删除一条轨道
	 */
	public void removeAtrack(float r) {
		removeTrack(r);
		numoftracks = getTrackMap().size();
	}

	/**
	 * 向特定轨道添加一个轨道物体
	 * 
	 * @param r 目标轨道半径
	 */
	public void addtotrack(float r) {
		electron e = new electron(1);
		addToTrack(e, r);
	}

	/**
	 * 从指定轨道上移除一个轨道物体
	 * 
	 * @param r 目标轨道半径
	 */
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

	/**
	 * 计算轨道系统熵值
	 */
	public void getEntropy() {
		System.out.print("熵值为：");
		System.out.println(CircularOrbitAPIs.getObjectDistributionEntropy(this));
	}

	/**
	 * 根据文件的读入信息建立轨道系统
	 */
	public void createcir() {
		clean();
		try {
			if(numoftracks!=electrontracks.length)
				throw  new MyException("轨道数量与元素轨道信息不符！");
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
		for (int i = 0; i < sourceMap().keySet().size(); i++)
			addL(new atomic(1, "质子"));
		}catch (MyException e) {
			System.out.println(e.getWhat());
		}
		
	}

	/**
	 * 模拟电子跃迁
	 * 
	 * @param r1 第一个轨道的半径
	 * @param r2 第二个轨道的半径
	 */
	public void moveatob(float r1, float r2) {
		removeEfromtrack(r1);
		addtotrack(r2);
	}
}