package edu.iastate.cs228.hw5;

import static org.junit.Assert.*;

import org.junit.Test;

public class MyBSTTests 
{

	@Test
	public void testSizeAfterAddingRoot() 
	{
		BST<Integer> myTree = new BST<Integer>();
		myTree.add(4);
		//BSTBuilder treeTester = new BSTBuilder();
		assertEquals(myTree.size(), 1);
	}
	
	@Test
	public void testRootAfterAddingRoot() 
	{
		BST<Integer> myTree = new BST<Integer>();
		myTree.add(4);
		//BSTBuilder treeTester = new BSTBuilder();
		assertEquals(myTree.getRoot().getData(), (Integer) 4);
	}
	
	@Test
	public void testLeftChildAfterAddingLessThanRoot() 
	{
		BST<Integer> myTree = new BST<Integer>();
		myTree.add(4);
		myTree.add(2);
		//BSTBuilder treeTester = new BSTBuilder();
		assertEquals(myTree.getRoot().getLeft().getData(), (Integer) 2);
	}
	
	@Test
	public void testRightChildAfterAddingGreaterThanRoot() 
	{
		BST<Integer> myTree = new BST<Integer>();
		myTree.add(4);
		myTree.add(2);
		myTree.add(6);
		//BSTBuilder treeTester = new BSTBuilder();
		assertEquals(myTree.getRoot().getRight().getData(), (Integer) 6);
	}

	@Test
	public void testRemoveLeftNodeAfterAddingLeftThenRight() 
	{
		BST<Integer> myTree = new BST<Integer>();
		myTree.add(4);
		myTree.add(2);
		myTree.add(6);
		myTree.remove(2);
		//BSTBuilder treeTester = new BSTBuilder();
		assertEquals(myTree.findEntry(2), null);
	}
	
	@Test
	public void testFindEntryLeft() 
	{
		BST<Integer> myTree = new BST<Integer>();
		myTree.add(4);
		myTree.add(2);
		myTree.add(6);
		//BSTBuilder treeTester = new BSTBuilder();
		assertEquals(myTree.findEntry(2).getData(), (Integer) 2);
	}
	
	@Test
	public void testThreeLengthPreorderFirstPass() 
	{
		BST<Integer> myTree = new BST<Integer>();
		myTree.add(4);
		myTree.add(2);
		myTree.add(6);
		//BSTBuilder treeTester = new BSTBuilder();
		//myTree.traversePreorder();
		assertEquals("4 2 6", myTree.traversePreorder());
	}
	
	@Test
	public void testThreeLengthInorder() 
	{
		BST<Integer> myTree = new BST<Integer>();
		myTree.add(4);
		myTree.add(2);
		myTree.add(6);
		//BSTBuilder treeTester = new BSTBuilder();
		//myTree.traverseInorder();
		assertEquals("2 4 6", myTree.traverseInorder());
	}
	
	@Test
	public void testThreeLengthPostorder() 
	{
		BST<Integer> myTree = new BST<Integer>();
		myTree.add(4);
		myTree.add(2);
		myTree.add(6);
		//BSTBuilder treeTester = new BSTBuilder();
		myTree.traversePostorder();
		assertEquals("2 6 4", myTree.traversePostorder());
	}
	
	@Test
	public void testHeight1() 
	{
		BST<Integer> myTree = new BST<Integer>();
		myTree.add(4);
		myTree.add(2);
		myTree.add(6);
		//BSTBuilder treeTester = new BSTBuilder();
		myTree.traversePostorder();
		myTree.height();
		assertEquals(1, myTree.height());
	}
	
	@Test
	public void testHeight2() 
	{
		BST<Integer> myTree = new BST<Integer>();
		myTree.add(4);
		myTree.add(2);
		myTree.add(6);
		myTree.add(5);
		myTree.add(3);
		//BSTBuilder treeTester = new BSTBuilder();
		myTree.traversePostorder();
		myTree.height();
		System.out.println(myTree.toString());
		assertEquals(2, myTree.height());
	}
}
