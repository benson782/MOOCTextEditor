/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		
		// test out of bounds
		try {
			shortList.remove(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {}

		try {
			shortList.remove(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {}
		
		// remove beginning
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
		// remove at end
		int newEndData = list1.get(list1.size() - 2);
		int endData = list1.get(list1.size() - 1);
		int endRemoved = list1.remove(list1.size() - 1);
		assertEquals("Remove: end removed is correct ", endData, endRemoved);
		assertEquals("Remove: check new end data ", (Integer)newEndData, list1.get(list1.size() - 1));
		assertEquals("Remove: check size is correct ", 1, list1.size());
		
		// add some more to the list
		list1.add(90);
		list1.add(92);
		list1.add(93);
		list1.add(99);
		list1.add(98);
		
		// remove from middle
		int currentSize = list1.size();
		int middleIndex = list1.size() / 2;
		int newMiddleData = list1.get(middleIndex + 1);
		int middeData = list1.get(middleIndex);
		int middleRemoved = list1.remove(middleIndex);
		assertEquals("Remove: end middle is correct ", middeData, middleRemoved);
		assertEquals("Remove: check new middle data ", (Integer)newMiddleData, list1.get(middleIndex));
		assertEquals("Remove: check size is correct ", currentSize - 1, list1.size());
		
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
		// adding null
		try {
			shortList.add(null);
			fail("Check adding null");
		}
		catch (NullPointerException e) {}
		
		// add to end
		list1.add(100);
		assertEquals("Add End: 100 is correct ", (Integer)100, list1.get(list1.size() - 1));
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// TODO: implement this test
		assertEquals("Size: empty list is 0 ", 0, emptyList.size());
		
		// test on list1 size
		int currentSize = list1.size();
		
		// add to list1
		list1.add(1000);
		currentSize++;
		assertEquals("Size: size increment ", currentSize, list1.size());
		
		// remove from list1
		list1.remove(list1.size() - 1);
		currentSize--;
		assertEquals("Size: size decrement ", currentSize, list1.size());
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
		// test out of bounds
		try {
			shortList.add(-1, "Negative");
			fail("Check out of bounds under");
		}
		catch (IndexOutOfBoundsException e) {}

		try {
			shortList.add(shortList.size()+1, "Over");
			fail("Check out of bounds over");
		}
		catch (IndexOutOfBoundsException e) {}
		
		// test adding null
		try {
			shortList.add(0, null);
			fail("Check adding null");
		}
		catch (NullPointerException e) {}
		
		// add to empty at index 0
		emptyList.add(0, 69);
		assertEquals("Add At Index 0: 69 is correct ", (Integer)69, emptyList.get(0));
		assertEquals("Remove At Index 0: 69 is correct ", (Integer)69, emptyList.remove(0));
		
		// add to beginning
		list1.add(0, 69);
		assertEquals("Add At Index 0: 69 is correct ", (Integer)69, list1.get(0));
		
		// add to end
		list1.add(list1.size() - 1, 169);
		Integer valAtEnd = list1.get(list1.size() - 2); 
		assertEquals("Add At Index end: 169 is correct ", (Integer)169, valAtEnd);
		
		// add to middle
		int middleIndex = list1.size() / 2;
		int middleIndexOldVal = list1.get(middleIndex);
		list1.add(middleIndex, 269);
		assertEquals("Add At Index " + middleIndex + ": 269 is correct ", (Integer)269, list1.get(middleIndex));
		assertEquals("Add At Index " + (middleIndex+1) + ": "+ middleIndexOldVal + " is correct ", 
				(Integer)middleIndexOldVal, list1.get(middleIndex+1));
		
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
		// test out of bounds
		try {
			shortList.set(-1, "Negative");
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {}

		try {
			shortList.set(2, "Over");
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {}
		
		// test setting null
		try {
			shortList.set(0, null);
			fail("Check setting null");
		}
		catch (NullPointerException e) {}
		
	    // set middle element
		int middleIndex = list1.size() / 2;
		int oldVal = list1.get(middleIndex);
		int oldVal2 = list1.set(middleIndex, 20000);
		assertEquals("Set: old data and data returned", oldVal, oldVal2);
	}
	
	
	// TODO: Optionally add more test methods.
	
}
