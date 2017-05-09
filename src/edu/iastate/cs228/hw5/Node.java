package edu.iastate.cs228.hw5;

/**
 * 
 * @author David Bruce
 *
 */


/**
 * 
 * This class represents a tree node.  The class is public but in the BST class 
 * the root node will be protected. 
 *
 */

// also okay to use the following. 
public class Node<E extends Comparable<? super E>>
//public class Node<E> 
{
	private E data; 
	private Node<E> parent; 
	private Node<E> left; 
	private Node<E> right; 
	
	
	public Node(E dat)
	{
		data = dat;
	}

	public Node(E dat, Node<E> left, Node<E> right)
	{
		data = dat;
		this.left = left;
		this.right = right;
	}
	
	/**
	 * Write the value of the instance variable named data.
	 */
	public String toString()
	{
		// TODO
		
		return null; 
	}

	public E getData()
	{	
		return data; 
	}
	
	public void setData(E dat)
	{
		data = dat;
	}
	
	public Node<E> getParent()
	{
		return parent; 
	}
	
	public void setParent(Node<E> parent)
	{
		this.parent = parent;
	}
	
	public Node<E> getLeft()
	{
		return left; 
	}
	
	public void setLeft(Node<E> left)
	{
		this.left = left;
	}
	
	public Node<E> getRight()
	{
		return right; 
	}
	
	public void setRight(Node<E> right)
	{
		this.right = right;
	}
	
	public boolean hasLeft()
	{
		if(left == null)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean hasRight()
	{
		if(right == null)
		{
			return false;
		}
		
		return true;
	}
	
	public boolean hasParent()
	{
		if(parent == null)
		{
			return false;
		}
		
		return true;
	}
	
}
