import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Enables users to compress files, uncompress files, and conduct a bitwise
 * comparison on two files
 * 
 * @author Alex Cohen
 */
public class HuffMain {

	/**
	 * @param args
	 */
	public static void main ( String[] args ) {
		Scanner scanner = new Scanner(System.in);
		for ( boolean proceed = true ; proceed == true ; ) {
			System.out.println("(1) compress file");
			System.out.println("(2) uncompress file");
			System.out.println("(3) compare files");
			System.out.println("(4) quit");
			System.out.print("> ");
			int choice = scanner.nextInt();
			if ( choice == 1 ) {
				System.out.println("1");
				System.out.print("file to compress: ");
				String inFile = scanner.next();
				System.out.print("file save in: ");
				String outFile = scanner.next();
				try {
					Huff.compress(inFile,outFile);
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			} else if ( choice == 2 ) {
				System.out.println("2");
				System.out.print("file to uncompress: ");
				String inFile = scanner.next();
				System.out.print("file save in: ");
				String outFile = scanner.next();
				try {
					Huff.uncompress(inFile,outFile);
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			} else if ( choice == 3 ) {
				System.out.println("3");
				System.out.print("file #1: ");
				String inFile = scanner.next();
				System.out.print("file #2: ");
				String outFile = scanner.next();
				try {
					System.out.println("Files are the same?: "
					    + FileDiff.compare(inFile,outFile));
					;
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			} else if ( choice == 4 ) {
				System.out.println("4");
				proceed = false;
			}
		}
	}

}
