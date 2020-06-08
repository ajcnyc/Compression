import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * A Trie (a type of tree)
 * 
 * @author Alex Cohen
 */
public class Trie {
	/**
	 * A node of a Trie
	 * 
	 * @author ac4176
	 */
	private class TrieNode implements Node {
		private Character elt_; // the TrieNode's element
		private Map<String,TrieNode> map_ = new TreeMap<String,TrieNode>();
		// associates each child of a TrieNode with a String

		/**
		 * Create a new TrieNode with a null element
		 */
		private TrieNode () {
			elt_ = null;
		}

		/**
		 * Create a new TrieNode with elt as its element
		 * 
		 * @param elt
		 *          the TrieNode's element
		 */
		private TrieNode ( Character elt ) {
			elt_ = elt;

		}

		/**
		 * Returns the TrieNode's element
		 * 
		 * @return the TrieNode's element
		 */
		public Character getElement () {
			return elt_;
		}

		/**
		 * Returns the TrieNode's number of children
		 * 
		 * @return the TrieNode's number of children
		 */
		private int getNumChildren () {
			return map_.size();
		}

		/**
		 * Returns the TrieNode's branches
		 * 
		 * @return a set of the strings associated with TrieNode's children
		 */
		private Set<String> getBranches () {
			return map_.keySet();
		}

		/**
		 * Returns the TrieNode's child corresponding to the given String
		 * 
		 * @param the
		 *          String
		 * @return the TrieNode's child
		 */
		TrieNode getChild ( String str ) {
			return map_.get(str);
		}

		/**
		 * Attaches node as a child of this node with the String str
		 * 
		 * @param node
		 *          the node to be attached
		 * @param str
		 *          the String
		 */
		private void attach ( TrieNode node, String str ) {
			map_.put(str,node);
		}
	}

	TrieNode root_; // the root of the Trie

	/**
	 * Creates a new Trie with only 1 node: the root
	 */
	public Trie () {
		root_ = new TrieNode();
	}

	/**
	 * Creates a new Trie with only 1 node: the root which will hold elt
	 * 
	 * @param elt
	 *          the element for the root to hold
	 */
	public Trie ( Character elt ) {
		root_ = new TrieNode(elt);
	}

	/**
	 * Gets the root of the Trie
	 * 
	 * @return the Trie's root node
	 */
	public Node getRoot () {
		return root_;
	}

	/**
	 * Checks whether node is a leaf or not
	 * 
	 * @param node
	 *          the node to check
	 * @return true if node is a leaf, false otherwise
	 */
	public boolean isLeaf ( Node node ) {
		if ( ((TrieNode) node).getNumChildren() == 0 ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks whether node is internal or not
	 * 
	 * @param node
	 *          the node to check
	 * @return true if node is internal, false otherwise
	 */
	public boolean isInternal ( Node node ) {
		if ( ((TrieNode) node).getNumChildren() != 0 ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks whether node has a child associated with the given String
	 * 
	 * @param node
	 *          the node to check
	 * @param the
	 *          given String
	 * @return true if node has a child associated with the given String, false
	 *         otherwise
	 */
	public boolean hasChild ( Node node, String str ) {
		if ( ((TrieNode) node).getBranches().contains(str) ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the number of children that node has
	 * 
	 * @param node
	 *          the node to check
	 * @return the number of children
	 */
	public int getNumChildren ( Node node ) {
		return ((TrieNode) node).getNumChildren();
	}

	/**
	 * Returns the branches of the given Node
	 * 
	 * @param the
	 *          given Node
	 * @return a set of the strings associated with Node's children
	 */
	public Set<String> getBranches ( Node node ) {
		return ((TrieNode) node).getBranches();
	}

	/**
	 * Returns the Node's child corresponding to the given String
	 * 
	 * @param the
	 *          Node
	 * @param the
	 *          String
	 * @return the Node's child
	 */
	public TrieNode getChild ( Node node, String str ) {
		return ((TrieNode) node).getChild(str);
	}

	/**
	 * Attaches trie as a child of this node with the String str
	 * 
	 * @param node
	 *          the node to be attached
	 * @param trie
	 *          the trie to be attached
	 * @param str
	 *          the String
	 */
	public void attach ( Node node, Trie trie, String str ) {
		TrieNode tnode = (TrieNode) node;
		TrieNode rnode = (TrieNode) trie.getRoot();
		tnode.attach(rnode,str);
	}
}
