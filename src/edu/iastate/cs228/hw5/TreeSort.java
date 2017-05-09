package edu.iastate.cs228.hw5;

import java.util.ArrayList;

/**
 *  
 * @author David Bruce
 *
 */


public class TreeSort
{
	/**
	 * Sorts an input array by first creating a binary search tree to store its elements, and then perform an 
	 * inorder traversal of the tree to visit the elements in order and put them back in the array. 
	 * @param eleArray
	 */
	public static <E extends Comparable <? super E>> void sort(E[] eleArray)
	{
		ArrayList<E> tempSortedArray = new ArrayList<E>();
		BST<E> treeToSort = new BST<E>(eleArray);
		treeToSort.traverseInorder();
		treeToSort.getInorderSequence(tempSortedArray);
		
		for(int i = 0; i < tempSortedArray.size(); i++)
		{
			eleArray[i] = tempSortedArray.get(i);
		}
		
	}
}

