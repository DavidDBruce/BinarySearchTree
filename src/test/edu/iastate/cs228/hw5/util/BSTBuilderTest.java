package test.edu.iastate.cs228.hw5.util;

import org.junit.Assert;
import org.junit.Test;

import edu.iastate.cs228.hw5.BST;
import edu.iastate.cs228.hw5.BSTBuilder;

/**
 * Tests to validate the BSTBuilder class
 * 
 * @author Brandon
 * 
 */
public class BSTBuilderTest {

	// Shouldn't really take that long to run each test
	private static final int timeout = 1000;

	/*
	 * Naming: Small tree - Depth 1, Medium tree - Depth 2, Large tree - Depth 3
	 * 
	 * Implicit - Don't provide the last level of all nulls, Explicit - Do
	 * provide last level of all nulls
	 * 
	 * Not Enough Data - A level doesn't have enough children for the number of
	 * parents
	 */

	@Test(timeout = timeout)
	public void testNull() {
		buildAndTest(null);
	}

	@Test(timeout = timeout)
	public void testEmpty() {
		buildAndTest(new String[][] {});
	}

	@Test(timeout = timeout)
	public void testRoot_Implicit() {
		buildAndTest(new String[][] { { "A" } });
	}

	@Test(timeout = timeout)
	public void testRoot_Explicit() {
		buildAndTest(new String[][] { { "A" }, { null, null } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testRootNotEnoughData() {
		buildAndTest(new String[][] { { "A" }, { null } });
	}

	@Test(timeout = timeout)
	public void testFullTreeSmall_Implicit() {
		buildAndTest(new String[][] { { "B" }, { "A", "C" } });
	}

	@Test(timeout = timeout)
	public void testFullTreeSmall_Explicit() {
		buildAndTest(new String[][] { { "B" }, { "A", "C" }, { null, null, null, null } });
	}

	@Test(timeout = timeout)
	public void testFullTreeMedium_Implicit() {
		buildAndTest(new String[][] { { "D" }, { "B", "F" }, { "A", "C", "E", "G" } });
	}

	@Test(timeout = timeout)
	public void testFullTreeMedium_Explicit() {
		buildAndTest(new String[][] { { "D" }, { "B", "F" }, { "A", "C", "E", "G" }, { null, null, null, null, null, null, null, null } });
	}

	@Test(timeout = timeout)
	public void testFullTreeLarge_Implicit() {
		buildAndTest(new String[][] { { "H" }, { "D", "L" }, { "B", "F", "J", "N" }, { "A", "C", "E", "G", "I", "K", "M", "O" } });
	}

	@Test(timeout = timeout)
	public void testFullTreeLarge_Explicit() {
		buildAndTest(new String[][] { { "H" }, { "D", "L" }, { "B", "F", "J", "N" }, { "A", "C", "E", "G", "I", "K", "M", "O" },
				{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null } });
	}

	@Test(timeout = timeout)
	public void testSingleHoleTreeSmall_Implicit() {
		buildAndTest(new String[][] { { "B" }, { null, "C" } });
	}

	@Test(timeout = timeout)
	public void testSingleHoleTreeSmall_Explicit() {
		buildAndTest(new String[][] { { "B" }, { null, "C" }, { null, null } });
	}

	@Test(timeout = timeout)
	public void testSingleHoleTreeMedium_Implicit() {
		buildAndTest(new String[][] { { "D" }, { "B", null }, { "A", "C" } });
	}

	@Test(timeout = timeout)
	public void testSingleHoleTreeMedium_Explicit() {
		buildAndTest(new String[][] { { "D" }, { "B", null }, { "A", "C" }, { null, null, null, null } });
	}

	@Test(timeout = timeout)
	public void testSingleHoleTreeLarge_Implicit() {
		buildAndTest(new String[][] { { "H" }, { "D", "L" }, { "B", null, "J", "N" }, { "A", "C", "I", "K", "M", "O" } });
	}

	@Test(timeout = timeout)
	public void testSingleHoleTreeLarge_Explicit() {
		buildAndTest(new String[][] { { "H" }, { "D", "L" }, { "B", null, "J", "N" }, { "A", "C", "I", "K", "M", "O" },
				{ null, null, null, null, null, null, null, null, null, null, null, null } });
	}

	// No tests for multi-hole small trees since they're just roots

	@Test(timeout = timeout)
	public void testMultiHoleTreeMedium_Implicit() {
		buildAndTest(new String[][] { { "D" }, { null, "F" }, { null, "G" } });
	}

	@Test(timeout = timeout)
	public void testMultiHoleTreeMedium_Explicit() {
		buildAndTest(new String[][] { { "D" }, { null, "F" }, { null, "G" }, { null, null } });
	}

	@Test(timeout = timeout)
	public void testMultiHoleTreeLarge_Implicit() {
		buildAndTest(new String[][] { { "H" }, { null, "L" }, { "J", null }, { "I", null } });
	}

	@Test(timeout = timeout)
	public void testMultiHoleTreeLarge_Explicit() {
		buildAndTest(new String[][] { { "H" }, { null, "L" }, { "J", null }, { "I", null }, { null, null } });
	}

	/*
	 * Error checks
	 */

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testSmallTreeNotEnoughData_Implicit() {
		buildAndTest(new String[][] { { "B" }, { "A" } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testSmallTreeNotEnoughData_Explicit() {
		buildAndTest(new String[][] { { "B" }, { "A" }, { null, null } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testMediumTreeNotEnoughData_Implicit() {
		buildAndTest(new String[][] { { "D" }, { "B", "F" }, { "A" } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testMediumTreeNotEnoughData_Explicit() {
		buildAndTest(new String[][] { { "D" }, { "B", "F" }, { "A" }, { null, null } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testLargeTreeNotEnoughData_Implicit() {
		buildAndTest(new String[][] { { "H" }, { "D", "L" }, { "F", "J", "N" }, { "E", "G", "I", "K", "M", "O" } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testLargeTreeNotEnoughData_Explicit() {
		buildAndTest(new String[][] { { "H" }, { "D", "L" }, { "F", "J", "N" }, { "E", "G", "I", "K", "M", "O" },
				{ null, null, null, null, null, null, null, null, null, null, null, null } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testSmallTreeTooMuchData_Implicit() {
		buildAndTest(new String[][] { { "B" }, { "A", "C", "D" } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testSmallTreeTooMuchData_Explicit() {
		buildAndTest(new String[][] { { "B" }, { "A", "C", "D" }, { null, null, null, null } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testMediumTreeTooMuchData_Implicit() {
		buildAndTest(new String[][] { { "D" }, { "B", "F" }, { "A", "C", "E", "G", "H" } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testMediumTreeTooMuchData_Explicit() {
		buildAndTest(new String[][] { { "D" }, { "B", "F" }, { "A", "C", "E", "G", "H" },
				{ null, null, null, null, null, null, null, null } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testLargeTreeTooMuchData_Implicit() {
		buildAndTest(new String[][] { { "H" }, { "D", "L" }, { "B", "F", "J", "N" }, { "A", "C", "E", "G", "I", "K", "M", "O", "P" } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testLargeTreeTooMuchData_Explicit() {
		buildAndTest(new String[][] { { "H" }, { "D", "L" }, { "B", "F", "J", "N" }, { "A", "C", "E", "G", "I", "K", "M", "O", "P" },
				{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testSmallTreeNotBST_Implicit() {
		buildAndTest(new String[][] { { "A" }, { "B", "C" } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testSmallTreeNotBST_Explicit() {
		buildAndTest(new String[][] { { "A" }, { "B", "C" }, { null, null, null, null } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testMediumTreeNotBST_Implicit() {
		buildAndTest(new String[][] { { "D" }, { "B", "F" }, { "A", "C", "G", "E" } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testMediumTreeNotBST_Explicit() {
		buildAndTest(new String[][] { { "D" }, { "B", "F" }, { "A", "C", "G", "E" }, { null, null, null, null, null, null, null, null } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testLargeTreeNotBST_Implicit() {
		buildAndTest(new String[][] { { "H" }, { "D", "L" }, { "B", "F", "J", "N" }, { "A", "C", "G", "E", "I", "K", "M", "O" } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testLargeTreeNotBST_Explicit() {
		buildAndTest(new String[][] { { "H" }, { "D", "L" }, { "B", "F", "J", "N" }, { "A", "C", "G", "E", "I", "K", "M", "O" },
				{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testSmallTree_ExplicitNotEnough() {
		buildAndTest(new String[][] { { "B" }, { "A", "C" }, { null, null } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testMediumTree_ExplicitNotEnough() {
		buildAndTest(new String[][] { { "D" }, { "B", "F" }, { "A", "C", "E", "G" }, { null, null, null, null, null, null } });
	}

	@Test(timeout = timeout, expected = IllegalArgumentException.class)
	public void testLargeTree_ExplicitNotEnough() {
		buildAndTest(new String[][] { { "H" }, { "D", "L" }, { "B", "F", "J", "N" }, { "A", "C", "E", "G", "I", "K", "M", "O" },
				{ null, null, null, null, null, null, null, null, null, null, null, null, null, null, null } });
	}

	@Test(timeout = timeout)
	public void testLargeDuplicates() {
		try {
			buildAndTest(new String[][] { { "H" }, { "D", "L" }, { "B", "F", "J", "N" }, { "A", "A", "A", "A", "A", "A", "A", "A" } });
		} catch (IllegalArgumentException iae) {
			Assert.assertTrue("When duplicates are found, it should say all of them.", iae.getMessage().contains("A"));
		}
	}

	@Test(timeout = timeout)
	public void testLargeMultiDuplicates() {
		try {
			buildAndTest(new String[][] { { "H" }, { "D", "L" }, { "B", "F", "J", "N" }, { "A", "A", "B", "B", "C", "C", "D", "D" } });
		} catch (IllegalArgumentException iae) {
			String msg = iae.getMessage();
			Assert.assertTrue("When duplicates are found, it should say all of them.",
					msg.contains("A") && msg.contains("B") && msg.contains("C") && msg.contains("D"));
		}
	}

	private static <E extends Comparable<? super E>> void buildAndTest(E[][] data) {
		BST<E> tree = BSTBuilder.buildBST(data);
		/*
		 *  I'm not going to provide the BSTValidator class since it would give away homework answers,
		 *  but you should be able to figure out what each method does by its name.
		 */
//		Assert.assertFalse("BST should be created without cycles", BSTValidator.hasCycle(tree));
//		Assert.assertTrue("BST should be created with the proper references", BSTValidator.hasProperRefs(tree));
//		Assert.assertTrue("BST should be created with the proper size", BSTValidator.hasProperSize(tree, data));
//		Assert.assertTrue("BST should be created with all the data in the right place", BSTValidator.hasAllData(tree, data));
	}

}
