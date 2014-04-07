package system.hasher.fuzzyvault;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CRCPolyMap {

	public static int getPolyInteger(int totalBits){
		ArrayList<BigInteger> bigIntPoly = CRCPolyMap.getCRCPoly(totalBits);
		
		if(totalBits != bigIntPoly.size()){
			System.out.println("Something went wrong");
			return 0;
		}
		
		BigInteger bigOne = BigInteger.ONE;
		
		int n = bigIntPoly.size();
		int intPoly = 0;
		
		for(int i=n-1; i>=0; i++){
			intPoly <<= 1;
			if(bigIntPoly.get(i).equals(bigOne)){
				intPoly++;
			}
		}
		
		return intPoly;
	}
	
	public static ArrayList<BigInteger> getCRCPoly(int totalBits){
		return CRCPolyMap.crcMap.get(totalBits-1);
	}
	static BigInteger[] crc1  = { BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc2  = { BigInteger.ONE, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc3  = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc4  = { BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc5  = { BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc6  = { BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc7  = { BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc8  = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc9  = { BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc10 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc11 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc12 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc13 = { BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc14 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc15 = { BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc16 = { BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc17 = { BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc18 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc19 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc20 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc21 = { BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc22 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc23 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc24 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc25 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc26 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc27 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc28 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc29 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc30 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc31 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc32 = { BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc33 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc34 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc35 = { BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc36 = { BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc37 = { BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc38 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc39 = { BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc40 = { BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc41 = { BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc42 = { BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc43 = { BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc44 = { BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc45 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc46 = { BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE };
	static BigInteger[] crc47 = { BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc48 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE };
	static BigInteger[] crc49 = { BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE };
	private static HashMap<Integer, ArrayList<BigInteger>> crcMap;
	static{
		crcMap = new HashMap<Integer, ArrayList<BigInteger>>();
		crcMap.put(1, new ArrayList<BigInteger>(Arrays.asList(crc1)));
		crcMap.put(2, new ArrayList<BigInteger>(Arrays.asList(crc2)));
		crcMap.put(3, new ArrayList<BigInteger>(Arrays.asList(crc3)));
		crcMap.put(4, new ArrayList<BigInteger>(Arrays.asList(crc4)));
		crcMap.put(5, new ArrayList<BigInteger>(Arrays.asList(crc5)));
		crcMap.put(6, new ArrayList<BigInteger>(Arrays.asList(crc6)));
		crcMap.put(7, new ArrayList<BigInteger>(Arrays.asList(crc7)));
		crcMap.put(8, new ArrayList<BigInteger>(Arrays.asList(crc8)));
		crcMap.put(9, new ArrayList<BigInteger>(Arrays.asList(crc9)));
		crcMap.put(10, new ArrayList<BigInteger>(Arrays.asList(crc10)));
		crcMap.put(11, new ArrayList<BigInteger>(Arrays.asList(crc11)));
		crcMap.put(12, new ArrayList<BigInteger>(Arrays.asList(crc12)));
		crcMap.put(13, new ArrayList<BigInteger>(Arrays.asList(crc13)));
		crcMap.put(14, new ArrayList<BigInteger>(Arrays.asList(crc14)));
		crcMap.put(15, new ArrayList<BigInteger>(Arrays.asList(crc15)));
		crcMap.put(16, new ArrayList<BigInteger>(Arrays.asList(crc16)));
		crcMap.put(17, new ArrayList<BigInteger>(Arrays.asList(crc17)));
		crcMap.put(18, new ArrayList<BigInteger>(Arrays.asList(crc18)));
		crcMap.put(19, new ArrayList<BigInteger>(Arrays.asList(crc19)));
		crcMap.put(20, new ArrayList<BigInteger>(Arrays.asList(crc20)));
		crcMap.put(21, new ArrayList<BigInteger>(Arrays.asList(crc21)));
		crcMap.put(22, new ArrayList<BigInteger>(Arrays.asList(crc22)));
		crcMap.put(23, new ArrayList<BigInteger>(Arrays.asList(crc23)));
		crcMap.put(24, new ArrayList<BigInteger>(Arrays.asList(crc24)));
		crcMap.put(25, new ArrayList<BigInteger>(Arrays.asList(crc25)));
		crcMap.put(26, new ArrayList<BigInteger>(Arrays.asList(crc26)));
		crcMap.put(27, new ArrayList<BigInteger>(Arrays.asList(crc27)));
		crcMap.put(28, new ArrayList<BigInteger>(Arrays.asList(crc28)));
		crcMap.put(29, new ArrayList<BigInteger>(Arrays.asList(crc29)));
		crcMap.put(30, new ArrayList<BigInteger>(Arrays.asList(crc30)));
		crcMap.put(31, new ArrayList<BigInteger>(Arrays.asList(crc31)));
		crcMap.put(32, new ArrayList<BigInteger>(Arrays.asList(crc32)));
		crcMap.put(33, new ArrayList<BigInteger>(Arrays.asList(crc33)));
		crcMap.put(34, new ArrayList<BigInteger>(Arrays.asList(crc34)));
		crcMap.put(35, new ArrayList<BigInteger>(Arrays.asList(crc35)));
		crcMap.put(36, new ArrayList<BigInteger>(Arrays.asList(crc36)));
		crcMap.put(37, new ArrayList<BigInteger>(Arrays.asList(crc37)));
		crcMap.put(38, new ArrayList<BigInteger>(Arrays.asList(crc38)));
		crcMap.put(39, new ArrayList<BigInteger>(Arrays.asList(crc39)));
		crcMap.put(40, new ArrayList<BigInteger>(Arrays.asList(crc40)));
	}


}
