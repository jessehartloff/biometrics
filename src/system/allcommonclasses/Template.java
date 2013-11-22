package system.allcommonclasses;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * 
 * A generic template. This is serializable so it can be stored in a database. It is 
 * essentially an ArrayList<BigInteger> so all hashers must conform to this standard. 
 *
 */
public class Template implements Serializable{

	
	private static final long serialVersionUID = 6938186447187369796L;

	public ArrayList<BigInteger> hashes;
	
	public Template(){
		hashes = new ArrayList<BigInteger>();
	}

}
