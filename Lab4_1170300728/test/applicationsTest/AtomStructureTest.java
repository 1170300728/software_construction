package applicationsTest;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import applications.AtomStructure;
import physicalObject.electron;

public class AtomStructureTest {
	String filePath = "src/AtomTest.txt";
	String errorPath1 = "src/AtomTestError1.txt";
	String errorPath2 = "src/AtomTestError2.txt";
	String errorPath3 = "src/AtomTestError3.txt";

	@Test
	public void Readtest() {
		AtomStructure atom = new AtomStructure("test");
		atom.readfromfile(filePath);
		assertEquals(2, atom.getnumoftracks());
		assertEquals(2, atom.getelectrontracks().length);
		assertEquals("C", atom.getelementname());
	}

	@Test
	public void Createtest() {
		AtomStructure atom = new AtomStructure("test");
		atom.readfromfile(filePath);
		atom.createcir();
		assertEquals(2, atom.getTrackMap().keySet().size());
		assertEquals(6, atom.sourceMap().keySet().size());		
	}
	
	@Test
	public void Movetest() {
		AtomStructure atom = new AtomStructure("test");
		atom.readfromfile(filePath);
		atom.createcir();
		atom.moveatob(2,1);
		for(Map<electron, List<electron>>m:atom.getTrackMap().values()) {
			assertEquals(3, m.keySet().size());	
		}
	}
	
	@Test
	public void Entropytest() {
		AtomStructure atom = new AtomStructure("test");
		atom.readfromfile(filePath);
		atom.createcir();
		atom.getEntropy();
	}
	
	@Test
	public void Errortest1() {
		AtomStructure atom = new AtomStructure("test");
		atom.readfromfile(errorPath1);
	}
	

	@Test
	public void Errortest2() {
		AtomStructure atom = new AtomStructure("test");
		atom.readfromfile(errorPath2);
	}

	@Test
	public void Errortest3() {
		AtomStructure atom = new AtomStructure("test");
		atom.readfromfile(errorPath3);
	}
	
	@Test
	public void Errortest123() {
		AtomStructure atom = new AtomStructure("test");
		atom.readfromfile(errorPath1);
		atom.readfromfile(errorPath2);
		atom.readfromfile(errorPath3);
	}
}
