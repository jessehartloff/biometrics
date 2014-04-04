package system.allcommonclasses.commonstructures;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashSet;

/**
 * 
 * A generic template. This is serializable so it can be stored in a database. It is 
 * essentially an ArrayList<BigInteger> so all hashers must conform to this standard. 
 *
 */
public class Template implements Serializable{

	
	private static final long serialVersionUID = 6938186447187369796L;

	private HashSet<BigInteger> hashes;
	private BigInteger extraHash;
	
	public Template(){
		hashes = new HashSet<BigInteger>();
	}

	@Override
	public String toString() {
		return hashes.toString();
	}

	public HashSet<BigInteger> getHashes() {
		return hashes;
	}

	public void setHashes(HashSet<BigInteger> hashes) {
		this.hashes = hashes;
	}

	public BigInteger getExtraHash() {
		return extraHash;
	}

	public void setExtraHash(BigInteger extraHash) {
		this.extraHash = extraHash;
	}

	
	
}
