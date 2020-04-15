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
	String errorPath1 = "src/SocialTestErrors1.txt";
	String errorPath2 = "src/SocialTestErrors2.txt";
	String errorPath3 = "src/SocialTestErrors3.txt";

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
		for (Track t : social.getTrackMap().keySet()) {
			for (FriendUser f : social.getTrackMap().get(t).keySet()) {
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

	@Test
	public void Entropytest() {
		SocialNetworkCircle social = new SocialNetworkCircle("test");
		social.readfromfile(filePath);
		social.makemap();
		social.createcir();
		social.getEntropy();
	}

	@Test
	public void Errortest1() {
		SocialNetworkCircle social = new SocialNetworkCircle("test");
		social.readfromfile(errorPath1);
	}

	@Test
	public void Errortest2() {
		SocialNetworkCircle social = new SocialNetworkCircle("test");
		social.readfromfile(errorPath2);
	}

	@Test
	public void Errortest3() {
		SocialNetworkCircle social = new SocialNetworkCircle("test");
		social.readfromfile(errorPath3);
	}

	@Test
	public void Errortest123() {
		SocialNetworkCircle social = new SocialNetworkCircle("test");
		social.readfromfile(errorPath1);
		social.readfromfile(errorPath2);
		social.readfromfile(errorPath3);
	}
}
