package P2;

import java.util.Map;
import P1.graph.ConcreteEdgesGraph;

public class FriendshipGraph {
	int Vnums = 0;
	ConcreteEdgesGraph<Person> vertexlist = new ConcreteEdgesGraph<Person>();

	public void addVertex(Person someone) {
		vertexlist.add(someone);
		Vnums++;
	}

	public void addEdge(Person src, Person dest) {
		vertexlist.set(src, dest, 1);
	}

	public int getDistance(Person start, Person end) {
		Person[] Vertexs = new Person[Vnums];
		int i = 0;

		for (Person key : vertexlist.vertices) {
			Vertexs[i] = key;
			i++;
		}
		int x = -1, y = -1;
		for (i = 0; i < Vnums; i++) {
			if (x >= 0 && y >= 0)
				break;
			else {
				if (Vertexs[i] == start) {
					x = i;
				}
				if (Vertexs[i] == end) {
					y = i;
				}
			}
		}
		if (x == -1 || y == -1) {
			return -1;
		}

		int[][] Graph = new int[Vnums][Vnums];
		int temp = -1;

		for (i = 0; i < Vnums; i++) {
			Map<Person, Integer> map = vertexlist.targets(Vertexs[i]);
			for (int j = 0; j < Vnums; j++) {
				Graph[i][j] = 0;
			}
			for (Person key : map.keySet()) {
				for (int j = 0; j < Vnums; j++) {
					if (Vertexs[j] == key) {
						temp = j;
						break;
					}
				}
				if (temp >= 0)
					Graph[i][temp] = 1;
				temp = -1;
			}
		}
		int[] s = new int[Vnums];
		int[][] distance = Graph;
		int[] path = new int[Vnums];

		for (i = 0; i < Vnums; i++) {
			if (Graph[x][i] > 0) {
				path[i] = x;
			} else {
				path[i] = -1;
			}
		}

		s[x] = 1;

		for (i = 0; i < Vnums; i++) {
			int min = Integer.MAX_VALUE;
			int v = 0;
			for (int j = 0; j < Vnums; j++) {
				if (s[j] != 1 && x != j && distance[x][j] != 0 && distance[x][j] < min) {
					min = distance[x][j];
					v = j;
				}
			}
			s[v] = 1;

			for (int j = 0; j < Vnums; j++) {
				if (s[j] != 1 && distance[v][j] != 0
						&& (min + distance[v][j] < distance[x][j] || distance[x][j] == 0)) {
					distance[x][j] = min + distance[v][j];
					path[j] = v;

				}
			}

		}

		return ((distance[x][y] == 0) ? ((x == y) ? 0 : -1) : distance[x][y]);

	}

}
