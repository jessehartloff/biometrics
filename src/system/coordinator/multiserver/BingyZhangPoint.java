package system.coordinator.multiserver;

import java.math.BigInteger;

import org.apache.commons.codec.binary.Base64;

public class BingyZhangPoint {
	
	private BigInteger x;
	private Boolean y;

	public BingyZhangPoint(){}
	
	public BingyZhangPoint(String input){
		this.fromEncodedString(input);
	}
	
	public BingyZhangPoint(BigInteger input){
		this.fromBigInt(input);
	}
	
	public String toEncodedString(){
		String toReturn = new String(Base64.encodeBase64(this.x.toByteArray()));
		toReturn += this.y ? " 1" : " 0";
		return toReturn;
	}
	
	public void fromEncodedString(String input){
//		System.out.println(input);
		if(input.equals(" 0")){
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			this.y = false;
			this.x = BigInteger.ZERO;
		}else{
			this.y = input.charAt(input.length()-1) == '1';
			this.x = new BigInteger(Base64.decodeBase64(input.substring(0, input.length() - 2)));
		}
	}
	
	
	public  BigInteger toBigInt(){
		BigInteger toReturn = this.x.shiftLeft(1);
		if(this.y){
			toReturn = toReturn.add(BigInteger.ONE);
		}
		return toReturn;
	}

	public void fromBigInt(BigInteger encoded){
		this.y = encoded.and(BigInteger.ONE).equals(BigInteger.ONE);
		this.x = encoded.shiftRight(1);
	}

	
	public BigInteger getX() {
		return x;
	}

	public void setX(BigInteger x) {
		this.x = x;
	}

	public Boolean getY() {
		return y;
	}

	public void setY(Boolean y) {
		this.y = y;
	}
	
}
