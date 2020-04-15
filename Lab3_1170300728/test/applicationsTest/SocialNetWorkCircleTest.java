package applicationsTest;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import applications.SocialNetworkCircle;
import physicalObject.FriendUser;
import physicalObject.electron;
import track.Track;

public class SocialNetWorkCircleTest {
	String filePath = "src/SocialTest.txt";

	@Test
	public void Readtest() {
		SocialNetworkCircle social = new SocialNetworkCircle("test");
		social.readfromfile(filePath);
		assertEquals(3, social.getfriends().size());
		assertEquals(5, social.getties().size());
		assertEquals("A", social.getcentral().getName());
	}

	@Test
	public void Maketest() {
		SocialNetworkCircle social = new SocialNetworkCircle("test");
		social.readfromfile(filePath);
		social.makemap();
		assertEquals(2, social.getcmap().size());
		assertEquals(3, social.getfmap().size());
		for (FriendUser f : social.getfmap().keySet())
			assertEquals(2, social.getfmap().get(f).size());
	}

	@Test
	public void Createtest() {
		SocialNetworkCircle social = new SocialNetworkCircle("test");
		social.readfromfile(filePath);
		social.makemap();
		social.createcir();
		assertEquals(2, social.getTrackMap().keySet().size());
		assertEquals(3, social.sourceMap().keySet().size());
		for(Track t :social.getTrackMap().keySet()) {
			for(FriendUser f :social.getTrackMap().get(t).keySet()) {
				assertEquals(2, social.getTrackMap().get(t).get(f).size());				
			}
		}
		for (FriendUser f : social.sourceMap().keySet())
			assertEquals(2, social.sourceMap().get(f).size());
	}

	@Test
	public void Logictest() {
		SocialNetworkCircle social = new SocialNetworkCircle("test");
		social.readfromfile(filePath);
		social.makemap();
		social.createcir();
		assertEquals(1, social.LogicalDistance("B", "D"));
	}
}
