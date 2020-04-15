/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {

	public final Set<L> vertices = new HashSet<>();
	public final List<Edge<L>> edgelist = new ArrayList<>();

	// Abstraction function:
	// Represents Graph made of edges connecting vertices
	// Representation invariant:
	// Contains a set of edges connected through vertices
	// Safety from rep exposure:
	// All fields are private
	// Edges and vertices consist of only strings that are immutable

	// TODO constructor
	public ConcreteEdgesGraph() {
	}

	// TODO checkRep
	public void checkRep() {
		for (Edge<L> e : edgelist) {
			assert (vertices.contains(e.getSource()));
			assert (vertices.contains(e.getTarget()));
		}
	}

	@Override
	public boolean add(L vertex) {
		if (!vertices.contains(vertex)) {
			vertices.add(vertex);
			return true;
		} else
			return false;
	}

	@Override
	public int set(L source, L target, int weight) {
		add(source);
		add(target);
		int ret = 0;
		boolean hasEdge = false;

		Iterator<Edge<L>> edgeiter = edgelist.iterator();
		while (edgeiter.hasNext()) {
			Edge<L> e = edgeiter.next();
			if (e.getSource().equals(source) && e.getTarget().equals(target)) {
				hasEdge = true;
				ret = e.getWeight();
				if (weight == 0)
					edgelist.remove(e);
				else
					e.setWeight(weight);
			}
		}

		if (!hasEdge) {
			edgelist.add(new Edge<L>(source, target, weight));
			ret = weight;
		}

		return ret;

	}

	@Override
	public boolean remove(L vertex) {
		if (vertices.contains(vertex)) {
			vertices.remove(vertex);
			Iterator<Edge<L>> edgeiter = edgelist.iterator();
			while (edgeiter.hasNext()) {
				Edge<L> e = edgeiter.next();
				if (e.getSource() == vertex || e.getTarget() == vertex)
					edgelist.remove(e);
			}
			return true;
		} else
			return false;
	}

	@Override
	public Set<L> vertices() {
		return vertices;
	}

	@Override
	public Map<L, Integer> sources(L target) {
		Map<L, Integer> srcmap = new HashMap<L, Integer>();
		Iterator<Edge<L>> edgeiter = edgelist.iterator();
		while (edgeiter.hasNext()) {
			Edge<L> e = edgeiter.next();
			if (e.getTarget().equals(target)) {
				srcmap.put(e.getSource(), e.getWeight());
			}
		}
		return srcmap;
	}

	@Override
	public Map<L, Integer> targets(L source) {
		Map<L, Integer> tarmap = new HashMap<L, Integer>();
		Iterator<Edge<L>> edgeiter = edgelist.iterator();
		while (edgeiter.hasNext()) {
			Edge<L> e = edgeiter.next();
			if (e.getSource().equals(source)) {
				tarmap.put(e.getTarget(), e.getWeight());
			}
		}
		return tarmap;
	}

	// TODO toString()
	public String toString() {
		String ret = "";
		Iterator<Edge<L>> edgeiter = edgelist.iterator();
		while (edgeiter.hasNext()) {
			Edge<L> e = edgeiter.next();
			ret += e.toString();
		}
		return ret;
	}

}

/**
 * TODO specification Immutable. This class is internal to the rep of
 * ConcreteEdgesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Edge<L> {

	// TODO fields
	private final L vertex1;
	private final L vertex2;
	private int weight;

	// Abstraction function:
	// Represents an edge between vertex1 and vertex2
	// Representation invariant:
	// vertex1 and vertex2 are non empty Strings
	// Safety from rep exposure:
	// Strings are immutable and set to final so edge is immutable

	// TODO constructor
	public Edge(L a, L b, int w) {
		vertex1 = a;
		vertex2 = b;
		weight = w;
	}

	// TODO checkRep
	@SuppressWarnings("unused")
	private void checkRep() {
		assert (!((String) vertex1).isEmpty());
		assert (!((String) vertex2).isEmpty());
	}

	// TODO methods
	public L getSource() {
		return vertex1;
	}

	public L getTarget() {
		return vertex2;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int w) {
		weight = w;
	}

	// TODO toString()
	@Override
	public String toString() {
		return "[" + vertex1 + "->" + vertex2 + "=" + weight + "]";
	}

}