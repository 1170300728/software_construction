package circularOribitTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import org.junit.Test;

import circularOrbit.ConcreteCircularOrbit;
import track.Track;

public class ConcreteCircularOrbitTest {
	ConcreteCircularOrbit<String,String> cco=new ConcreteCircularOrbit<String, String> ("game");
	
	@Test
	public void Tracktest() {
		cco.addTrack(1);
		cco.addTrack(2);
		cco.addTrack(3);
		cco.addTrack(4);
		cco.addTrack(5);
		assertEquals(5, cco.getTrackMap().keySet().size());
		cco.addTrack(4);
		assertEquals(5, cco.getTrackMap().keySet().size());
		cco.removeTrack(1);
		cco.removeTrack(4);
		assertEquals(3, cco.getTrackMap().keySet().size());
		cco.removeTrack(4);
		assertEquals(3, cco.getTrackMap().keySet().size());
	}
	
	@Test
	public void Ltest() {
		cco.addL("1");
		cco.addL("2");
		cco.addL("3");
		assertEquals(3, cco.getLmap().keySet().size());
		cco.addL("2");
		assertEquals(3, cco.getLmap().keySet().size());
	}

	@Test
	public void Etest() {
		cco.addTrack(1);
		cco.addTrack(2);
		cco.addTrack(3);
		cco.addTrack(4);
		cco.addTrack(5);
		cco.addToTrack("1",1);
		cco.addToTrack("2",1);
		cco.addToTrack("3",1);
		Track t1=null,t2=null;
		for(Track t:cco.getTrackMap().keySet()) {
			if(t.GetRadius()==1)
				t1=t;
		}
		assertEquals(3, cco.getTrackMap().get(t1).size());
		cco.addToTrack("4",2);
		for(Track t:cco.getTrackMap().keySet()) {
			if(t.GetRadius()==2)
				t2=t;
		}
		assertEquals(3, cco.getTrackMap().get(t1).size());
		assertEquals(1, cco.getTrackMap().get(t2).size());
	}
}
