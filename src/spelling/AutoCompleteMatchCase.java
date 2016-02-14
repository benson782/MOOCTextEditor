package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteMatchCase implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteMatchCase()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
		TrieNode currNode = root;
		String wordLower = word.toLowerCase();

		// create the nodes for each letter in the word
		for (char c : wordLower.toCharArray()) {
			TrieNode childNode = currNode.insert(c);
			if (childNode == null) {
				childNode = currNode.getChild(c);
			}
			currNode = childNode;
		}
		
		// set the end of the node as a word
		if (!currNode.endsWord()) {
			currNode.setEndsWord(true);
			size++;
			return true;
		}
	    return false;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{
		TrieNode currNode = root;
		String wordLower = s.toLowerCase();

		// traverse down the treed
		for (char c : wordLower.toCharArray()) {
			TrieNode childNode = currNode.getChild(c);
			
			// reached the end of the tree
			if (childNode == null) {
				return false;
			}
			
			// found the word
			if (childNode.endsWord() && childNode.getText().equals(wordLower)) {
				return true;
			}
			
			// continue down the tree
			currNode = childNode;
		}
		return false;
	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 
    	 List<String> completions = new ArrayList<>();
    	 String prefixLower = prefix.toLowerCase();
    	 
    	 // verify inputs
    	 if (numCompletions <= 0) {
    		 return completions;
    	 }
    	 
    	 // find the stem
    	 TrieNode stemNode = root;
    	 for(char c : prefixLower.toCharArray()) {
    		 TrieNode childNode = stemNode.getChild(c);
 			if (childNode == null) {
 				return completions;
 			}
 			stemNode = childNode;
    	 }
    	 
    	 // search for completions
    	 int completionsFound = 0;
    	 Queue<TrieNode> toVisit = new LinkedList<>();
    	 toVisit.add(stemNode);
    	 while(!toVisit.isEmpty() && completionsFound < numCompletions) {
    		 TrieNode curr = toVisit.remove();
    		 
    		 // add to completion list if a word
    		 if (curr.endsWord()) {
    			 completions.add(curr.getText());
    			 completionsFound++;
    		 }
    		 
    		 // add children to queue
    		 for (char c : curr.getValidNextCharacters()) {
    			 toVisit.add(curr.getChild(c));
    		 }
    	 }
    	 
         return completions;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}