/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {

	private final List<Vertex<L>> verticelist = new ArrayList<>();

	// Abstraction function:
	// Represents Graph made vertices
	// Representation invariant:
	// Contains a set of vertices
	// Safety from rep exposure:
	// All fields are private
	// vertices consist of only strings that are immutable

	// TODO checkRep

	@Override
	public boolean add(L vertex) {
		Iterator<Vertex<L>> vertexiter = verticelist.iterator();
		while (vertexiter.hasNext()) {
			Vertex<L> v = vertexiter.next();
			L lab = v.getlabel();
			if (lab.equals(vertex))
				return false;
		}
		Vertex<L> v = new Vertex<L>(vertex);
		verticelist.add(v);
		return true;
	}

	@Override
	public int set(L source, L target, int weight) {
		add(source);
		add(target);
		int ret = 0;

		Iterator<Vertex<L>> vertexiter = verticelist.iterator();
		while (vertexiter.hasNext()) {
			Vertex<L> v = vertexiter.next();
			if (v.getlabel().equals(source))
				ret = v.addTarget(target, weight);
		}
		return ret;
	}

	@Override
	public boolean remove(L vertex) {
		boolean removed = false;
		Iterator<Vertex<L>> vertexiter = verticelist.iterator();
		while (vertexiter.hasNext()) {
			L lab = vertexiter.next().getlabel();
			if (lab.equals(vertex)) {
				vertexiter.remove();
				removed = true;
			}
		}
		Iterator<Vertex<L>> vertexiter2 = verticelist.iterator();
		while (vertexiter2.hasNext()) {
			Vertex<L> v = vertexiter2.next();
			v.removeTarget(vertex);
		}
		return removed;
	}

	@Override
	public Set<L> vertices() {
		Set<L> verts = new HashSet<L>();
		Iterator<Vertex<L>> vertexiter = verticelist.iterator();
		while (vertexiter.hasNext()) {
			Vertex<L> v = vertexiter.next();
			verts.add(v.getlabel());
		}
		return verts;
	}

	@Override
	public Map<L, Integer> sources(L target) {
		Map<L, Integer> srcmap = new HashMap<L, Integer>();
		Iterator<Vertex<L>> vertexiter = verticelist.iterator();
		while (vertexiter.hasNext()) {
			Vertex<L> v = vertexiter.next();
			Map<L, Integer> targets = new HashMap<L, Integer>();
			targets = v.getTargets();
			if (targets.containsKey(target))
				srcmap.put(v.getlabel(), targets.get(target));
		}
		return srcmap;
	}

	@Override
	public Map<L, Integer> targets(L source) {
		Iterator<Vertex<L>> vertexiter = verticelist.iterator();
		while (vertexiter.hasNext()) {
			Vertex<L> v = vertexiter.next();
			L lab = v.getlabel();
			if (lab.equals(source))
				return v.getTargets();
		}
		Map<L, Integer> tarmap = new HashMap<L, Integer>();
		return tarmap;
	}

	// TODO toString()
	public String toString() {
		String ret = "";
		Iterator<Vertex<L>> vertexiter = verticelist.iterator();
		while (vertexiter.hasNext()) {
			Vertex<L> v = vertexiter.next();
			L vertex1 = v.getlabel();
			Map<L, Integer> map = new HashMap<L, Integer>();
			map = v.getTargets();
			Iterator<Map.Entry<L, Integer>> mapiter = map.entrySet().iterator();
			while (mapiter.hasNext()) {
				Map.Entry<L, Integer> vertex2 = mapiter.next();
				ret += "[" + vertex1 + "->" + vertex2.getKey() + "=" + map.get(vertex2) + "]";
			}
		}
		return ret;
	}

}

/**
 * TODO specification Mutable. This class is internal to the rep of
 * ConcreteVerticesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Vertex<L> {

	// TODO fields
	private final L label;
	Map<L, Integer> targets = new HashMap<L, Integer>();
	// Abstraction function:
	// represents a vertex and all its targets in graph
	// Representation invariant:
	// non empty label
	// Safety from rep exposure:
	// String is immutable and final

	// TODO constructor
	public Vertex(L l) {
		label = l;
	}

	// TODO checkRep
	@SuppressWarnings("unused")
	private void checkRep() {
		assert (!((String) label).isEmpty());
	}

	// TODO methods
	public int addTarget(L l, int w) {
		int ret = 0;
		if (targets.keySet().contains(l)) {
			ret = targets.get(l);
			targets.replace(l, w);
		} else {
			targets.put(l, w);
		}
		return ret;
	}

	public Map<L, Integer> getTargets() {
		return targets;
	}

	public L getlabel() {
		return label;
	}

	public boolean removeTarget(L l) {
		if (targets.keySet().contains(l)) {
			targets.remove(l);
			return true;
		}
		return false;
	}

	// TODO toString()
	@Override
	public String toString() {
		return label + " ： " + targets.toString() + "\n";
	}
}