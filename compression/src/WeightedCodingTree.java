import java.util.Map;

/**
 * A coding tree with an associated integer weight
 * 
 * @author Alex Cohen
 */
public class WeightedCodingTree {
	private Trie trie_; // the underlying trie
	private int weight_; // the weight

	/**
	 * Creates a new weighted coding tree
	 * 
	 * @param weight
	 *          the weight for the weighted coding tree
	 * @param c
	 *          the character to be added to the root of the tree
	 */
	public WeightedCodingTree ( int weight, Character c ) {
		weight_ = weight;
		trie_ = new Trie(c);
	}

	/**
	 * Creates a new Weighted Coding Tree where the 0 child of the root is tree1
	 * and the 1 child of the root is tree2
	 * 
	 * @param tree1
	 *          the tree that will become the 0 child
	 * @param tree2
	 *          the tree that will become the 1 child
	 */
	public WeightedCodingTree ( WeightedCodingTree tree1,
	                            WeightedCodingTree tree2 ) {
		weight_ = tree1.getWeight() + tree2.getWeight();
		trie_ = new Trie();
		trie_.attach(trie_.root_,tree1.trie_,"0");
		trie_.attach(trie_.root_,tree2.trie_,"1");
	}

	/**
	 * Gets a coding tree of the Weighted Coding Tree
	 * 
	 * @return the coding tree
	 */
	public CodingTree getCodingTree () {
		return new CodingTree(trie_);
	}

	/**
	 * Gets the weight associated with the coding tree
	 * 
	 * @return the weight
	 */
	public int getWeight () {
		return weight_;
	}
}
