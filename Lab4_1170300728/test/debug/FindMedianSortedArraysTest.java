package debug;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FindMedianSortedArraysTest {

	FindMedianSortedArrays d = new FindMedianSortedArrays();

	@Test // A全比B小
	public void Atest() {
		int[] A = { 1, 1, 1 };
		int[] B = { 5, 6, 7 };
		double c = d.findMedianSortedArrays(A, B);
		assertEquals(3.0, c, 0.01);
	}

	@Test // A有元素比B大
	public void btest() {
		int[] A = { 1, 1, 8 };
		int[] B = { 5, 6, 7 };
		double c = d.findMedianSortedArrays(A, B);
		assertEquals(5.5, c, 0.01);
	}

	@Test // A是null
	public void ctest() {
		int[] A = null;
		int[] B = { 5, 6, 7 };
		double c = d.findMedianSortedArrays(A, B);
		assertEquals(6.0, c, 0.01);
	}

	@Test // A和B都是null
	public void dtest() {
		int[] A = null;
		int[] B = null;
		double c = d.findMedianSortedArrays(A, B);
		assertEquals(-1, c, 0.01);
	}

	@Test // A有元素比B大，总元素奇数个
	public void etest() {
		int[] A = { 1, 1, 8, 9 };
		int[] B = { 5, 6, 7 };
		double c = d.findMedianSortedArrays(A, B);
		assertEquals(6.0, c, 0.01);
	}

}