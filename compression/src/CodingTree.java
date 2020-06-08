import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * An implementation of Trie where every node only has 2 children- a 0 child and
 * a 1 child
 * 
 * @author Alex Cohen
 */
public class CodingTree {

	private Trie trie_; // The underlying trie

	/**
	 * Create a new CodingTree from a Trie
	 * 
	 * @param trie
	 *          the Trie that the CodingTree will be made from
	 */
	public CodingTree ( Trie trie ) {
		trie_ = trie;
	}

	/**
	 * Create a new CodingTree from a BitInputStream
	 * 
	 * @param in
	 *          the BitInputStream
	 */
	public CodingTree ( BitInputStream in ) {
		trie_ = recursiveBuddy(in);
	}

	/**
	 * Helper for the CodingTree ( BitInputStream ) Constructor
	 * 
	 * @param The
	 *          BitInputStream
	 * @return Trie the finished trie representing the initialized coding tree
	 */
	private Trie recursiveBuddy ( BitInputStream in ) {
		// read 1 bit
		// BitString bit = new BitString();
		try {
			BitString intBit = in.readBits(1);

			// if bit is 1 denoting leaf, read HuffConstants.BITS_PER_CHARACTER,
			// convert them to a Character, and return a new single-node trie with
			// that
			// Character as its element
			if ( intBit.toString().equals("1") ) {
				Character ch =
				    (in.readBits(HuffConstants.BITS_PER_CHARACTER)).asCharacter();
				return new Trie(ch);
			}
			// else if the bit is 0, denoting an internal node, recursively call the
			// helper to read each of the "0" and "1" subtrees and return a new Trie
			// with
			// those two subtrees attached as children of the root
			else if ( intBit.toString().equals("0") ) {
				Trie parentTrie = new Trie();
				parentTrie.attach(parentTrie.getRoot(),recursiveBuddy(in),"0");
				parentTrie.attach(parentTrie.getRoot(),recursiveBuddy(in),"1");
				return parentTrie;
			} else {
				// throw new IllegalArgumentException("BitInputStream in should only
				// contain 1s and 0s");
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		System.out.println("here");
		return trie_;

	}

	/**
	 * Writes all possible sequences of 0s and 1s represented by each root to leaf
	 * path
	 * 
	 * @param out
	 *          the BitOutputStream to write paths to
	 */
	public void write ( BitOutputStream out ) {
		writeRecurse(trie_.getRoot(),out);
	}

	/**
	 * Helper for write(BitOuputStream)
	 * 
	 * @param node
	 *          The node whose children will be visited while building up the
	 *          BitOutputStream
	 * @param out
	 *          The BitOutputStream to write paths to
	 */
	private void writeRecurse ( Node node, BitOutputStream out ) {
		try {
			if ( trie_.isInternal(node) ) {
				out.writeBits(new BitString("0"));
				// recurse on both children
				writeRecurse(trie_.getChild(node,"0"),out);
				writeRecurse(trie_.getChild(node,"1"),out);
			} else if ( !trie_.isInternal(node) ) {
				out.writeBits(new BitString("1"));
				out.writeBits(new BitString(node.getElement()));

			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the next char represented by the given BitInputStream
	 * 
	 * @param in
	 *          the BitInputStream
	 * @return the next Character
	 * @throws IOException
	 */
	public Character nextChar ( BitInputStream in ) throws IOException {
		// recursion for every other method?
		// try {
		Node currentNode = trie_.getRoot();
		for ( ; trie_.isLeaf(currentNode) == false ; ) {
			BitString next = in.readBits(1);
			System.out.println("next.toString(): " + next.toString());
			// Character nextCharacter = next.asCharacter();
			String strNext = next.toString();
			currentNode = trie_.getChild(currentNode,strNext);
		}
		return currentNode.getElement();
		// } catch ( IOException e ) {
		// e.printStackTrace();
		// }
		// return null;
	}

	/**
	 * Gets a map of the encodings from characters to their corresponding
	 * bitstrings
	 * 
	 * @return a Map that maps each Character to its corresponding BitString
	 */
	public Map<Character,BitString> getEncodings () {
		Map<Character,BitString> map = new TreeMap<Character,BitString>();
		computeOutcomes("",trie_.getRoot(),map);
		return map;
	}

	/**
	 * Private helper for getEncodings()
	 * 
	 * @param currentString
	 *          the current String, representing a bitString that we have built up
	 * @param currentNode
	 *          the current node that we are dealing with
	 * @param map
	 *          the map that will hold the final result
	 */
	private void computeOutcomes ( String currentString, Node currentNode,
	                               Map<Character,BitString> map ) {
		if ( trie_.isLeaf(currentNode) ) {
			map.put(currentNode.getElement(),new BitString(currentString));
		} else {
			// left child
			computeOutcomes(currentString + "0",trie_.getChild(currentNode,"0"),map);
			// right child
			computeOutcomes(currentString + "1",trie_.getChild(currentNode,"1"),map);
		}
	}
}
