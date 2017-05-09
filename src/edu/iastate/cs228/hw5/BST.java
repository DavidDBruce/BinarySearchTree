package edu.iastate.cs228.hw5;

/**
 *  
 * @author David Bruce
 *
 */


import java.util.AbstractSet;
import java.util.Iterator;
import java.util.ArrayList; 
import java.lang.IllegalArgumentException; 



/**
 * Binary search tree implementation
 */
public class BST<E extends Comparable<? super E>> extends AbstractSet<E>
{
	private Node<E> root;
	private Node<E> current;
	private int size;      
  
	private ArrayList<E>  preorderArr;	// stores the key values from a preorder traversal
	private ArrayList<E>  inorderArr;	// stores the key values from an inorder traversal
	private ArrayList<E>  postorderArr;	// stores the key values from a postorder traversal
	/*
	 * These tags will be set to false respectively at the ends of calls to traversePreorder(), 
	 * traverseInorder(), and traversePostorder(). They must be set back to true whenever
	 * the binary search tree is modified by add(), remove(), leftRotate(), and rightRotate(). 
	 */
	private boolean redoPreorder = true; 	
	private boolean redoInorder = true; 
	private boolean redoPostorder = true; 
  
	

	// ------------
	// Constructors
	// ------------
  
	/**
	 * Default constructor builds an empty tree. 
	 */
	public BST()
	{
		size = 0;
		this.preorderArr = new ArrayList<E>();
		this.inorderArr = new ArrayList<E>();
		this.postorderArr = new ArrayList<E>();
	}
	
	
	/**
	 * Constructor from an existing tree (manually set up for testing) 
	 * @param root
	 * @param size
	 */
	public BST(Node<E> root, int size) 
	{
		this.root = root;
		this.size = size;
		current = root;
		this.preorderArr = new ArrayList<E>();
		this.inorderArr = new ArrayList<E>();
		this.postorderArr = new ArrayList<E>();
	 }
  

	/**
	 * Constructor over an element array.  Elements must be inserted sequentially in order of 
	 * increasing index from the array.  
	 * 
	 * @param eleArray
	 */
	public BST(E[] eleArray)
	{
		size = 0;
		for(int i = 0; i < eleArray.length; i++)
		{
			add(eleArray[i]);
		}
		current = root;
		this.preorderArr = new ArrayList<E>();
		this.inorderArr = new ArrayList<E>();
		this.postorderArr = new ArrayList<E>();
	}
 
  
	/**
	 * Copy constructor.  It takes a binary tree with a given root as input, and calls isBST() to check 
	 * if it is indeed a binary search tree. If not, throws a TreeStructureException with the message 
	 * "Copying a non-BST tree".  If so, makes a deep copy of the input tree such that the resulting BST
	 * assumes the same structure and has the same key stored at every corresponding node.  
	 * 
	 * @param rt  root of an existing binary tree 
	 */
	public BST(Node<E> root) throws TreeStructureException
	{
		
		if(!isBST(root))
		{
			throw new TreeStructureException("Copying a non-BST tree");
		}
		
		BST<E> copyTree = new BST<E>(root, 0);
		copyTree.traversePreorder();
		ArrayList<E> tempArray = copyTree.preorderArr;
		
		for(int i = 0; i < tempArray.size(); i++)
		{
			add(tempArray.get(i));
		}
		System.out.println("This One");
		System.out.println(this.toString());
		
	}
  
	

	// -------
	// Getters
	// -------
  
	/**
	 * This function is here for grading purpose not as a good programming practice.
	 * @return root of the BST
	 */
	public Node<E> getRoot()
	{
		return root; 
	}

	
	public int size()
	{
		return size; 
	}
	
	
	/**
	 * 
	 * @return tree height 
	 */
	public int height()
	{
		if(root == null)
		{
			return 0;
		}
		return traverseHeight(0,root); 
	}

	/**
	 * This method must be implemented by operating over the tree without using either of 
	 * the array lists preorderArr, inorderArr, and postorderArr. 
	 * 
	 * @return	the minimum element in the tree or null in the case of an empty tree 
	 */
	public E min()
	{
		while(current.getLeft() != null)
		{
			current = current.getLeft();
		}
		
		return current.getData(); 
	}
	
	
	/**
	 * This method must be implemented by operating over the tree without using either of 
	 * the array lists preorderArr, inorderArr, and postorderArr. 
	 * 
	 * @return	the maximum element in the tree or null in the case of an empty tree 
	 */
	public E max()
	{
		while(current.getRight() != null)
		{
			current = current.getRight();
		}
		
		return current.getData();
	}
	
	
	/**
	 * Calls traversePreorder() and copy the content of preorderArr to arr. 
	 * 
	 * @param arr array list to store the sequence 
	 */
	public void getPreorderSequence(ArrayList<E> arr)
	{
		traversePreorder();
		for(int i = 0; i < preorderArr.size(); i++)
		{
			arr.add(preorderArr.get(i));
		}
	}
	

