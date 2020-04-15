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
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
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
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    
    @Test
    public void testaddvertices() {
        // TODO you may use, change, or remove this test
    	ConcreteEdgesGraph<String> edgetest1 = new ConcreteEdgesGraph<String>();
    	edgetest1.add(vertex1);
    	edgetest1.add(vertex2);
    	edgetest1.set(vertex1,vertex2, i1);
    	Set<String> hash_Set = new HashSet<String>();
    	hash_Set.add("A");
    	hash_Set.add("B");
    	assertEquals("expected new graph to have a new vertix",
               hash_Set, edgetest1.vertices());
    }
    @Test
    public void testaddDuplicatevertices() {
        // TODO you may use, change, or remove this test
    	ConcreteEdgesGraph<String> edgetest3 = new ConcreteEdgesGraph<String>();
    	edgetest3.add(vertex1);
    	edgetest3.add(vertex2);
    	edgetest3.add(vertex1);
    	edgetest3.add(vertex2);
    	Set<String> hash_Set = new HashSet<String>();
    	hash_Set.add("A");
    	hash_Set.add("B");
    	assertEquals("expected new graph to have the same number of vertix and no duplicate vertex",
               hash_Set, edgetest3.vertices());
    }
    
    @Test
    public void testRemovevertices() {
        // TODO you may use, change, or remove this test
    	ConcreteEdgesGraph<String> edgetest2 = new ConcreteEdgesGraph<String>();
    	edgetest2.add(vertex1);
    	edgetest2.add(vertex2);
    	edgetest2.remove(vertex1);
    	Set<String> hash_Set = new HashSet<String>();
    	hash_Set.add("B");
    	assertEquals("expected new graph to have a removed vertix",
               hash_Set, edgetest2.vertices());
    }
    
    @Test
    public void testSources() {
        // TODO you may use, change, or remove this test
    	ConcreteEdgesGraph<String> edgetest4 = new ConcreteEdgesGraph<String>();
    	edgetest4.add(vertex1);
    	edgetest4.add(vertex2);
    	edgetest4.add(vertex3);
    	edgetest4.add(vertex4);
    	edgetest4.set(vertex1,vertex2, i1);
    	edgetest4.set(vertex3,vertex2, i1);
    	edgetest4.set(vertex4,vertex2, i1);
    	edgetest4.set(vertex1,vertex3, i1);
    	edgetest4.set(vertex2,vertex3, i1);
    	edgetest4.set(vertex4,vertex3, i1);
    	Map<String, Integer> expectedMap = new HashMap<String, Integer>();
    	expectedMap.put(vertex1,i1);
    	expectedMap.put(vertex3,i1);
    	expectedMap.put(vertex4,i1);
    	
    	assertEquals("expected map of target vertex",
               expectedMap, edgetest4.sources(vertex2));
    }
    
    @Test
    public void testTarget() {
        // TODO you may use, change, or remove this test
    	ConcreteEdgesGraph<String> edgetest5 = new ConcreteEdgesGraph<String>();
    	edgetest5.add(vertex1);
    	edgetest5.add(vertex2);
    	edgetest5.add(vertex3);
    	edgetest5.add(vertex4);
    	edgetest5.set(vertex1,vertex2, i1);
    	edgetest5.set(vertex3,vertex2, i1);
    	edgetest5.set(vertex4,vertex2, i1);
    	edgetest5.set(vertex1,vertex3, i1);
    	edgetest5.set(vertex2,vertex3, i1);
    	edgetest5.set(vertex1,vertex4, i1);
    	Map<String, Integer> expectedMap = new HashMap<String, Integer>();
    	expectedMap.put(vertex2,i1);
    	expectedMap.put(vertex3,i1);
    	expectedMap.put(vertex4,i1);

    	assertEquals("expected map of target vertex",
               expectedMap, edgetest5.targets(vertex1));
    }
}