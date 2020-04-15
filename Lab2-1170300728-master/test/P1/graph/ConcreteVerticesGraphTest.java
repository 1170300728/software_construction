/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {

	/*
	 * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
	 */
	@Override
	public Graph<String> emptyInstance() {
		return new ConcreteVerticesGraph<String>();
	}

	String vertex1 = "A";
	String vertex2 = "B";
	String vertex3 = "C";
	String vertex4 = "D";
	int i1 = 1;
	int i2 = 2;
	int i3 = 3;

	/**
	 * Overridden by implementation-specific test classes.
	 * 
	 * @return a new empty graph of the particular implementation being tested
	 */

	@Test
	public void testInitialVerticesEmpty() {
		assertEquals("expected new graph to have no vertices", Collections.emptySet(), emptyInstance().vertices());
	}

	@Test
	public void testaddvertices() {
		// TODO you may use, change, or remove this test
		ConcreteVerticesGraph<String> vertextest1 = new ConcreteVerticesGraph<String>();
		vertextest1.add(vertex1);
		vertextest1.add(vertex2);
		vertextest1.set(vertex1, vertex2, i1);
		Set<String> hash_Set = new HashSet<String>();
		hash_Set.add("A");
		hash_Set.add("B");
		assertEquals("expected new graph to have a new vertix", hash_Set, vertextest1.vertices());
	}

	@Test
	public void testaddDuplicatevertices() {
		// TODO you may use, change, or remove this test
		ConcreteVerticesGraph<String> vertextest3 = new ConcreteVerticesGraph<String>();
		vertextest3.add(vertex1);
		vertextest3.add(vertex2);
		vertextest3.add(vertex1);
		vertextest3.add(vertex2);
		Set<String> hash_Set = new HashSet<String>();
		hash_Set.add("A");
		hash_Set.add("B");
		assertEquals("expected new graph to have the same number of vertix and no duplicate vertex", hash_Set,
				vertextest3.vertices());
	}

	@Test
	public void testRemovevertices() {
		// TODO you may use, change, or remove this test
		ConcreteVerticesGraph<String> vertextest2 = new ConcreteVerticesGraph<>();
		vertextest2.add(vertex1);
		vertextest2.add(vertex2);
		vertextest2.add(vertex3);
		vertextest2.add(vertex4);
		vertextest2.set(vertex1, vertex2, 1);
		vertextest2.set(vertex3, vertex2, 1);
		vertextest2.set(vertex4, vertex2, 1);
		vertextest2.set(vertex1, vertex3, 1);
		vertextest2.set(vertex2, vertex3, 1);
		vertextest2.set(vertex4, vertex3, 1);

		Map<String, Integer> expectedMap1 = new HashMap<String, Integer>();
		Map<String, Integer> expectedMap2 = new HashMap<String, Integer>();
		Map<String, Integer> expectedMap3 = new HashMap<String, Integer>();
		expectedMap1.put(vertex2, 1);
		expectedMap3.put(vertex2, 1);

		vertextest2.remove(vertex3);

		assertEquals(expectedMap1, vertextest2.targets(vertex1));
		assertEquals(expectedMap2, vertextest2.targets(vertex3));
		assertEquals(expectedMap2, vertextest2.sources(vertex3));
		assertEquals(expectedMap3, vertextest2.targets(vertex4));
	}

	@Test
	public void testSources() {
		// TODO you may use, change, or remove this test
		ConcreteVerticesGraph<String> vertextest4 = new ConcreteVerticesGraph<String>();
		vertextest4.add(vertex1);
		vertextest4.add(vertex2);
		vertextest4.add(vertex3);
		vertextest4.add(vertex4);
		vertextest4.set(vertex1, vertex2, i1);
		vertextest4.set(vertex3, vertex2, i1);
		vertextest4.set(vertex4, vertex2, i1);
		vertextest4.set(vertex1, vertex3, i1);
		vertextest4.set(vertex2, vertex3, i1);
		vertextest4.set(vertex4, vertex3, i1);
		Map<String, Integer> expectedMap = new HashMap<String, Integer>();
		expectedMap.put(vertex1, i1);
		expectedMap.put(vertex3, i1);
		expectedMap.put(vertex4, i1);

		assertEquals("expected map of target vertex", expectedMap, vertextest4.sources(vertex2));
		System.out.print(vertextest4);
	}

	@Test
	public void testTarget() {
		// TODO you may use, change, or remove this test
		ConcreteVerticesGraph<String> vertextest5 = new ConcreteVerticesGraph<String>();
		vertextest5.add(vertex1);
		vertextest5.add(vertex2);
		vertextest5.add(vertex3);
		vertextest5.add(vertex4);
		vertextest5.set(vertex1, vertex2, w1);
		vertextest5.set(vertex3, vertex2, w1);
		vertextest5.set(vertex4, vertex2, w1);
		vertextest5.set(vertex1, vertex3, w1);
		vertextest5.set(vertex2, vertex3, w1);
		vertextest5.set(vertex1, vertex4, w1);
		Map<String, Integer> expectedMap = new HashMap<String, Integer>();
		expectedMap.put(vertex2, w1);
		expectedMap.put(vertex3, w1);
		expectedMap.put(vertex4, w1);

		assertEquals("expected map of target vertex", expectedMap, vertextest5.targets(vertex1));
	}

}