	/**
	 * Calls traverseInorder() and copy the content of inorderArr to arr. 
	 * 
	 * @param arr array list to store the sequence 
	 */
	public void getInorderSequence(ArrayList<E> arr)
	{
		traverseInorder();
		for(int i = 0; i < inorderArr.size(); i++)
		{
			arr.add(inorderArr.get(i));
		}
	}
	
	
	/**
	 * Calls traversePostorder() and copy the content of postorderArr to arr. 
	 * 
	 * @param arr array list to store the sequence 
	 */
	public void getPostorderSequence(ArrayList<E> arr)
	{
		traversePostorder();
		for(int i = 0; i < postorderArr.size(); i++)
		{
			arr.add(postorderArr.get(i));
		}
	}	
	
		
	
	// -----------
	// Comparators 
	// -----------
	
	/**
	 * Returns true if the tree and a second tree o have exactly the same structure, and equal 
	 * elements stored at every pair of corresponding nodes.  
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) 
	{
		BST<E> inputConversion = new BST<E>();
		inputConversion = (BST<E>) o;
		if(redoPreorder)
		{
			traversePreorder();
		}
		if(redoInorder)
		{
			traverseInorder();
		}
		
		if(inputConversion.redoPreorder)
		{
			inputConversion.traversePreorder();
		}
		if(inputConversion.redoInorder)
		{
			inputConversion.traverseInorder();
		}
		if(preorderArr.equals(inputConversion.preorderArr) && inorderArr.equals(inputConversion.inorderArr))
		{
			return true;
		}
		
		return false; 
	}
	
	
	
	/** 
	 * Returns true if two binary search trees store the same set of elements, and false otherwise.   
	 * The tree rooted at tree is also a binary search tree.   
	 *    
	 * @param rt
	 * @return
	 */
	public boolean setEquals(BST<E> tree)
	{
		if(this.traverseInorder().equals(tree.traverseInorder()))
		{
			return true;
		}
		
		return false; 
	}

	
	
	// ----------
	// Traversals
	// ----------
	
	/**
	 * Performs a preorder traversal of the tree, stores the result in the array list preOrderArr, and also 
	 * write the key values to a string in which they are separated by blanks (exactly one blank  
	 * between two adjacent key values). 
	 *  
	 * No need to perform the traversal if redoPreorder == false. 
	 */
	public String traversePreorder()
	{
		
		String preOrder = "";
		current = root;
		if(!redoPreorder)
		{
			for(int i = 0; i < preorderArr.size(); i++)
			{
				preOrder += preorderArr.get(i);
				if(i != preorderArr.size() - 1)
				{
					preOrder += " ";
				}
			}
			return preOrder;
		}
		preorderArr = new ArrayList<E>();
		traversePre(root);
		
		for(int i = 0; i < preorderArr.size(); i++)
		{
			preOrder += preorderArr.get(i);
			
			if(i != preorderArr.size() -1)
			{
				preOrder += " ";
			}
		}
		redoPreorder = false;
		return preOrder; 
	}
  
  
	/**
	 * Performs an inorder traversal of the tree, and stores the result in the array list inOrderArr. 
	 * No need to perform the traversal if redoInorder == false. 
	 */
	public String traverseInorder()
	{
		
		String inOrder = "";
		current = root;
		if(!redoInorder)
		{
			for(int i = 0; i < inorderArr.size(); i++)
			{
				inOrder += inorderArr.get(i);
				if(i != inorderArr.size() - 1)
				{
					inOrder += " ";
				}
			}
			return inOrder;
		}
		inorderArr = new ArrayList<E>();
		traverseIn(root);
		
		for(int i = 0; i < inorderArr.size(); i++)
		{
			inOrder += inorderArr.get(i);
			
			if(i != inorderArr.size() -1)
			{
				inOrder += " ";
			}
		}
		redoInorder = false;
		return inOrder; 
	}  
  
  
	/**
	 * Performs a postorder traversal of the tree, and stores the result in the array list preOrderArr. 
	 * No need to perform the traversal if redoPostorder == false. 
	 */   
	public String traversePostorder()
	{
		String postOrder = "";
		current = root;
		if(!redoPostorder)
		{
			for(int i = 0; i < postorderArr.size(); i++)
			{
				postOrder += postorderArr.get(i);
				if(i != postorderArr.size() - 1)
				{
					postOrder += " ";
				}
			}
			return postOrder;
		}
		postorderArr = new ArrayList<E>();
		traversePost(root);
		
		for(int i = 0; i < postorderArr.size(); i++)
		{
			postOrder += postorderArr.get(i);
			
			if(i != postorderArr.size() -1)
			{
				postOrder += " ";
			}
		}
		redoPostorder = false;
		return postOrder;
	}

	
  
