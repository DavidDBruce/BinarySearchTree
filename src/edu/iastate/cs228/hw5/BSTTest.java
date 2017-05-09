
package edu.iastate.cs228.hw5;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Brennen Ferguson
 *
 */
public class BSTTest
{
	BST<Integer> bst;
	BST<Integer> tree1;
	BST<Integer> tree2;
	BST<Integer> tree3;
	BST<Integer> tree4;
	BST<Integer> bst2;
	
	@Before
	public void setup()
	{
		//Tree from the traversal powerpoint.
		Integer[][] intArr = {{15},
				{6,18},
				{3,7,17,20},
				{2,4,null,13,null,null,null,null},
				{null,null,null,null,9,null},
				{null,null}};
		bst = BSTBuilder.buildBST(intArr);
		
		//Trees from the project powerpoint. -Equals() setEquals() section.
		Integer[][] arr1 = {{7},
				{4,11},
				{3,6,9,null},
				{2,null,null,null,null,null}};
		tree1 = BSTBuilder.buildBST(arr1);
		
		Integer[][] arr2 = {{7},
				{4,11},
				{3,6,9,null},
				{2,null,null,null,null,null}};
		tree2 = BSTBuilder.buildBST(arr2);
		
		Integer[][] arr3 = {{6},
				{3,9},
				{2,4,7,11}};
		tree3 = BSTBuilder.buildBST(arr3);
		
		Integer[][] arr4 = {{8},
				{7,10},
				{null,null,null,20}};
		tree4 = BSTBuilder.buildBST(arr4);
		
		//Tree from the poject powerpoint. -Rotation section
		Integer[][] arr5 = {{7},
				{4,11},
				{3,6,9,18},
				{2,null,null,null,null,null,14,19},
				{null,null,12,17,null,22},
				{null,null,null,null,20,null}};
		bst2 = BSTBuilder.buildBST(arr5);
	}
	
	@Test
	public void printTrees()
	{
		System.out.println("bst tree: \n" + bst);
		System.out.println("tree1: \n" + tree1);
		System.out.println("tree2: \n" + tree2);
		System.out.println("tree3: \n" + tree3);
		System.out.println("tree4: \n" + tree4);
		System.out.println("bst2 tree: \n" + bst2);
	}
	
	//Tests for the traversals as they will be used to test everything else.
	//If these fail, most others will. This tree and answers are from the traversal powerpoint.
	@Test
	public void testPreorder()
	{
		ArrayList<Integer> arr = new ArrayList<Integer>();
		bst.getPreorderSequence(arr);
		assertEquals("[15, 6, 3, 2, 4, 7, 13, 9, 18, 17, 20]",arr.toString());
	}
	
	@Test
	public void testInorder()
	{
		ArrayList<Integer> arr = new ArrayList<Integer>();
		bst.getInorderSequence(arr);
		assertEquals("[2, 3, 4, 6, 7, 9, 13, 15, 17, 18, 20]",arr.toString());
	}
	
	@Test
	public void testPostorder()
	{
		ArrayList<Integer> arr = new ArrayList<Integer>();
		bst.getPostorderSequence(arr);
		assertEquals("[2, 4, 3, 9, 13, 7, 6, 17, 20, 18, 15]",arr.toString());
	}
	
	@Test
	public void testPreorder2()
	{
		String s = bst.traversePreorder();
		assertEquals("15 6 3 2 4 7 13 9 18 17 20",s);
	}
	
	@Test
	public void testInorder2()
	{
		String s = bst.traverseInorder();
		assertEquals("2 3 4 6 7 9 13 15 17 18 20",s);
	}
	
	@Test
	public void testPostorder2()
	{
		String s = bst.traversePostorder();
		assertEquals("2 4 3 9 13 7 6 17 20 18 15",s);
	}
	
