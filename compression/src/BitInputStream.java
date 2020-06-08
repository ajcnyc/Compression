import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Input stream for reading bits.
 * 
 * @author ssb
 */
public class BitInputStream extends InputStream {

	// number of bits per byte
	private static final int BITS_PER_BYTE = 8;

	// input buffers
	private int inbuffer_ = 0, inbitsleft_ = 0;

	// has EOF been encountered?
	private boolean ineof_ = false;

	// underlying input stream
	private InputStream in_;

	/**
	 * Create a BitInputStream wrapping the specified stream.
	 * 
	 * @param in
	 *          input stream to read from
	 */
	public BitInputStream ( InputStream in ) {
		in_ = in;
	}

	/**
	 * Read one byte.
	 * 
	 * @see java.io.InputStream#read()
	 * @return byte read, or -1 if EOF was encountered
	 */
	@Override
	public int read () throws IOException {
		// fill buffer as needed, then return that byte
		int result = 0;

		// read from the buffer, refilling it as needed
		for ( int numread = 0 ; numread < BITS_PER_BYTE ; ) {
			if ( inbitsleft_ == 0 ) { // refill buffer
				inbuffer_ = in_.read(); // reads one BYTE
				if ( inbuffer_ == -1 ) {
					ineof_ = true;
					return -1;
				}
				inbitsleft_ = BITS_PER_BYTE;
			}

			// read from buffer, right to left
			for ( ; inbitsleft_ > 0
			    && numread < BITS_PER_BYTE ; inbitsleft_--, numread++ ) {
				result = (result << 1);
				result |= ((inbuffer_ >> (inbitsleft_ - 1)) & 1);
			}
		}

		return result;
	}

	/**
	 * Read the specified number of bits, returning the resulting bit string.
	 * 
	 * @param numbits
	 *          number of bits to read (numbits >= 0)
	 * @return the bits read, as a bit string; returns null if EOF is encountered
	 *         while reading bits
	 * @throws IOException
	 *           if there is an error reading
	 * @throws EOFException
	 *           if EOF has been encountered on a previous call to getBits()
	 */
	public BitString readBits ( int numbits ) throws IOException {
		if ( numbits < 0 ) {
			throw new IllegalArgumentException("number of bits to read must be >= 0");
		}
		if ( ineof_ ) {
			throw new EOFException("attempt to read past end-of-file");
		}

		BitString bits = new BitString(); // bits read in so far

		// read from the buffer, refilling it as needed
		for ( int numread = 0 ; numread < numbits ; ) {
			if ( inbitsleft_ == 0 ) { // refill buffer
				inbuffer_ = in_.read(); // reads one BYTE
				if ( inbuffer_ == -1 ) {
					ineof_ = true;
					return null;
				}
				inbitsleft_ = BITS_PER_BYTE;
			}

			// read from buffer, right to left
			for ( ; inbitsleft_ > 0
			    && numread < numbits ; inbitsleft_--, numread++ ) {
				bits.append(((inbuffer_ >> (inbitsleft_ - 1)) & 1) == 0 ? "0" : "1");
			}
		}

		return bits;
	}

	/**
	 * Return true if the end-of-file (EOF) has been encountered during a previous
	 * call to getBits(). Note: this returns false if the last call to getBits()
	 * read up to and including the last bit in the stream, but did not attempt to
	 * read further - eof() only returns true if there has been an attempt to read
	 * past the end of the file.
	 * 
	 * @return true if end-of-file has been reached, false otherwise
	 */
	public boolean eof () {
		return ineof_;
	}

}
