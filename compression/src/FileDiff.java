import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.ProgressMonitorInputStream;

/**
 * Compare two files bit-by-bit.
 * 
 * @author ssb
 */
public class FileDiff {

	/**
	 * Compare two files bit-by-bit.
	 * 
	 * @param file1
	 *          name of first file
	 * @param file2
	 *          name of second file
	 * @return true if the files are identical, false if not
	 * @throws FileNotFoundException
	 *           if either file cannot be opened
	 * @throws IOException
	 *           if an error occurs while reading either file
	 */
	public static boolean compare ( String file1, String file2 )
	    throws FileNotFoundException, IOException {
		InputStream in1 =
		    new ProgressMonitorInputStream(null,"reading " + file1,
		                                   new FileInputStream(file1));
		ArrayList<Integer> bytes1 = new ArrayList<Integer>();
		for ( ; true ; ) {
			int input = in1.read();
			if ( input == -1 ) {
				break;
			}
			bytes1.add(input);
		}
		in1.close();

		InputStream in2 =
		    new ProgressMonitorInputStream(null,"reading " + file2,
		                                   new FileInputStream(file2));
		ArrayList<Integer> bytes2 = new ArrayList<Integer>();
		for ( ; true ; ) {
			int input = in2.read();
			if ( input == -1 ) {
				break;
			}
			bytes2.add(input);
		}
		in2.close();

		System.out.println(bytes1.size() + " " + bytes2.size());
		if ( bytes1.size() != bytes2.size() ) {
			System.out.println("last byte: " + bytes1.get(bytes1.size() - 1) + " "
			    + bytes2.get(bytes2.size() - 1));
			return false;
		}

		for ( int ctr = 0 ; ctr < bytes1.size() ; ctr++ ) {
			if ( bytes1.get(ctr) != bytes2.get(ctr) ) {
				System.out
				    .println(ctr + ": " + bytes1.get(ctr) + " " + bytes2.get(ctr));
				return false;
			}
		}

		return true;
	}

}
