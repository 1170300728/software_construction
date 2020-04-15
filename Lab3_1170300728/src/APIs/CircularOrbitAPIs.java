package APIs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import circularOrbit.CircularOrbit;
import track.Track;

public class CircularOrbitAPIs<L, E> {

	public static <L, E> double getObjectDistributionEntropy(CircularOrbit<L, E> c) {
		Map<Track, Map<E, List<E>>> trackmap = c.getTrackMap();
		Map<E, List<E>> sourcemap = c.sourceMap();
		double temp, Entro = 0;
		int Enum = sourcemap.keySet().size();
		if (Enum == 0)
			return 0;
		else
			for (Track t : trackmap.keySet()) {
				temp = (double) trackmap.get(t).keySet().size() / (double) Enum;
				if (temp != 0)
					Entro -= temp * Math.log(temp);
			}
		return Entro;
	}

	public static <L, E> int getLogicalDistance(CircularOrbit<L, E> c, E e1, E e2) {
		Map<L, List<E>> lmap = c.getLmap();
		Map<E, List<E>> sourcemap = c.sourceMap();
		String[] Users = new String[lmap.keySet().size() + sourcemap.keySet().size()];
		int i = 0, Vnums = lmap.keySet().size() + sourcemap.keySet().size();
		Map<L, Integer> llist = new HashMap<>();
		Map<E, Integer> elist = new HashMap<>();

		System.out.println(Vnums+","+lmap.keySet().size()+","+sourcemap.keySet().size());
		for (L l : lmap.keySet()) {
			Users[i] = l.toString();
			llist.put(l, i);
			i++;
		}
		for (E e : sourcemap.keySet()) {
			Users[i] = e.toString();
			elist.put(e, i);
			i++;
		}
		int x = -1, y = -1;
		if (elist.keySet().contains(e1))
			x = elist.get(e1);
		if (elist.keySet().contains(e2))
			y = elist.get(e2);
		if (x == -1 || y == -1) {
			return -1;
		}
			System.out.println(x+","+y);

		int[][] Graph = new int[Vnums][Vnums];

		for (i = 0; i < Vnums; i++) {
			for (int j = 0; j < Vnums; j++) {
				Graph[i][j] = 0;
			}
			for (L l : lmap.keySet()) {
				for (E e : lmap.get(l)) {
					Graph[llist.get(l)][elist.get(e)] = 1;
					Graph[elist.get(e)][llist.get(l)] = 1;
				}
			}
			for (E s : sourcemap.keySet()) {
				for (E e : sourcemap.get(s)) {
					Graph[elist.get(s)][elist.get(e)] = 1;
					Graph[elist.get(e)][elist.get(s)] = 1;
				}
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

			for (int j = 0; j < Vnums; j++) {
				System.out.print(Graph[i][j]);
			}
			System.out.println();
		}
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
