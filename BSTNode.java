import src.edu.iastate.cs228.hw5.ABTreeSet.Node;

public interface BSTNode<E> {

	

	int count();

	E data();

	BSTNode<E> left();

	BSTNode<E> parent();

	BSTNode<E> right();

	String toString();
}
