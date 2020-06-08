import java.io.IOException;
import java.io.OutputStream;

/**
 * Output stream for writing bits. The stream is buffered, so it is essential
 * that flush() is called to clear the buffer and also pad out the length to a
 * whole number of bytes when writing is complete.
 * 
 * @author ssb
 */
public class BitOutputStream extends OutputStream {

	// number of bits per byte
	private static final int BITS_PER_BYTE = 8;

	// output buffers
	private int outbuffer_ = 0, outbitsleft_ = 0;

	// underlying output stream
	private OutputStream out_;

	/**
	 * Create a BitOutputStream wrapping the specified stream.
	 * 
	 * @param out
	 *          output stream to write to
	 */
	public BitOutputStream ( OutputStream out ) {
		out_ = out;
	}

	/**
	 * Write the specified byte.
	 * 
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write ( int b ) throws IOException {
		out_.write(b);
	}

	/**
	 * Write the specified bit string.
	 * 
	 * @param bits
	 *          bit string to write (bits != null)
	 * @throws IOException
	 *           if there is an error writing
	 */
	public void writeBits ( BitString bits ) throws IOException {
		if ( bits == null ) {
			throw new IllegalArgumentException("cannot write null bit string");
		}

		for ( int ctr = 0 ; ctr < bits.length() ; ctr++ ) {
			// add to buffer if there is room
			if ( outbitsleft_ < BITS_PER_BYTE ) {
				// shift buffer one right, and add in current bit from bit string
				outbuffer_ = (outbuffer_ << 1) | bits.at(ctr);
				outbitsleft_++;
			}
			// write out buffer if it is full
			if ( outbitsleft_ == BITS_PER_BYTE ) {
				out_.write(outbuffer_);
				outbuffer_ = 0;
				outbitsleft_ = 0;
			}
		}

	}

	/**
	 * Flush any unwritten bits to the output stream, and write "padding" bits as
	 * necessary to make the total number of bits written a multiple of one byte.
	 * 
	 * @see java.io.OutputStream#flush()
	 */
	@Override
	public void flush () throws IOException {
		// pad with 0s
		if ( outbitsleft_ > 0 ) {
			int bitstowrite = (outbuffer_ << BITS_PER_BYTE - outbitsleft_);
			System.out.println("flush: " + bitstowrite + " / " + outbitsleft_);
			out_.write(bitstowrite);
			outbuffer_ = 0;
			outbitsleft_ = 0;
		}
		out_.flush();
		super.flush();
	}

}
