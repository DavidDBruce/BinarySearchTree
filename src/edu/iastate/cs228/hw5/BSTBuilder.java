package edu.iastate.cs228.hw5;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Utility class used to build BSTs.
 * Check the included README for details on how to use it
 * 
 * @author Brandon
 * 
 */
public class BSTBuilder {

	private static final int LEVEL_0_COUNT = 1;

	/**
	 * Builds a BST object with the given information. See the included README
	 * for detailed use.
	 * 
	 * @param levels
	 * 		The data to put in the tree, level by level
	 * @return The BST represented by the given List, or an empty tree if levels
	 *         is null or empty
	 * @throws IllegalArgumentException
	 *             If not enough information is provided
	 * @throws IllegalArgumentException
	 *             If you attempt to build a BST that violates BST ordering
	 * @throws IllegalArgumentException
	 *             If you have duplicate values in the given array
	 */
	public static <E extends Comparable<? super E>> BST<E> buildBST(E[][] levels) {
		// Empty tree
		if (levels == null || levels.length == 0) {
			return new BST<E>(null, 0);
		}

		// Check for valid input
		validateLevels(levels);
		List<E> data = flatten(levels);
		validateNoDuplicates(data);
		
		// If it's good, then go ahead
		return buildTree(data);
	}

	/*
	 * Checks to make sure, each level has the proper number of children for the
	 * number of parents
	 */
	private static void validateLevels(Object[][] levels) {

		// Check the root manually
		if (levels[0].length != LEVEL_0_COUNT) {
			invalidChildCount(0, levels[0]);
		}
		validateLevelsRec(1, 1, levels);
	}

	/*
	 * Recursively checks that the number of children is proper for the number
	 * of parents
	 */
	private static void validateLevelsRec(int curLevel, int parentCount, Object[][] levels) {
		// Success!
		if (curLevel >= levels.length) {
			return;
		}

		// Invalid case
		if (levels[curLevel] == null) {
			invalidChildCount(curLevel, levels[curLevel]);
		}

		// Check that the number of children is correct
		if (getProperChildCount(parentCount) != levels[curLevel].length) {
			invalidChildCount(curLevel, levels[curLevel]);
		}

		validateLevelsRec(curLevel + 1, countNonNulls(levels[curLevel]), levels);
	}
	
	/*
	 * Checks that there are no duplicates in data
	 */
	private static void validateNoDuplicates(List<?> data) {
		if (data == null) {
			return;
		}
		Set<Object> found = new HashSet<>();
		Set<Object> dups = new HashSet<>();
		for (Object da : data) {
			// Nulls will be duplicated
			if (da == null) {
				continue;
			}
			if (!found.contains(da)) {
				found.add(da);
				continue;
			} else {
				dups.add(da);
			}
		}
		
		if (!dups.isEmpty()) {
			invalidDuplicates(dups);
		}
	}

	// 2 children for every parent
	private static int getProperChildCount(int parentCount) {
		return parentCount * 2;
	}

	// Counts how many elements in children are not null
	private static int countNonNulls(Object[] children) {
		if (children == null) {
			return 0;
		}
		int count = 0;
		for (Object o : children) {
			if (o != null) {
				++count;
			}
		}
		return count;
	}

	/*
	 * Converts the 2D array into a 1D list
	 */
	private static <E extends Comparable<? super E>> List<E> flatten(E[][] arr) {
		List<E> ret = new LinkedList<>();
		if (arr == null) {
			return ret;
		}

		for (E[] innerArr : arr) {
			if (innerArr == null) {
				continue;
			}
			ret.addAll(Arrays.asList(innerArr));
		}

		return ret;
	}

	/*
	 * Converts the level order list to a BST. Since a Level order traversal is
	 * created using a Queue (and not recursion), it makes sense to use a Queue
	 * to build the tree as well.
	 */
	private static <E extends Comparable<? super E>> BST<E> buildTree(List<E> eles) {
		// Create our starting point
		Node<E> root = new Node<>(eles.get(0));
		Queue<Node<E>> q = new LinkedList<>();
		q.offer(root);

		// Where we're getting the data
		int dataIndex = 1;
		while (!q.isEmpty()) {
			// The node we're building now
			Node<E> curNode = q.poll();

			// Get the children, using null if we've run out of data, or if the data itself is null
			Node<E> left = (dataIndex >= eles.size() || eles.get(dataIndex) == null) ? null : new Node<>(eles.get(dataIndex));
			Node<E> right = (dataIndex >= eles.size() || eles.get(dataIndex + 1) == null) ? null : new Node<>(eles.get(dataIndex + 1));

			// Check that we're not making an invalid BST
			validateLeft(curNode, left);
			validateRight(curNode, right);

			// We only put non-null Nodes into the queue since nulls don't need their children set
			if (left != null) {
				curNode.setLeft(left);
				left.setParent(curNode);
				q.offer(left);
			}

			if (right != null) {
				curNode.setRight(right);
				right.setParent(curNode);
				q.offer(right);
			}

			// Used up 2 data elements
			dataIndex += 2;
		}

		return new BST<>(root, countNonNulls(eles.toArray()));
	}

	// Checks that left.data < parent.data
	private static <E extends Comparable<? super E>> void validateLeft(Node<E> parent, Node<E> left) {
		if (left == null) {
			return;
		}

		if (left.getData().compareTo(parent.getData()) >= 0) {
			invalidLeft(parent.getData(), left.getData());
		}
	}

	// Checks that right.data > parent.data
	private static <E extends Comparable<? super E>> void validateRight(Node<E> parent, Node<E> right) {
		if (right == null) {
			return;
		}

		if (right.getData().compareTo(parent.getData()) <= 0) {
			invalidRight(parent.getData(), right.getData());
		}
	}

	/*
	 * --------------------------------------------------------------------------
	 * Exceptions
	 * ----------------------------------------------------------------
	 */
	private static void invalidChildCount(int level, Object[] children) {
		throw new IllegalArgumentException(String.format("Invalid number of children for level: %d. Children were: %s", level,
				Arrays.toString(children)));
	}
	
	private static void invalidDuplicates(Set<Object> dups) {
		throw new IllegalArgumentException(String.format("Found duplicated data: %s", dups));
	}

	private static void invalidLeft(Object parent, Object left) {
		throw new IllegalArgumentException(String.format("Tried to build an invalid BST! Parent: %s, Left: %s"));
	}

	private static void invalidRight(Object parent, Object right) {
		throw new IllegalArgumentException(String.format("Tried to build an invalid BST! Parent: %s, Right: %s"));
	}
}
