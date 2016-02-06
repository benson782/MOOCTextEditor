package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// check if generator is already trained
		if (wordList.size() > 0) {
			// must use the retrain method if already trained
			return;
		}

		// setup
		String[] sourceTextList = sourceText.split("\\s+");
		starter = sourceTextList[0];
		String prevWord = starter;
		
		// train  generator
		for (int i = 1; i < sourceTextList.length; i++) {
			String word = sourceTextList[i];
			
			// add word to the word list
			updateWordList(prevWord, word);
			
			// set prevWord to word
			prevWord = word;
		}
		
		// add starter to be a next word for the last word in the source text
		// prevWord should have the last word
		updateWordList(prevWord, starter);
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
		String currWord = starter;
		String output = "";
		int wordCount = 0;
		
		// add currWord to the output
		if (numWords > 0) {
			output += currWord +  " ";
			wordCount++;
		}
		
		// add more words
		while (wordCount < numWords) {
			ListNode wordNode = getWordFromWordList(currWord);
			
			// throw exception if wordNode is null
			if (wordNode == null) {
				break;
			}

			// add random word to the output
			String word = wordNode.getRandomNextWord(rnGenerator);
			output += word + " ";
			currWord = word;
			wordCount++;
		}
		
		return output.trim();
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// reset the wordList and starter variables
		wordList.clear();
		starter = "";
		train(sourceText);
	}
	
	// TODO: Add any private helper methods you need here.
	
	/**
	 * Get a word node from the word list
	 * @param word the word linking the next words
	 * @return the word node.  null if not found
	 */
	private ListNode getWordFromWordList (String word) {
		for (int i = 0; i < wordList.size(); i++) {
			ListNode node = wordList.get(i);
			if (node.getWord().equals(word)) {
				return node;
			}
		}
		return null;
	}
	
	/**
	 * Update the word list
	 * @param word the word linking the next words
	 * @param nextWord the next word to add
	 */
	private void updateWordList (String word, String nextWord) {
		// check to see if "prevWord" is already a node in the list
		ListNode node = getWordFromWordList(word);
		
		if (node == null) {
			// add new node to the list with new word
			node = new ListNode(word);
			node.addNextWord(nextWord);
			wordList.add(node);
		}
		else {
			// add the new word to the node in the list
			node.addNextWord(nextWord);
		}
	}
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		int randomIndex = generator.nextInt(nextWords.size());
		return nextWords.get(randomIndex);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


