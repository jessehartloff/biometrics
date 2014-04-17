package system.coordinator.multiserver;

import java.math.BigInteger;

import org.apache.commons.codec.binary.Base64;

public class BingyZhangPoint {
	private BigInteger x;
	private Boolean y;
	
	public BingyZhangPoint(String input){
		this.y =input.charAt(input.length() -1) == '0';
		this.x = new BigInteger(Base64.decodeBase64(input.substring(input.length()- 3)));
	}
	
	public  BigInteger toBigInt(){
	}

}
