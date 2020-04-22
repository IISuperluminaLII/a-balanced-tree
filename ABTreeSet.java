package src.edu.iastate.cs228.hw5;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;


/**
 * @author SM
 */
public class ABTreeSet<E extends Comparable<? super E>> extends AbstractSet<E> {

	Node root = null;
	int BSTsize = 0;
	
	
	final class ABTreeIterator implements Iterator<E> {
		// TODO add private fields here
		private Node nextNode = null;
		private Node previousNode = null;

		ABTreeIterator() {
			// TODO
			
			nextNode = root;

			if ( nextNode != null ){
				while ( nextNode.left() != null )
					nextNode = nextNode.left;
			} // default constructor
			
			else
				throw new IllegalStateException();
			
		}

		@Override
		public boolean hasNext() {
			// TODO
			return (nextNode!=null);
		}

		@Override
		public E next() {
			// TODO
			if ( nextNode == null )
				throw new IllegalStateException();

			previousNode = nextNode;
			nextNode = (Node)successor(nextNode);
			return previousNode.data();
		}

		@Override
		public void remove() {
			// TODO
			
			if ( previousNode == null )
				throw new IllegalStateException();

			if ( previousNode.left() != null && previousNode.right() != null )//{
				nextNode = previousNode;

			delete(previousNode);
			previousNode = null;
			BSTsize--;
			
		}
	}

	final class Node implements BSTNode<E> {
		private int count;
		private E data;
		private Node right, left, parent;

		// TODO add private fields here

		Node(E data) {
			// TODO
			this.data = data;
		}

		@Override
		public int count() {
			return count;
		}

		@Override
		public E data() {
			// TODO

			return data;
		}

		@Override
		public BSTNode<E> left() {
			// TODO
			count++;
			return left;
		}

		@Override
		public BSTNode<E> parent() {
			// TODO
			count++;
			return parent;
		}

		@Override
		public BSTNode<E> right() {
			// TODO
			count++;
			return right;
		}

		@Override
		public String toString() {
			return data.toString();
		}
	}

	private boolean isSelfBalancing;
	private int top;
	private int bottom;

	// TODO add private fields here

	/**
	 * Default constructor. Builds a non-self-balancing tree.
	 */
	public ABTreeSet() {
		// TODO
		
		this.isSelfBalancing = false;
		
			
	}

	/**
	 * If <code>isSelfBalancing</code> is <code>true</code> <br>
	 * builds a self-balancing tree with the default value alpha = 2/3.
	 * <p>
	 * If <code>isSelfBalancing</code> is <code>false</code> <br>
	 * builds a non-self-balancing tree.
	 * 
	 * @param isSelfBalancing
	 */
	public ABTreeSet(boolean isSelfBalancing) {
		// TODO
		
		this.isSelfBalancing = isSelfBalancing;
		top = 2;
		bottom = 3;

	}

