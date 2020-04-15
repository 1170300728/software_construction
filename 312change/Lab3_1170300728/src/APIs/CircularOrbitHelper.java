package APIs;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import centralObject.centralobjects;
import circularOrbit.CircularOrbit;
import physicalObject.Athlete;
import physicalObject.physicalobjects;
import track.Track;

public class CircularOrbitHelper<L, E> extends JFrame {

	public CircularOrbitHelper(CircularOrbit<L, E> c) {
		super();
		setSize(1500, 1000);
		setResizable(false);
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		MyPanel<L, E> mypanel = new MyPanel<L, E>(c);
		add(mypanel); // 将面板类的实例添加到窗体容器中
	}

	public static <L, E> void visualize(CircularOrbit<L, E> c) {
		CircularOrbitHelper<L, E> coh = new CircularOrbitHelper<L, E>(c);
		coh.setVisible(true); // 显示窗体
	}

	class MyPanel<L, E> extends JPanel {
		public CircularOrbit<L, E> cir;

		public MyPanel(CircularOrbit<L, E> c) {
			cir = c;
		}

		public void paint(Graphics g) { // 重写paint()方法
			Map<Track, Map<E, List<E>>> trackmap = cir.getTrackMap();
			Map<L, List<E>> lmap = cir.getLmap();
			int n = trackmap.keySet().size();
			int centerx = 40 * n, centery = 40 * n;
			Track[] tracks = sorttrack(trackmap, n);
			Map<E,Dot>dotmap=new HashMap<>();

			g.fillOval(centerx-10, centery-10, 20, 20);
			for (int i = 0; i < n; i++) {
				drawcircle(centerx, centery, 50 + i * 40, g);
				int num = trackmap.get(tracks[i]).keySet().size();
				int j = 0;
				for (E e : trackmap.get(tracks[i]).keySet()) {
					Dot d = new Dot(
							centerx - (int) (Math.sin(Math.toRadians(360 * (double) j / (double) num)) * (i * 20 + 25)),
							centery - (int) (Math.cos(Math.toRadians(360 * (double) j / (double) num)) * (i * 20 + 25))
							);
					drawcircle(d.getx(), d.gety(), 10, g);
					j++;
					dotmap.put(e,d);
				}
			}
			if(!lmap.isEmpty()) {
				for(L l:lmap.keySet()) {
					if(!lmap.get(l).isEmpty()) {
						for(E e:lmap.get(l)) {
							g.drawLine(centerx, centery, dotmap.get(e).getx(), dotmap.get(e).gety());
						}
					}
				}
			}

			for(Track t:trackmap.keySet()) {
				if(!trackmap.get(t).isEmpty())
				for(E e1:trackmap.get(t).keySet()) {
					if(!trackmap.get(t).get(e1).isEmpty())
					for(E e2:trackmap.get(t).get(e1)) {
						g.drawLine(dotmap.get(e1).getx(), dotmap.get(e1).gety(), dotmap.get(e2).getx(), dotmap.get(e2).gety());
					}
				}
			}
		}

		public Track[] sorttrack(Map<Track, Map<E, List<E>>> trackmap, int n) {
			int i = 0;
			Track temp = null;
			Track[] tracks = new Track[n];
			for (Track t : trackmap.keySet()) {
				tracks[i] = t;
				i++;
			}
			for (int j = 0; j < n; j++) {
				for (int k = j + 1; k < n; k++) {
					if (tracks[j].GetRadius() > tracks[k].GetRadius()) {
						temp = tracks[j];
						tracks[j] = tracks[k];
						tracks[k] = temp;
					}
				}
			}

			return tracks;
		}

		public void drawcircle(int x, int y, int r, Graphics g) {
			g.drawArc(x - r / 2, y - r / 2, r, r, 0, 360);
		}
	}

	class Dot {
		int x, y;

		public Dot(int x0, int y0) {
			x = x0;
			y = y0;
		}

		public int getx() {
			return x;
		}

		public int gety() {
			return y;
		}

	}
}