	// -------------
	// Query Methods
	// -------------
	
	/**
	 * Returns the number of keys with values >= minValue and <= maxValue, and stores these key values 
	 * in the array eleArray[] in the increasing order.  Note that minValue and maxValue may not be any
	 * of the key values stored in the tree. 
	 * 
	 * Exception is thrown if minValue > maxValue. 
	 *  
	 * @param minValue	lower bound for query values 
	 * @param maxValue  upper bound for query values 
	 * @param eleArray	stores elements >= minValue and <= maxValue 
	 * @return			number of elements in the interval [minValue, maxValue]
	 */
	public int rangeQuery(E minValue, E maxValue, E[] eleArray) throws IllegalArgumentException 
	{
		traverseInorder();
		if(minValue.compareTo(maxValue) > 0)
		{
			throw new IllegalArgumentException();
		}
		int j = 0;
		for(int i = 0; i < inorderArr.size(); i++)
		{
			if(inorderArr.get(i).compareTo(minValue) >= 0 && maxValue.compareTo(inorderArr.get(i)) >= 0)
			{
				eleArray[j] = inorderArr.get(i);
				j++;
			}
		}
		return eleArray.length; 
	}
	
	
	/**
	 * Get the keys that are between the imin-th and the imax-th positions from an inorder traversal. 
	 * The first visited node is at position 0.  Store these keys in eleArray[] in their original order. 
	 * 
	 * Exception is thrown if 1) imax < imin, 2) imin < 0, or 3) imax >= size. 
	 * 
	 * @param imin			minimum index of the keys to be searched for according to inorder
	 * @param imax			maximum index of the keys to be searched for according to inorder
	 * @param eleArray		stores the found keys 
	 * @return
	 */
	public void orderQuery(int imin, int imax, E[] eleArray) throws IllegalArgumentException 
	{
		traverseInorder();
		if(imax < imin)
		{
			throw new IllegalArgumentException();
		}
		if(imin < 0)
		{
			throw new IllegalArgumentException();
		}
		if(imax >= inorderArr.size())
		{
			throw new IllegalArgumentException();
		}
		
			int j = 0;
			for(int i = imin; i <= imax; i++)
			{
				eleArray[j] = inorderArr.get(i);
				j++;
			}
	}

	
    
	// --------------------------
	// Operations related to Keys
	// --------------------------
  
	 @SuppressWarnings("unchecked")
	@Override
	  public boolean contains(Object obj)
	  {
	    // This cast may cause comparator to throw ClassCastException at runtime,
	    // which is the expected behavior
	    E key = (E) obj;
	    return findEntry(key) != null;
	  }
  

	@Override
	public boolean add(E key)
	{
		redoPreorder = true;
		redoInorder = true; 
		redoPostorder = true;
		
		 if (root == null)
		    {
		      root = new Node<E>(key, null, null);
		      ++size;
		      return true;
		    }
		    
		    Node<E> current = root;
		    while (true)
		    {
		      int comp = current.getData().compareTo(key);
		      if (comp > 0)
		      {
		        if (current.getLeft() != null)
		        {
		          current = current.getLeft();
		        }
		        else
		        {
		        	Node<E> input = new Node<E>(key, null, null);
		          current.setLeft(input);
		          ++size;
		          return true;
		        }
		      }
		      else if(comp < 0)
		      {
		        if (current.getRight() != null)
		        {
		          current = current.getRight();
		        }
		        else
		        {
		          Node<E> input = new Node<E>(key, null, null);
		          current.setRight(input); 
		          ++size;
		          return true;
		        }
		      }
		    }
	}
  
