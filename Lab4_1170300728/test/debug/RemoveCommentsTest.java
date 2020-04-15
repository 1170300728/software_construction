package debug;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RemoveCommentsTest {

	@Test
	public void removetest1() {
		String[] source = { "/*Test program */", "int main()", "{ ", "  // variable declaration ", "int a, b, c;",
				"/* This is a test", "   multiline  ", "   comment for ", "   testing */", "a = b + c;", "}" };
		String[] output = { "int main()", "{ ", "  ", "int a, b, c;", "a = b + c;", "}" };
		for (int i = 0; i < output.length; i++) {
			assertEquals(output[i], RemoveComments.removeComments(source).get(i));
		}
	}
	
	@Test
	public void removetest2() {
		String[] source = {"a/*comment", "line", "more_comment*/b"};
		String[] output = { "ab" };
		for (int i = 0; i < output.length; i++) {
			assertEquals(output[i], RemoveComments.removeComments(source).get(i));
		}
	}
}
