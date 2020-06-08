import java.util.Comparator;

/**
 * Comparator that compares 2 weighted coding trees
 * 
 * @author Alex Cohen
 */
public class WeightedCodingTreeComparator
    implements Comparator<WeightedCodingTree> {
	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	/**
	 * Compares two weighted coding trees
	 * 
	 * @param tree1
	 *          the first weighted coding tree
	 * @param tree2
	 *          the second weighted coding tree
	 * @return -1 if tree1's weight is less than tree 2's, 1 if tree1's weight is
	 *         greater than tree 2's, and 0 if they have the same weight
	 */
	@Override
	public int compare ( WeightedCodingTree tree1, WeightedCodingTree tree2 ) {
		if ( tree1.getWeight() < tree2.getWeight() ) {
			return -1;
		} else if ( tree1.getWeight() > tree2.getWeight() ) {
			return 1;
		} else if ( tree1.getWeight() == tree2.getWeight() ) {
			return 0;
		}

		return 50;

	}
}