	@SuppressWarnings("unchecked")
	@Override
	  public boolean remove(Object obj)
	  {
		redoPreorder = true;
		redoInorder = true; 
		redoPostorder = true;
	    // This cast may cause comparator to throw ClassCastException at runtime,
	    // which is the expected behavior
	    E key = (E) obj;
	    Node<E> n = findEntry(key);
	    if (n == null)
	    {
	      return false;
	    }
	    unlinkNode(n);
	    return true;
	  }
	  
	/**
	 * Returns the node containing key, or null if the key is not
	 * found in the tree.
	 * @param key
	 * @return the node containing key, or null if not found
	 */
	protected Node<E> findEntry(E key)
	  {
	    Node<E> current = root;
	    while (current != null)
	    {
	      int comp = current.getData().compareTo(key);
	      if (comp == 0)
	      {
	        return current;
	      }
	      else if (comp > 0)
	      {
	        current = current.getLeft();
	      }
	      else
	      {
	        current = current.getRight();
	      }
	    }   
	    return null;
	  }
	  
  
  
	
	// -------------------
	// Operations on Nodes
	// -------------------

	/**
	 * Returns the successor of the given node.
	 * @param n
	 * @return the successor of the given node in this tree, 
	 *   or null if there is no successor
	 */
	protected Node<E> successor(Node<E> n)
	  {
	    if (n == null)
	    {
	      return null;
	    }
	    else if (n.getRight() != null)
	    {
	      // leftmost entry in right subtree
	      Node<E> current = n.getRight();
	      while (current.getLeft() != null)
	      {
	        current = current.getLeft();
	      }
	      return current;
	    }
	    else 
	    {
	      // we need to go up the tree to the closest ancestor that is
	      // a left child; its parent must be the successor
	      Node<E> current = n.getParent();
	      Node<E> child = n;
	      while (current != null && current.getRight() == child)
	      {
	        child = current;
	        current = current.getParent();
	      }
	      // either current is null, or child is left child of current
	      return current;
	    }
	  }
  

	/**
	 * Returns the predecessor of a node.
	 * @param n
	 * @return the predecessor of the given node in this tree, 
	 *   or null if there is no successor
	 */
	public Node<E> predecessor(Node<E> n)
	{
		current = n;
		if(n.hasLeft())
		{
			current = current.getLeft();
			while(current.hasRight())
			{
				current = current.getRight();
			}
		}
		else
		{
			while(current.hasParent())
			{
				if(current.getParent().getData().compareTo(current.getData()) < 0)
				{
					return current.getParent();
				}
				current = current.getParent();
			}
		}
		
		
		
		return current; 
	}

	
	/**
	 * Performs left rotation on a node 
	 * Reset tags redoPreorder, redoInorder, redoPostorder 
	 * 
	 * @param n
	 */
	public void leftRotate(Node<E> n)
	{
		redoPreorder = true;
		redoPostorder = true;
		redoInorder = true;
			if(n.hasRight())
			{
				Node<E> tempRotate = n.getRight();
				tempRotate.setParent(n.getParent());
				if(n.getData().compareTo(n.getParent().getData()) > 0)
				{
					tempRotate.getParent().setRight(tempRotate);
				}
				else
				{
					tempRotate.getParent().setLeft(tempRotate);
				}
				if(tempRotate.hasLeft())
				{
					n.setRight(tempRotate.getLeft());
				}
				else
				{
					n.setRight(null);
				}
				tempRotate.setLeft(n);
				n.setParent(tempRotate);
			}

	}
  
	/**
	 * Performs right rotation on a node 
	 * Reset tags redoPreorder, redoInorder, redoPostorder 
	 * 
	 * @param n
	 */
	public void rightRotate(Node<E> n)
	{
		redoPreorder = true;
		redoPostorder = true;
		redoInorder = true;
		
		Node<E> tempRotate = n.getLeft();
		tempRotate.setParent(n.getParent());
		if(n.getData().compareTo(n.getParent().getData()) > 0)
		{
			tempRotate.getParent().setRight(tempRotate);
		}
		else
		{
			tempRotate.getParent().setLeft(tempRotate);
		}
		if(tempRotate.hasRight())
		{
			n.setLeft(tempRotate.getRight());
		}
		else
		{
			n.setLeft(null);
		}
		tempRotate.setRight(n);
		n.setParent(tempRotate);
	}
  
		
  
