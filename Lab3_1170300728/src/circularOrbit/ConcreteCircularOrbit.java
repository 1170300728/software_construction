package circularOrbit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import track.Track;

public class ConcreteCircularOrbit<L, E> implements CircularOrbit<L, E> {
	private String tag;
	private final Map<L, List<E>> lmap = new HashMap<>();
	private final Map<Track, Map<E, List<E>>> trackmap = new HashMap<>();

	public ConcreteCircularOrbit(String t) {
		tag=t;
	}
	
	public String getTag() {
		return tag;
	}

	public Map<L, List<E>> getLmap() {
		return lmap;
	}
	
	@Override
	public void addTrack(float radius) {
		for (Track t : trackmap.keySet()) {
			if (t.GetRadius() == radius) {
				System.out.println("已经有半径为" + radius + "的轨道了！");
				return;
			}
		}
		Track t = new Track(radius);
		Map<E, List<E>> em = new HashMap<>();
		trackmap.put(t, em);
	}

	@Override
	public void removeTrack(float radius) {
		Iterator<Track> titer = trackmap.keySet().iterator();
		while (titer.hasNext()) {
			Track t = titer.next();
			if (t.GetRadius() == radius) {
				trackmap.remove(t);
				return;
			}
		}
		System.out.println("没有找到半径为" + radius + "的轨道！");
	}

	@Override
	public void addL(L ln) {
		for (L l : lmap.keySet()) {
			if (l.equals(ln)) {
				System.out.println("已经有这个中心了");
				return;
			}
		}
			List<E> el = new ArrayList<>();
			lmap.put(ln, el);
	}

	@Override
	public void addToTrack(E e, float r) {
		for (Track t : trackmap.keySet()) {
			if (t.GetRadius() == r) {
				if (!trackmap.get(t).containsKey(e)) {
					List<E> el = new ArrayList<>();
					trackmap.get(t).put(e, el);
					return;
				} else {
					System.out.println("该轨道上已经有这个物体了");
					return;
				}
			}
		}
		System.out.println("没有找到这个轨道！");
	}

	@Override
	public void addLToE(L l, E e) {
		if (!lmap.containsKey(l)) {
			System.out.println("没有找到该中心物体！");
		} else {
			Boolean hase = false;
			for (Track t : trackmap.keySet()) {
				if (trackmap.get(t).keySet().contains(e)) {
					hase = true;
				}
			}
			if (hase) {
				if (!lmap.get(l).contains(e)) {
					lmap.get(l).add(e);
				} else {
					System.out.println("已经建立过关系了");
				}
			} else {
				System.out.println("没有找到该轨道物体！");
			}
		}
	}

	@Override
	public void addEToE(E e1, E e2) {
		if (e1.equals(e2)) {
			System.out.println("请输入两个不同的轨道物体！");
			return;
		}
		Boolean hase1 = false, hase2 = false;
		for (Map<E, List<E>> em : trackmap.values()) {
			if (em.keySet().contains(e1)) {
				hase1 = true;
				if (em.get(e1).contains(e2)) {
					System.out.println("已经建立过关系了");
					return;
				}
			}
			if (em.keySet().contains(e2)) {
				hase2 = true;

			}
		}
		if (!hase1) {
			System.out.println("没有找到轨道物体1！");
		} else if (!hase2) {
			System.out.println("没有找到轨道物体2！");
		} else {
			for (Map<E, List<E>> em : trackmap.values()) {
				if (em.keySet().contains(e1)) {
					em.get(e1).add(e2);
				}
			}
		}
	}

	@Override
	public void addEAndE(E e1, E e2) {
		if (e1.equals(e2)) {
			System.out.println("请输入两个不同的轨道物体！");
			return;
		}
		addEToE(e1, e2);
		addEToE(e2, e1);
	}
	
	@Override
	public Map<E,List<E>> sourceMap(){
		Map<E,List<E>> tempmap=new HashMap<>();
		for (Track t:trackmap.keySet()) {
			for(E e:trackmap.get(t).keySet()) {
				tempmap.put(e, trackmap.get(t).get(e));
			}
		}
		return tempmap;
	}

	@Override
	public Map<Track, Map<E, List<E>>> getTrackMap(){
		return trackmap;
	}
	
	@Override
	public void clean() {
		lmap.clear();
		trackmap.clear();
	}
	
}