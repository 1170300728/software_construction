package applicationsTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import applications.TrackGame;
import physicalObject.FriendUser;
import track.Track;

public class TrackGameTest {
	String filePath = "src/TrackTest.txt";

	@Test
	public void Readtest() {
		TrackGame trac = new TrackGame("test");
		trac.readfromfile(filePath);
		assertEquals(7, trac.getathletes().size());
		assertEquals(4, trac.getnumoftrack());
		assertEquals("100", trac.getgame());
	}
	@Test
	public void Atuotest1() {
		TrackGame trac = new TrackGame("test");
		trac.readfromfile(filePath);
		trac.autogame1();
		assertEquals(4, trac.getTrackMap().keySet().size());		
	}
	@Test
	public void Atuotest2() {
		TrackGame trac = new TrackGame("test");
		trac.readfromfile(filePath);
		trac.autogame2();
		assertEquals(4, trac.getTrackMap().keySet().size());		
		assertEquals(7, trac.sourceMap().keySet().size());
	}
	@Test
	public void addandremovetest1() {
		TrackGame trac = new TrackGame("test");
		trac.readfromfile(filePath);
		trac.autogame1();
		trac.addtrack();
		assertEquals(5, trac.getTrackMap().keySet().size());	
		assertEquals(7, trac.sourceMap().keySet().size());
		trac.autogame1();
		trac.removetrack();
		assertEquals(4, trac.getTrackMap().keySet().size());	
		assertEquals(5, trac.sourceMap().keySet().size());
		trac.autogame1();	
		assertEquals(4, trac.getTrackMap().keySet().size());	
		assertEquals(7, trac.sourceMap().keySet().size());
	}
	@Test
	public void addandremovetest2() {
		TrackGame trac = new TrackGame("test");
		trac.readfromfile(filePath);
		trac.autogame2();
		trac.addtrack();
		assertEquals(5, trac.getTrackMap().keySet().size());	
		assertEquals(7, trac.sourceMap().keySet().size());
		trac.autogame2();
		trac.removetrack();
		assertEquals(4, trac.getTrackMap().keySet().size());	
		assertEquals(6, trac.sourceMap().keySet().size());
		trac.autogame2();	
		assertEquals(4, trac.getTrackMap().keySet().size());	
		assertEquals(7, trac.sourceMap().keySet().size());
	}
}
