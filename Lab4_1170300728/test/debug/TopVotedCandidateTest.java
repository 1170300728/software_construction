package debug;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TopVotedCandidateTest {

	@Test
	public void votetest() {
		int[] persons= {0,1,1,0,0,1,0};
		int[] times={0,5,10,15,20,25,30};
		int[] qs={3,12,25,15,24,8};
		int[] output= {0,1,1,0,0,1};
		TopVotedCandidate toptest = new TopVotedCandidate(persons,times);
		for(int i=0;i<qs.length;i++) {
			assertEquals(output[i],toptest.q(qs[i]));
		}
	}
}
