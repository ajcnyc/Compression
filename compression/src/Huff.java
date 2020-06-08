import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * Provides methods to compress and uncompress a file
 * 
 * @author Alex Cohen
 */
public class Huff {
	/**
	 * Compresses a file according to the Huffman Coding algorithm
	 * 
	 * @param infile
	 *          the name of the file to be compressed
	 * @param outfile
	 *          the name of the file that will hold the compressed version of
	 *          infile
	 * @throws IOException
	 */
	public static void compress ( String infile, String outfile )
	    throws IOException {
		Reader reader = new FileReader(infile);
		Map<Character,Integer> freqMap = new TreeMap<Character,Integer>();
		PriorityQueue<WeightedCodingTree> pq =
		    new PriorityQueue<WeightedCodingTree>(1,
		                                          new WeightedCodingTreeComparator());
		pq.add(new WeightedCodingTree(1,HuffConstants.PSEUDO_EOF));

		for ( int intChar = reader.read() ; intChar != -1 ; intChar =
		    reader.read() ) {
			char ch = (char) intChar;
			if ( freqMap.keySet().contains(ch) ) {
				freqMap.replace(ch,(freqMap.get(ch) + 1));
			} else {
				freqMap.put(ch,1);
			}
		}
		reader.close();

		WeightedCodingTreeComparator comparator =
		    new WeightedCodingTreeComparator();

		for ( Character ch : freqMap.keySet() ) {
			pq.add(new WeightedCodingTree(freqMap.get(ch),ch));
		}
		// Assemble our final Weighted Coding Tree
		for ( ; pq.size() > 1 ; ) {
			WeightedCodingTree tree1 = pq.poll();
			WeightedCodingTree tree2 = pq.poll();
			WeightedCodingTree mergedTree = new WeightedCodingTree(tree1,tree2);
			pq.add(mergedTree);
		}
		// convert our final Weighted Coding Tree to a Coding Tree
		CodingTree codingTree = pq.poll().getCodingTree();

		Map<Character,BitString> encodingMap = codingTree.getEncodings();
		Reader encodingReader = new FileReader(infile);
		BitOutputStream output = new BitOutputStream(new FileOutputStream(outfile));
		output.writeBits(HuffConstants.MAGIC_NUMBER);
		codingTree.write(output);

		for ( int counter = encodingReader.read() ; counter != -1 ; counter =
		    encodingReader.read() ) {
			System.out.println("counter: " + counter);
			char ch = (char) counter;
			output.writeBits(encodingMap.get(ch));
			System.out.println("cccccharrrrrr :" + ch);
		}

		System.out.println("ps eof: " + HuffConstants.PSEUDO_EOF);
		System.out.println("keyset: " + encodingMap.keySet());
		System.out.println("values: " + encodingMap.values());
		output.writeBits(encodingMap.get(HuffConstants.PSEUDO_EOF));
		encodingReader.close();
		output.flush();
		output.close();
	}

	/**
	 * Uncompresses a file according to the Huffman Coding algorithm
	 * 
	 * @param infile
	 *          the name of the compressed file to be uncompressed
	 * @param outfile
	 *          the name of the file that will hold the uncompressed version of
	 *          infile
	 * @throws IOException
	 */
	public static void uncompress ( String infile, String outfile )
	    throws IOException {
		BitInputStream bitInput = new BitInputStream(new FileInputStream(infile));
		BitString readMagic =
		    bitInput.readBits(HuffConstants.MAGIC_NUMBER.length());

		System.out.println("	 	 readMagic: " + readMagic);
		System.out
		    .println("HuffConstants.MAGIC_NUMBER: " + HuffConstants.MAGIC_NUMBER);
		System.out.println();
		if ( !readMagic.equals(HuffConstants.MAGIC_NUMBER) ) {
			throw new IllegalArgumentException();
		}

		CodingTree remadeTree = new CodingTree(bitInput);
		PrintWriter output = new PrintWriter(new FileWriter(outfile));
		System.out.println("Pseudo EOF: " + HuffConstants.PSEUDO_EOF);
		for ( ; true ; ) {
			System.out.println("bitInput: " + bitInput.toString());
			Character ch = remadeTree.nextChar(bitInput);
			System.out.println("ch: " + ch);
			output.write((int) ch);
			if ( ch == (HuffConstants.PSEUDO_EOF) ) {
				break;
			}

		}
		bitInput.close();
		output.flush();
		output.close();

	}
}
