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
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //   TODO
    ConcreteEdgesGraph<String> CEG = new ConcreteEdgesGraph<String>();
    ConcreteVerticesGraph<String> CVG = new ConcreteVerticesGraph<String>();
    String vertex1 = "one";
    String vertex2 = "two";
    String vertex3 = "three";
    String vertex4 = "four";
    int w1 = 1;
    int w2 = 2;
    int w3 = 3;
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
 // TODO other tests for instance methods of Graph
    
    @Test
    public void testaddvertices() {
        // TODO you may use, change, or remove this test
    	CEG.add(vertex1);
    	CEG.add(vertex2);
    	CEG.set(vertex1,vertex2, w1);
    	Set<String> hash_Set = new HashSet<String>();
    	hash_Set.add("one");
    	hash_Set.add("two");
    	assertEquals("expected new graph to have a new vertix",
               hash_Set, CEG.vertices());
    }
    @Test
    public void testaddDuplicatevertices() {
        // TODO you may use, change, or remove this test
    	CEG.add(vertex1);
    	CEG.add(vertex2);
    	Set<String> hash_Set = new HashSet<String>();
    	hash_Set.add("one");
    	hash_Set.add("two");
    	assertEquals("expected new graph to have the same number of vertix and no duplicate vertex",
               hash_Set, CEG.vertices());
    }
    
    @Test
    public void testRemovevertices() {
        // TODO you may use, change, or remove this test
    	CEG.remove(vertex1);
    	Set<String> hash_Set = new HashSet<String>();
    	hash_Set.add("two");
    	assertEquals("expected new graph to have a removed vertix",
               hash_Set, CEG.vertices());
    }
    
    @Test
    public void testSources() {
        // TODO you may use, change, or remove this test
    	CEG.add(vertex1);
    	CEG.add(vertex2);
    	CEG.add(vertex3);
    	CEG.add(vertex4);
    	CEG.set(vertex1,vertex2, w1);
    	CEG.set(vertex3,vertex2, w1);
    	CEG.set(vertex4,vertex2, w1);
    	CEG.set(vertex1,vertex3, w1);
    	CEG.set(vertex2,vertex3, w1);
    	CEG.set(vertex4,vertex3, w1);
    	Map<String, Integer> expectedMap = new HashMap<String, Integer>();
    	expectedMap.put(vertex1,w1);
    	expectedMap.put(vertex3,w1);
    	expectedMap.put(vertex4,w1);

    	assertEquals("expected map of target vertex",
               expectedMap, CEG.sources(vertex2));
    }
}