package P2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

public class FriendshipGraphTest {

	Person A = new Person("A");
	Person B = new Person("B");
	Person C = new Person("C");
	Person D = new Person("D");
	Person E = new Person("E");
	Person F = new Person("F");
	FriendshipGraph Graph = new FriendshipGraph();
	ArrayList<Integer> edgelist = new ArrayList<Integer>();

	@Test
	public void addVertextest() {
		Graph.addVertex(A);
		Graph.addVertex(B);
		Graph.addVertex(C);
		Graph.addVertex(D);
		Graph.addVertex(E);
		assertEquals(5, Graph.Vnums);
	}

	@Test
	public void getDistancetest() {
		Graph.addVertex(A);
		Graph.addVertex(B);
		Graph.addVertex(C);
		Graph.addVertex(D);
		Graph.addVertex(E);

		Graph.addEdge(A, E);
		Graph.addEdge(E, A);
		Graph.addEdge(D, C);
		Graph.addEdge(C, D);
		Graph.addEdge(A, D);
		Graph.addEdge(D, A);
		Graph.addEdge(E, B);
		Graph.addEdge(B, E);
		Graph.addEdge(C, B);
		Graph.addEdge(B, C);
		assertEquals(1, Graph.getDistance(A, D));
		assertEquals(2, Graph.getDistance(D, B));
		assertEquals(0, Graph.getDistance(D, D));
		assertEquals(-1, Graph.getDistance(F, A));
	}

	public void getDistancetestex() {
		Graph.addVertex(A);
		Graph.addVertex(B);
		Graph.addVertex(C);
		Graph.addVertex(D);
		Graph.addVertex(E);

		Graph.addEdge(A, E);
		Graph.addEdge(E, A);
		Graph.addEdge(C, D);
		Graph.addEdge(A, D);
		Graph.addEdge(D, A);
		Graph.addEdge(B, E);
		Graph.addEdge(C, B);
		Graph.addEdge(B, C);
		assertEquals(1, Graph.getDistance(A, D));
		assertEquals(-1, Graph.getDistance(D, B));
		assertEquals(0, Graph.getDistance(D, D));
		assertEquals(-1, Graph.getDistance(F, A));
	}
}
