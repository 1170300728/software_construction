package P3;

import java.util.ArrayList;

public class Action {
	String name1;
	String name2;
	ArrayList<String> List1;
	ArrayList<String> List2;

	public Action(String n1, String n2) {
		name1 = n1;
		name2 = n2;
		List1 = new ArrayList<String>();
		List2 = new ArrayList<String>();
	}

	public void add(String n, String act) {
		if (name1.equals(n)) {
			List1.add(act);
		} else if (name2.equals(n)) {
			List2.add(act);
		}
	}

	public void printlist(String n) {
		System.out.println(n + "的历史操作如下：");
		if (name1.equals(n)) {
			for (String s : List1) {
				System.out.println(s);
			}
		} else if (name2.equals(n)) {
			for (String s : List2) {
				System.out.println(s);
			}
		}
		System.out.println();
	}
}
