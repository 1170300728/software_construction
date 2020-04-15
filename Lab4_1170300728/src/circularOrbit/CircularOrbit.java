package circularOrbit;

import java.util.List;
import java.util.Map;

import track.Track;

public interface CircularOrbit<L,E> {
	
	/**
	 * 添加一条轨道
	 * @param radius 轨道半径
	 */
	public void addTrack(float radius);
	/**
	 * 删除一条轨道
	 * @param radius 要删除的轨道半径
	 */
	public void removeTrack(float radius);
	
	/**
	 * 添加一个中心元素
	 * @param l 某中心元素
	 */
	public void addL(L l);
	
	/**
	 * 向指定轨道添加轨道元素
	 * @param e 某轨道元素
	 * @param r 目标轨道半径
	 */
	public void addToTrack(E e,float r);
	
	/**
	 * 添加从指定中心元素到指定轨道元素的关系
	 * @param l 目标中心元素
	 * @param e 目标轨道元素
	 */
	public void addLToE(L l,E e);

	/**
	 * 添加从轨道元素到轨道元素的有向关系
	 * @param e1 初始轨道元素
	 * @param e2 终止轨道元素
	 */
	public void addEToE(E e1,E e2);

	/**
	 * 添加从轨道元素到轨道元素的无向关系
	 * @param e1 目标轨道元素
	 * @param e2 目标轨道元素
	 */
	public void addEAndE(E e1,E e2);
	
	/**
	 * 功能函数，返回一个关于所有轨道元素及其对应有向关系的map
	 * @return 关于所有轨道元素及其对应有向关系的map
	 */
	public Map<E,List<E>> sourceMap();

	/**
	 * getter 获取trackmap
	 * @return trackmap
	 */
	public Map<Track, Map<E, List<E>>> getTrackMap();

	/**
	 * getter 获取lmap
	 * @return lmap
	 */
	public Map<L, List<E>> getLmap();

	/**
	 * getter 获取tag
	 * @return tag
	 */
	public String getTag();
	
	/**
	 * 清空该轨道系统
	 */
	public void clean();
}