	/**
	 * If <code>isSelfBalancing</code> is <code>true</code> <br>
	 * builds a self-balancing tree with alpha = top / bottom.
	 * <p>
	 * If <code>isSelfBalancing</code> is <code>false</code> <br>
	 * builds a non-self-balancing tree (top and bottom are ignored).
	 * 
	 * @param isSelfBalancing
	 * @param top
	 * @param bottom
	 * @throws IllegalArgumentException
	 *             if (1/2 < alpha < 1) is false
	 */
	public ABTreeSet(boolean isSelfBalancing, int top, int bottom) {
		// TODO

		
		this.isSelfBalancing = isSelfBalancing;
		
		if(isSelfBalancing){
			
			if(top/bottom < (1/2)){
				throw new IllegalArgumentException("top/bottom < 1/2");
			}
			
			this.top = top;
			this.bottom = bottom;
		}
		
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException
	 *             if e is null.
	 */
	@Override
	public boolean add(E e) {
		
		if(this.size() == 0){
			root = new Node(e);
		}
		
		boolean checkToAdd = recAddItem(root, e);

		if(checkToAdd)
			this.BSTsize++;
		else
			return false;

		return false;
		
		
		
	}
	
	private boolean recAddItem(Node r, E item)
	{
		if ( r == null || item == null )
			throw new NullPointerException("recAdd problem");

		Node tmp;

		//Right or Left inital compare
		int cv = item.compareTo(r.data());

		//equal?
		if ( cv == 0 ) 
			return false;


		if ( cv > 0 ){ 
			//recursively scrape the Nodes
			if ( r.right() == null ){ 
				tmp = new Node(item);
				r.right = tmp;	  
				tmp.parent = r;
				return true;
			}

			else
				return recAddItem((Node) r.right(), item);
		}
		//same for left side
		if ( r.left() == null ){ 
			tmp = new Node(item);
			r.left = tmp;
			tmp.parent = r;
			return true;
		}
		else
			return recAddItem((Node)r.left(), item);
	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object o) {
		// TODO
				
		Iterator<E> itr = this.iterator();
		
		while(itr.hasNext()){
			
			if(itr.next().compareTo((E) o) == 0){
				return true;
			}
			
		}
		

		return false;
	}

	/**
	 * @param e
	 * @return BSTNode that contains e, null if e does not exist
	 */
	@SuppressWarnings("unchecked")
	public BSTNode<E> getBSTNode(E e) {
		// TODO
		
		Iterator<E> itr = this.iterator();
		
		while(itr.hasNext()){
			E data = itr.next();
			
			if(e.compareTo(data) == 0){
				return (BSTNode<E>)data;
			}	
		}
		
		return null;
		
		
	}
        
	/**
	 * Returns an in-order list of all nodes in the given sub-tree.
	 * 
	 * @param root
	 * @return an in-order list of all nodes in the given sub-tree.
	 */
	public List<BSTNode<E>> inorderList(BSTNode<E> root) {
		// TODO
		List<BSTNode<E>> result = new ArrayList<BSTNode<E>>();

		if(root !=null){
			InOrderRecursiveHelper((Node)root,result);
		}

		return result;    
	}

	public void InOrderRecursiveHelper(Node p, List<BSTNode<E>> result){

		if(p.left()!=null)
			InOrderRecursiveHelper(p.left,result);

		result.add(new Node(p.data));

		if(p.right()!=null)
			InOrderRecursiveHelper(p.right,result);
	}
	
	@Override
	public Iterator<E> iterator() {
		// TODO
		
		
		return new ABTreeIterator();
	}

	/**
	 * Returns an pre-order list of all nodes in the given sub-tree.
	 * 
	 * @param root
	 * @return an pre-order list of all nodes in the given sub-tree.
	 */
	public List<BSTNode<E>> preorderList(BSTNode<E> root) {
		// TODO

		List<BSTNode<E>> returnList = new ArrayList<BSTNode<E>>();

		if(root == null)
			return returnList;

		Stack<Node> stack = new Stack<Node>();
		stack.push((Node)root);

		while(!stack.empty()){
			
			Node n = stack.pop();
			returnList.add(new Node(n.data()));

			if(n.right() != null){
				stack.push(n.right);
			}
			if(n.left() != null){
				stack.push(n.left);
			}

		}
		return returnList;
	}

	

	/**
	 * Performs a re-balance operation on the subtree rooted at the given node.
	 * 
	 * @param bstNode
	 */
	public void rebalance(BSTNode<E> bstNode) {
		// TODO
		
		int midPoint = (this.top + this.bottom)/2;
		
		

	}

	@Override
	public boolean remove(Object o) {
		// TODO

		if ( o == null ) 
			return false;

		Node toDel = findNode((E)o);
		if (toDel == null) 
			return false;

		delete( toDel );
		this.BSTsize--;
		return true;
	}
	private Node findNode(E item)
	{
		Node curpos = root;
		while (curpos != null)
		{
			int cv = item.compareTo(curpos.data());

			if (cv == 0) 
				return curpos;

			if (cv > 0) 
				curpos = curpos.right;

			else 
				curpos = curpos.left;
		}

		return null;

	} // It takes O(h) time, where h is the height of the tree.
	private void delete(Node aNode)
	{
		if ( aNode == null )
			throw new NullPointerException("delete");

		Node toDel = aNode;

		if ( toDel.left() != null && toDel.right() != null )
		{
			Node nextNodeToLink = (Node) this.successor(toDel);
			toDel.data = (nextNodeToLink.data());
			toDel = nextNodeToLink;
		}

		// at this point we know toDel has at most one child.
		if (toDel.left() != null){ // has left child 
			if ( toDel == root )
			{
				root = (Node) toDel.left();
				if ( toDel.left() != null)
					toDel.left.parent = (null);
			}
			else
			{
				if (toDel.parent.left() == toDel)
					toDel.parent.left = (Node)(toDel.left());
				
				else
					toDel.parent.right = (Node)(toDel.left());
				
				if(toDel.left() != null)
					toDel.left.parent = (Node)(toDel.parent());
			}
		}
		//same as last but with right?
		else{ // has right child or null
			if ( toDel == root )
			{
				root = (Node)toDel.right();
				if ( toDel.right() != null)
					toDel.right.parent = (null);
			}
			else
			{
				if (toDel.parent().right() == toDel)
					toDel.parent.left = (Node)(toDel.right());
				
				else
					toDel.parent.right = (Node) (toDel.right());
				
				if(toDel.left() != null)
					toDel.left.parent = (Node)(toDel.parent());
			}
		
		}
			
	} // It takes O(h) time.

	/**
	 * Returns the root of the tree.
	 * 
	 * @return the root of the tree.
	 */
	public BSTNode<E> root() {
		// TODO
		return root;
	}

	public void setSelfBalance(boolean isSelfBalance) {
		// TODO
		this.isSelfBalancing = isSelfBalance;
	}

	@Override
	public int size() {
		// TODO
		return this.BSTsize;
	}

	public BSTNode<E> successor(BSTNode<E> node) {
		// TODO
		node = (Node)node;
		if (node.right() != null)
		{
			Node next = (Node)node.left();
			while (next.left() != null)
			{
				next = (Node) next.left();
			}
			return next;
		}
		Node parentNode = (Node)node.parent();
		Node childNode = (Node)node;
		while ( parentNode!=null && parentNode.right() == childNode )
		{
			childNode = parentNode;
			parentNode = (Node)parentNode.parent();
		}
		return parentNode;
	}

	@Override
	public String toString() {
		// TODO

		return null;
	}

}
