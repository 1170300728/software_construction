package circularOrbit;

import java.util.List;
import java.util.Map;

import track.Track;

public interface CircularOrbit<L,E> {
	
	public void addTrack(float radius);
	
	public void removeTrack(float radius);
	
	public void addL(L l);
	
	public void addToTrack(E e,float r);
	
	public void addLToE(L l,E e);

	public void addEToE(E e1,E e2);

	public void addEAndE(E e1,E e2);
	
	public Map<E,List<E>> sourceMap();

	public Map<Track, Map<E, List<E>>> getTrackMap();

	public Map<L, List<E>> getLmap();

	public String getTag();
	
	public void clean();
}