	/**
	 * Removes the given node, preserving the binary search
	 * tree property of the tree.
	 * @param n node to be removed
	 */
	protected void unlinkNode(Node<E> n)
	  {
	    // first deal with the two-child case; copy
	    // data from successor up to n, and then delete successor 
	    // node instead of given node n
	    if (n.getLeft() != null && n.getRight() != null)
	    {
	      Node<E> s = successor(n);
	      n.setData(s.getData());
	      n = s; // causes s to be deleted in code below
	    }
	    
	    // n has at most one child
	    Node<E> replacement = null;    
	    if (n.getLeft() != null)
	    {
	      replacement = n.getLeft();
	    }
	    else if (n.getRight() != null)
	    {
	      replacement = n.getRight();
	    }
	    
	    // link replacement into tree in place of node n 
	    // (replacement may be null)
	    if (n.getParent() == null)
	    {
	      root = replacement;
	    }
	    else
	    {
	      if (n == n.getParent().getLeft())
	      {
	        n.getParent().setLeft(replacement);
	      }
	      else
	      {
	        n.getParent().setRight(replacement);
	      }
	    }
	    
	    if (replacement != null)
	    {
	      replacement.setParent(n.getParent());
	    }
	    
	    --size;
	  }
	

	
	// -------------
	// String output
	// -------------
 
	/**
	 * Returns a representation of this tree as a multi-line string.
	 * The tree is drawn with the root at the left and children are
	 * shown top-to-bottom.  Leaves are marked with a "-" and non-leaves
	 * are marked with a "+".
	 */
	@Override
	  public String toString()
	  {
	    StringBuilder sb = new StringBuilder();
	    toStringRec(root, sb, 0);
	    return sb.toString();
	  }
 
	/**
	 * Iterator implementation is from BSTSet.java and thus not required here. 
	 */	
	@Override
	public Iterator<E> iterator()
	{
	    return null;
	}

  
	/**
	 * Checks if the tree with a given root is a binary search tree. 
	 * @param rt
	 */
	private boolean isBST(Node<E> root)
	{
		BST<E> copyTree = new BST<E>(root, 0);
		copyTree.traverseInorder();
		for(int i = 1; i < copyTree.inorderArr.size(); i++)
		{
			if(copyTree.inorderArr.get(i).compareTo(copyTree.inorderArr.get(i-1)) < 0)
			{
				return false;
			}
		}
		
		return true; 
	}  
	
	/**
	   * Preorder traversal of the tree that builds a string representation
	   * in the given StringBuilder.
	   * @param n root of subtree to be traversed
	   * @param sb StringBuilder in which to create a string representation
	   * @param depth depth of the given node in the tree
	   */
	  private void toStringRec(Node<E> n, StringBuilder sb, int depth)
	  {
	    for (int i = 0; i < depth; ++i)
	    {
	      sb.append("  ");
	    }
	    
	    if (n == null)
	    {
	      sb.append("-\n");
	      return;
	    }
	    
	    if (n.getLeft() != null || n.getRight() != null)
	    {
	      sb.append("+ ");
	    }
	    else
	    {
	      sb.append("- ");
	    }
	    sb.append(n.getData().toString());
	    sb.append("\n");
	    if (n.getLeft() != null || n.getRight() != null)
	    {
	      toStringRec(n.getLeft(), sb, depth + 1);   
	      toStringRec(n.getRight(), sb, depth + 1);
	    }
	  }
	  
	  private void traversePre(Node<E> inputNode)
	  {
		  if(inputNode == null)
		  {
			  return;
		  }
		  preorderArr.add(inputNode.getData());
		  traversePre(inputNode.getLeft());
		  traversePre(inputNode.getRight());
	  }
	  
	  private void traverseIn(Node<E> inputNode)
	  {
		  if(inputNode == null)
		  {
			  return;
		  }
		  traverseIn(inputNode.getLeft());
		  inorderArr.add(inputNode.getData());
		  traverseIn(inputNode.getRight());
	  }
	  
	  private void traversePost(Node<E> inputNode)
	  {
		  if(inputNode == null)
		  {
			  return;
		  }
		  traversePost(inputNode.getLeft());
		  traversePost(inputNode.getRight());
		  postorderArr.add(inputNode.getData());
	  }
	  
	  private int traverseHeight(int intInput, Node<E> nodeInput)
	  {
		  int curInt = intInput;
		  if(!nodeInput.hasLeft() && !nodeInput.hasRight())
		  {
			  return intInput;
		  }
		  else
		  {
			  if(nodeInput.getLeft() != null)
			  {
				  curInt = Math.max(intInput,traverseHeight(intInput+1, nodeInput.getLeft()));
			  }
			  if(nodeInput.getRight() != null)
			  {
				  curInt = Math.max(intInput,(traverseHeight(intInput+1, nodeInput.getRight())));
			  }
		  }
		  return curInt;
	  }
}
