/**
 * A BitString is a sequence of bits. The sequence can be any length.
 * 
 * @author ssb
 */
public class BitString {

	/**
	 * The bit sequence, as a string of 0s and 1s.
	 */
	private String bits_;

	/**
	 * Create a new bit string of length 0.
	 */
	public BitString () {
		bits_ = "";
	}

	/**
	 * Create a new bit string with the specified bits.
	 * 
	 * @param bits
	 *          string representation of a bit string - every character must be 0
	 *          or 1
	 */
	public BitString ( String bits ) {
		checkString(bits);
		bits_ = bits;
	}

	/**
	 * Create a new bit string corresponding to the specified character. (This is
	 * the binary representation of the character's ASCII code.) The length of the
	 * bit string will be exactly HuffConstants.BITS_PER_CHARACTER.
	 * 
	 * @param ch
	 *          the character
	 */
	public BitString ( Character ch ) {
		bits_ = "";
		for ( int c = (int) ch.charValue(), count =
		    0 ; count < HuffConstants.BITS_PER_CHARACTER ; c = c >> 1, count++ ) {
			bits_ = (c & 1) + bits_;
		}
	}

	/**
	 * Append 'newbits' to this bit string.
	 * 
	 * @param newbits
	 *          string representation of a bit string - every character must be 0
	 *          or 1
	 */
	public void append ( String newbits ) {
		checkString(newbits);
		bits_ += newbits;
	}

	/**
	 * Append a single bit to this bit string.
	 * 
	 * @param bit
	 *          the new bit to append (must be 0 or 1)
	 */
	public void append ( int bit ) {
		if ( bit != 0 && bit != 1 ) {
			throw new IllegalArgumentException("not a legal bit (must be 0 or 1)");
		}
		bits_ += bit;
	}

	/**
	 * Check that 'bits' contains only 0s and 1s. Exits quietly if this is true;
	 * throws an IllegalArgumentException if not.
	 * 
	 * @param bits
	 *          string representation of a bit string - every character must be 0
	 *          or 1
	 * @throws IllegalArgumentException
	 *           if 'bits' contains something other than 0s and 1s
	 */
	private void checkString ( String bits ) {
		for ( int ctr = 0 ; ctr < bits.length() ; ctr++ ) {
			if ( bits.charAt(ctr) != '0' && bits.charAt(ctr) != '1' ) {
				throw new IllegalArgumentException("not a legal bit sequence (can only contain 0s and 1s");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals ( Object obj ) {
		if ( obj == null ) {
			return false;
		} else if ( obj instanceof String ) {
			return bits_.equals((String) obj);
		} else if ( obj instanceof BitString ) {
			return bits_.equals(((BitString) obj).bits_);
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		return bits_;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode () {
		return bits_.hashCode();
	}

	/**
	 * Return the length of the bit sequence.
	 * 
	 * @return the length of the bit sequence
	 */
	public int length () {
		return bits_.length();
	}

	/**
	 * Retrieve the specified bit from the bit string, as an integer value.
	 * 
	 * @param index
	 *          which bit to retrieve (0 <= index < length())
	 * @return the bit at the specified index, as an integer (0 or 1)
	 */
	public int at ( int index ) {
		if ( index < 0 || index >= bits_.length() ) {
			throw new IllegalArgumentException("illegal index " + index
			    + ": must be >= 0 and < " + bits_.length());
		}
		return (bits_.charAt(index) == '0' ? 0 : 1);
	}

	/**
	 * Get the character corresponding to this bit string. This is the character
	 * whose ASCII code, represented in binary, matches this bit string. The bit
	 * string must have length HuffConstants.BITS_PER_CHARACTER.
	 * 
	 * @return the character corresponding to this bit string
	 */
	public Character asCharacter () {
		if ( bits_.length() != HuffConstants.BITS_PER_CHARACTER ) {
			throw new IllegalArgumentException("bit string length " + bits_.length()
			    + "; can only convert to Character if length "
			    + HuffConstants.BITS_PER_CHARACTER);
		}
		int ch = 0;
		for ( int i = 0 ; i < bits_.length() ; i++ ) {
			ch = (ch << 1) | at(i);
		}
		return (char) ch;
	}
}
