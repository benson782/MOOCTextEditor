package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		size = 0;
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// Check element is not null
		if (element == null) {
			throw new NullPointerException();
		}
		
		LLNode<E> nodeToAdd = new LLNode<E>(element, tail.prev, tail);
		tail.prev.next = nodeToAdd;
		tail.prev = nodeToAdd ; //update the sentinel node's prev must be last;
		size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// Check index is valid
		if (!isIndexValid(index)) {
			throw new IndexOutOfBoundsException();
		}
		
		LLNode<E> node = getNodeAtIndex(index);
		
		return node.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// Check element is not null
		if (element == null) {
			throw new NullPointerException();
		}
		
		// Check index is valid
		if (!isIndexValid(index)) {
			if (index == size && size == 0) {/* Do Nothing */}
			else {
				throw new IndexOutOfBoundsException();
			}
		}
		
		LLNode<E> nodeAtIndex = getNodeAtIndex(index);
		LLNode<E> nodeToAdd = new LLNode<E>(element, nodeAtIndex.prev, nodeAtIndex);
		nodeAtIndex.prev.next = nodeToAdd;
		nodeAtIndex.prev = nodeToAdd;
		size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// Check index is valid
		if (!isIndexValid(index)) {
			throw new IndexOutOfBoundsException();
		}
		
		// get the node to remove
		LLNode<E> nodeToRemove = getNodeAtIndex(index);
		
		// change previous node's next node to nodeToRemove's next node
		nodeToRemove.prev.next = nodeToRemove.next;
		
		// change next node's prev node to nodeToRemove's prev node
		nodeToRemove.next.prev = nodeToRemove.prev;
		
		// update size
		size--;
		
		return nodeToRemove.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 * @throws NullPointerException if the element is null.
	 */
	public E set(int index, E element) 
	{
		// Check index is valid
		if (!isIndexValid(index)) {
			throw new IndexOutOfBoundsException();
		}
		
		// Check element is not null
		if (element == null) {
			throw new NullPointerException();
		}
		
		// set the new element
		LLNode<E> nodeToReplace = getNodeAtIndex(index);
		E oldData = nodeToReplace.getData();
		nodeToReplace.setData(element);
		return oldData;
	}
	
	/** Validates the index is valid  **/
	private boolean isIndexValid(int index) {
		return (index < 0 || index >= size) ? false : true;
 	}
	
	/** Get a node at the index **/
	private LLNode<E> getNodeAtIndex(int index) {
		LLNode<E> nodeCurr = head;
		for(int i = index; i >= 0; i--) {
			nodeCurr = nodeCurr.next;
		}
		return nodeCurr;
	}
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	public LLNode(E e, LLNode<E> prev, LLNode<E> next) {
		this.data = e;
		this.prev = prev;
		this.next = next;
	}
	
	public E getData() {
		return data;
	}
	
	public void setData(E e) {
		data = e;
	}
}
