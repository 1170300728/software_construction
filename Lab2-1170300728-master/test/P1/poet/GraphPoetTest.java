/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {

	/**
	 * Testing strategy -Test assertions enabled -Test when corpus doesn't exist
	 * -Test when corpus is empty -Test when input is empty -Test that spaces
	 * between words are one space long and words contain letters only -Test more
	 * than one space, words contain letters and numbers
	 */
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	// TODO tests
	// corpus doesn't exist
	@Test(expected = IOException.class)
	public void testNonexistentCorpus() throws IOException {
		File givenCorpus = new File("test/P1/poet/NotFound.txt");
		@SuppressWarnings("unused")
		GraphPoet graph = new GraphPoet(givenCorpus);
	}

	// corpus is empty
	@Test
	public void testPoemEmptyCorpus() throws IOException {
		File givenCorpus = new File("test/P1/poet/empty_test.txt");
		GraphPoet graph = new GraphPoet(givenCorpus);
		String input = "test the empty one";
		String outputPoem = graph.poem(input);
		assertEquals(input, outputPoem);
	}

	// input is empty
	@Test
	public void testPoemEmptyInput() throws IOException {
		File givenCorpus = new File("test/P1/poet/1170300728_test.txt");
		GraphPoet graph = new GraphPoet(givenCorpus);
		String input = "";
		String outputPoem = graph.poem(input);
		assertEquals(input, outputPoem);
	}

	// all spaces between words are one space long and words contain letters only
	@Test
	public void testPoem() throws IOException {
		File givenCorpus = new File("test/P1/poet/1170300728_test.txt");
		GraphPoet graph = new GraphPoet(givenCorpus);
		// System.out.println(graph.toString());
		String input = "each street,";
		String outputPoem = graph.poem(input);
		String correctPoem = "each chartered street,";
		assertEquals(correctPoem, outputPoem);
	}

	// more than one space, words contain letters and numbers
	@Test
	public void testPoemLettersOrNumbers() throws IOException {
		File givenCorpus = new File("test/P1/poet/1170300728_test.txt");
		GraphPoet graph = new GraphPoet(givenCorpus);
		// System.out.println(graph.toString());
		String input = "And blights plagues marriage-hearse.";
		String outputPoem = graph.poem(input);
		String correctPoem = "And blights with plagues the marriage-hearse.";
		assertEquals(correctPoem, outputPoem);
	}
}