	//Exceptions
	@Test (expected = TreeStructureException.class)
	public void testTreeStructureExceptionConstructor() throws TreeStructureException
	{
		Integer[][] arr = {{15},
				{6,17},
				{2,8,16,19},
				{null,null,null,20,null,null,null,null}};
		BST<Integer> bst = BSTBuilder.buildBST(arr);
		Node<Integer> n = bst.getRoot();
		BST<Integer> newBST = new BST<Integer>(n);
	}
	
	@Test (expected = TreeStructureException.class)
	public void testTreeStructureExceptionConstructor2() throws TreeStructureException
	{
		Integer[][] arr = {{15},
				{6,17},
				{2,9,null,null},
				{null,10,null,null}};
		BST<Integer> bst = BSTBuilder.buildBST(arr);
		Node<Integer> n = bst.getRoot();
		BST<Integer> newBST = new BST<Integer>(n);
	}
	
	@Test
	public void testTreeStructureExceptionConstructorMessage() throws TreeStructureException
	{
		Integer[][] arr = {{15},
				{6,17},
				{2,8,16,19},
				{null,null,null,20,null,null,null,null}};
		BST<Integer> bst = BSTBuilder.buildBST(arr);
		Node<Integer> n = bst.getRoot();
		try { BST<Integer> newBST = new BST<Integer>(n); }
		catch (TreeStructureException e)
		{ assertEquals("Copying a non-BST tree", e.getLocalizedMessage());}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testRangeQueryException()
	{
		Integer[] arr = new Integer[10];
		Integer min = 5;
		Integer max = 4;
		bst.rangeQuery(min, max, arr);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testOrderQueryExceptioniMinGreater()
	{
		Integer[] arr = new Integer[10];
		bst.orderQuery(5, 4, arr);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testOrderQueryExceptioniMinLessThanZero()
	{
		Integer[] arr = new Integer[10];
		bst.orderQuery(-1, 4, arr);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testOrderQueryExceptioniMinGreaterThanSize()
	{
		Integer[] arr = new Integer[10];
		bst.orderQuery(1, bst.size() + 1, arr);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testOrderQueryExceptioniMinEqualToSize()
	{
		Integer[] arr = new Integer[10];
		bst.orderQuery(5, bst.size(), arr);
	}
	
	//GeneralTests
	@Test
	public void constructorRootSizeGiven()
	{
		Node<Integer> v2 = new Node<Integer>(2);
		Node<Integer> v7 = new Node<Integer>(7);
		Node<Integer> root = new Node<Integer>(5,v2,v7);
		v2.setParent(root);
		v7.setParent(root);
		BST<Integer> bst = new BST<Integer>(root,3);
		bst.traverseInorder();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		bst.getInorderSequence(arr);
		assertEquals("[2, 5, 7]", arr.toString());
	}
	
	//You can see my post on blackboard for this.
	@Test
	public void constructorGivenArray()
	{
		Integer[] eleArray = {3,6,2,4};
		BST<Integer> bst = new BST<Integer>(eleArray);
		ArrayList<Integer> arr = new ArrayList<Integer>();
		bst.getInorderSequence(arr);
		assertEquals("[2, 3, 4, 6]",arr.toString());
	}
	
	@Test
	public void constructorGivenArraySize()
	{
		Integer[] eleArray = {3,6,2,4};
		BST<Integer> bst = new BST<Integer>(eleArray);
		assertEquals(4,bst.size());
	}
	
	@Test
	public void constructorGivenRootCopyDifferentNodeRootOnly() throws TreeStructureException
	{
		Node<Integer> n = bst.getRoot();
		BST<Integer> newBST = new BST<Integer>(n);
		boolean same = newBST.getRoot() == n;
		assertFalse(same);
	}
	
	@Test
	public void constructorGivenRootCopySameData() throws TreeStructureException
	{
		Node<Integer> n = bst.getRoot();
		BST<Integer> newBST = new BST<Integer>(n);
		ArrayList<Integer> arr = new ArrayList<Integer>();
		newBST.getInorderSequence(arr);
		assertEquals("[2, 3, 4, 6, 7, 9, 13, 15, 17, 18, 20]",arr.toString());
	}
	
	@Test
	public void constructorGivenRootCopySize() throws TreeStructureException
	{
		Node<Integer> n = bst.getRoot();
		BST<Integer> newBST = new BST<Integer>(n);
		assertEquals(11,newBST.size());
	}
	
	//Tests from the project powerpoint
	@Test
	public void testEquals()
	{
		boolean same = tree1.equals(tree2);
		assertTrue(same);
	}
	
	@Test
	public void testEquals2()
	{
		boolean same = tree1.equals(tree3);
		assertFalse(same);
	}
	
	@Test
	public void testEquals3()
	{
		boolean same = tree1.equals(tree4);
		assertFalse(same);
	}
	
	@Test
	public void testSetEquals()
	{
		boolean same = tree1.setEquals(tree2);
		assertTrue(same);
	}
	
	@Test
	public void testSetEquals2()
	{
		boolean same = tree1.setEquals(tree3);
		assertTrue(same);
	}
	
	@Test
	public void testSetEquals3()
	{
		boolean same = tree1.setEquals(tree4);
		assertFalse(same);
	}
	
	@Test
	public void testRangeQuery()
	{
		Integer[] eleArray = new Integer[4];
		Integer min = 5;
		Integer max = 14;
		int numOfEles = bst.rangeQuery(min, max, eleArray);
		assertEquals("[6, 7, 9, 13]", Arrays.toString(eleArray));
	}
	
	@Test
	public void testRangeQuery2()
	{
		Integer[] eleArray = new Integer[4];
		Integer min = 6;
		Integer max = 13;
		int numOfEles = bst.rangeQuery(min, max, eleArray);
		assertEquals("[6, 7, 9, 13]", Arrays.toString(eleArray));
	}
	
	@Test
	public void testRangeQuery3()
	{
		Integer[] eleArray = new Integer[4];
		Integer min = 6;
		Integer max = 13;
		int numOfEles = bst.rangeQuery(min, max, eleArray);
		assertEquals(4,numOfEles);
	}
	
	@Test
	public void testRangeQuery4()
	{
		Integer[] eleArray = new Integer[4];
		Integer min = 12;
		Integer max = 19;
		int numOfEles = bst.rangeQuery(min, max, eleArray);
		assertEquals(4,numOfEles);
	}
	
	@Test
	public void testRangeQuery5()
	{
		Integer[] eleArray = new Integer[7];
		Integer min = 10;
		Integer max = 20;
		int numOfEles = bst2.rangeQuery(min, max, eleArray);
		assertEquals(7,numOfEles);
	}
	
	@Test
	public void testRangeQuery6()
	{
		Integer[] eleArray = new Integer[7];
		Integer min = 10;
		Integer max = 20;
		int numOfEles = bst2.rangeQuery(min, max, eleArray);
		assertEquals("[11, 12, 14, 17, 18, 19, 20]",Arrays.toString(eleArray));
	}
	
	@Test
	public void testOrderQuery()
	{
		Integer[] eleArray = new Integer[4];
		bst.orderQuery(3, 6, eleArray);
		assertEquals("[6, 7, 9, 13]", Arrays.toString(eleArray));
	}
	
	@Test
	public void testOrderQuery2()
	{
		Integer[] eleArray = new Integer[14];
		bst2.orderQuery(0, 13, eleArray);
		assertEquals("[2, 3, 4, 6, 7, 9, 11, 12, 14, 17, 18, 19, 20, 22]", Arrays.toString(eleArray));
	}
	
	@Test
	public void testOrderQuery3()
	{
		Integer[] eleArray = new Integer[1];
		bst2.orderQuery(1, 1, eleArray);
		assertEquals("[3]", Arrays.toString(eleArray));
	}
	
	@Test
	public void testOrderQuery4()
	{
		Integer[] eleArray = new Integer[2];
		bst2.orderQuery(1, 2, eleArray);
		assertEquals("[3, 4]", Arrays.toString(eleArray));
	}
	
	@Test
	public void testPredecessor()
	{
		Node<Integer> n = bst.getRoot();
		Node<Integer> pred = bst.predecessor(n);
		Node<Integer> realPred = n.getLeft().getRight().getRight();
		boolean same = pred == realPred;
		assertTrue(same);
	}
	
	@Test
	public void testPredecessor2()
	{
		Node<Integer> n = bst.getRoot().getLeft().getRight();
		Node<Integer> pred = bst.predecessor(n);
		Node<Integer> realPred = n.getParent();
		boolean same = pred == realPred;
		assertTrue(same);
	}
	
	@Test
	public void testPredecessor3()
	{
		Node<Integer> n = bst.getRoot().getLeft();
		Node<Integer> pred = bst.predecessor(n);
		Node<Integer> realPred = n.getLeft().getRight();
		boolean same = pred == realPred;
		assertTrue(same);
	}
	
	@Test
	public void testRotateLeft()
	{
		Node<Integer> n = bst2.getRoot().getRight();
		bst2.leftRotate(n);
		ArrayList<Integer> arr = new ArrayList<Integer>();
		bst2.getPreorderSequence(arr);
		assertEquals("[7, 4, 3, 2, 6, 18, 11, 9, 14, 12, 17, 19, 22, 20]",arr.toString());
	}
	
	@Test
	public void testRotateLeft2()
	{
		Node<Integer> n = bst2.getRoot().getRight();
		bst2.leftRotate(n);
		ArrayList<Integer> arr = new ArrayList<Integer>();
		bst2.getInorderSequence(arr);
		assertEquals("[2, 3, 4, 6, 7, 9, 11, 12, 14, 17, 18, 19, 20, 22]",arr.toString());
	}
	
	@Test
	public void testRotateLeft3()
	{
		Node<Integer> n = bst2.getRoot().getLeft();
		bst2.leftRotate(n);
		ArrayList<Integer> arr = new ArrayList<Integer>();
		bst2.getPreorderSequence(arr);
		System.out.println("This One");
		System.out.println(bst2.toString());
		assertEquals("[7, 6, 4, 3, 2, 11, 9, 18, 14, 12, 17, 19, 22, 20]",arr.toString());
	}
	
	@Test
	public void testRotateRight()
	{
		Node<Integer> n = bst2.getRoot().getLeft();
		bst2.rightRotate(n);
		ArrayList<Integer> arr = new ArrayList<Integer>();
		bst2.getPreorderSequence(arr);
		assertEquals("[7, 3, 2, 4, 6, 11, 9, 18, 14, 12, 17, 19, 22, 20]",arr.toString());
	}
	
	@Test
	public void testRotateRight2()
	{
		Node<Integer> n = bst2.getRoot().getLeft();
		bst2.rightRotate(n);
		ArrayList<Integer> arr = new ArrayList<Integer>();
		bst2.getInorderSequence(arr);
		assertEquals("[2, 3, 4, 6, 7, 9, 11, 12, 14, 17, 18, 19, 20, 22]",arr.toString());
	}
	
	@Test
	public void testRotateRight3()
	{
		Node<Integer> n = bst2.getRoot().getRight().getRight();
		bst2.rightRotate(n);
		ArrayList<Integer> arr = new ArrayList<Integer>();
		bst2.getPreorderSequence(arr);
		assertEquals("[7, 4, 3, 2, 6, 11, 9, 14, 12, 18, 17, 19, 22, 20]",arr.toString());
	}
	
	@Test
	public void testHeight()
	{
		int height = bst2.height();
		assertEquals(5,height);
	}
	
	@Test
	public void testHeight2()
	{
		int height = tree4.height();
		assertEquals(2,height);
	}
	
	@Test
	public void testHeightOnlyRoot() throws TreeStructureException
	{
		BST<Integer> bst = new BST<Integer>(new Node<Integer>(5));
		int height = bst.height();
		assertEquals(0,height);
	}
	
	@Test
	public void testMin()
	{
		int min = bst2.min();
		assertEquals(2,min);
	}
	
	@Test
	public void testMax()
	{
		int max = bst2.max();
		assertEquals(22,max);
	}
}