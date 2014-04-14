package system.coordinator.multiserver;

import java.math.BigInteger;
import java.util.ArrayList;

import org.bouncycastle.math.ec.ECPoint;

public class MyECPoint {

	public BigInteger x;
	public BigInteger y;
	public BigInteger bigTwo = BigInteger.valueOf(2L);
	public BigInteger bigThree = BigInteger.valueOf(3L);
	
	BigInteger p = new BigInteger("6277101735386680763835789423207666416083908700390324961279");
	BigInteger a = new BigInteger("6277101735386680763835789423207666416083908700390324961276");
	
	public MyECPoint(){
	}
	
	public MyECPoint(BigInteger x, BigInteger y){
		this.x = x;
		this.y = y;
	}
	
	public MyECPoint(BigInteger x, BigInteger y, BigInteger p, BigInteger a){
		this.x = x;
		this.y = y;
		this.p = p;
		this.a = a;
	}
	
	public MyECPoint(ECPoint point){
		this.x = point.getXCoord().toBigInteger();
		this.y = point.getYCoord().toBigInteger();
	}
	
	public MyECPoint(ECPoint point, BigInteger p, BigInteger a){
		this.x = point.getXCoord().toBigInteger();
		this.y = point.getYCoord().toBigInteger();
		this.p = p;
		this.a = a;
	}
	
	public MyECPoint(MyECPoint point){
		this.x = point.x;
		this.y = point.y;
		this.p = point.p;
		this.a = point.a;
	}
	
	public MyECPoint myAdd(MyECPoint otherPoint){
		MyECPoint toReturn = new MyECPoint();
		toReturn.p = this.p;
		toReturn.a = this.a;

		BigInteger lambda = (this.y.add(otherPoint.y.negate()))
				.multiply(((this.x.add(otherPoint.x.negate())).mod(this.p)).modInverse(this.p))  .mod(this.p);
		toReturn.x = (lambda.pow(2).mod(this.p).add(this.x.negate()).add(otherPoint.x.negate())).mod(this.p);
		toReturn.y = (this.y.negate().add(lambda.multiply(this.x.add(toReturn.x.negate())))).mod(p);
		
		return toReturn;
	}

	public MyECPoint myTwice() {
		MyECPoint toReturn = new MyECPoint();
		toReturn.p = this.p;
		toReturn.a = this.a;
//		System.out.println(((this.x.add(otherPoint.x.negate())).mod(this.p)));
		BigInteger lambda = ((this.x.modPow(this.bigTwo, this.p).multiply(this.bigThree).mod(this.p)).add(this.a))
				.multiply(  (this.y.multiply(this.bigTwo)).mod(this.p).modInverse(this.p))  .mod(this.p);
		toReturn.x = (lambda.modPow(this.bigTwo, this.p).add(this.x.negate().multiply(this.bigTwo))).mod(this.p);
		toReturn.y = (this.y.negate().add(lambda.multiply(this.x.add(toReturn.x.negate())))).mod(p);
		
		return toReturn;
	}
	
	public MyECPoint multiply(BigInteger multiplyBy){
		MyECPoint toReturn = new MyECPoint();
		toReturn.x = BigInteger.valueOf(-1L);
		toReturn.p = this.p;
		toReturn.a = this.a;
		
		MyECPoint currentPoint = new MyECPoint(this);
		
		BigInteger currentBit = BigInteger.ONE;
		for(int i=0; i<multiplyBy.bitLength(); i++){
			if(multiplyBy.and(currentBit).equals(currentBit)){
				if(toReturn.x.equals(BigInteger.valueOf(-1L))){
					toReturn.x = currentPoint.x;
					toReturn.y = currentPoint.y;
				}else{
					toReturn = toReturn.add(currentPoint);
				}
			}
			currentPoint = currentPoint.twice();
			currentBit = currentBit.shiftLeft(1);
		}
		return toReturn;
	}

	
	public MyECPoint multiplyALittleFaster(BigInteger multiplyBy){
		MyECPoint Q = new MyECPoint();
		Q.p = this.p;
		Q.a = this.a;
		
		boolean notYet = true;
		BigInteger anotherTimes = multiplyBy.add(BigInteger.ZERO);
		int w = 4;
		
		BigInteger twoToW = this.bigTwo.pow(w);
		
		MyECPoint[] preComputed = new MyECPoint[twoToW.intValue()];

		preComputed[1] = new MyECPoint(this);
		preComputed[2] = preComputed[1].twice();
		
		for(int i=3; i<twoToW.intValue(); i++){
			preComputed[i] = preComputed[i-1].add(this);
		}

		MyECPoint currentPoint = new MyECPoint(this);
		
		BigInteger range = this.bigTwo.pow(w).add(BigInteger.valueOf(-1L));
		
		while(!anotherTimes.equals(BigInteger.ZERO)){
			BigInteger di = anotherTimes.and(range);
			if(!notYet){
				for(int y=0; y<w; y++){
					Q = Q.twice();
				}
			}
			if(!di.equals(BigInteger.ZERO)){
				if(notYet){
					Q = preComputed[di.intValue()];
				}else{
					Q = Q.add(preComputed[di.intValue()]);
				}
			}
			
			for(int y=0; y<w; y++){
				currentPoint = currentPoint.twice();
			}
			anotherTimes = anotherTimes.shiftRight(w);
		}
		return Q;
		
			
	}
	

	public MyECPoint multiplyFaster(BigInteger multiplyBy){
		MyECPoint Q = new MyECPoint();
		Q.p = this.p;
		Q.a = this.a;
		
		boolean notYet = true;
		
		int w = 4;
		BigInteger twoToW = this.bigTwo.pow(w);
		
		BigInteger d = multiplyBy.add(BigInteger.ZERO);
		
		ArrayList<BigInteger> di= new ArrayList<BigInteger>();
		int i=0;
		while(d.compareTo(BigInteger.ZERO) > 0){
			if(d.mod(this.bigTwo).equals(BigInteger.ONE)){
				if(d.and(twoToW.shiftRight(1)).equals(twoToW.shiftRight(1))){
					di.add(twoToW.add(twoToW.shiftRight(1).negate()));
				}else{
					di.add(d.mod(twoToW));
				}			
				d = d.add(di.get(i).negate());
			}else{
				di.add(BigInteger.ZERO);
			}
			d = d.shiftRight(1);
			i++;
		}
		
		ArrayList<MyECPoint> preComputed = new ArrayList<MyECPoint>();
		
		for(int u=1; u < twoToW.shiftRight(1).intValue(); u=i+2){
			MyECPoint point = this.multiply(BigInteger.valueOf(u)); 
			MyECPoint pointNegated = new MyECPoint(point);
			pointNegated.y = pointNegated.y.negate().mod(this.p);
			preComputed.add(pointNegated);
			preComputed.add(point);
		}
		
		for(int j=i-1; j>=0; j--){
			if(!notYet){
				Q = Q.twice();
			}
			if(!di.get(j).equals(BigInteger.ZERO)){
//				Q = Q.add(G.multiply(di.get(j));
			}
		}
		
		
		return Q;
			
	}
	

	

    /**
     * D.3.2 pg 101
     * @see org.bouncycastle.math.ec.ECMultiplier#multiply(org.bouncycastle.math.ec.ECPoint, java.math.BigInteger)
     */
    public MyECPoint multiplyBouncy(BigInteger k)
    {
    	MyECPoint p = new MyECPoint(this);
        // TODO Probably should try to add this
        // BigInteger e = k.mod(n); // n == order of p
        BigInteger e = k;
        BigInteger h = e.multiply(BigInteger.valueOf(3));

        MyECPoint neg = p.negate();
        MyECPoint R = p;

        for (int i = h.bitLength() - 2; i > 0; --i)
        {             
            R = R.twice();

            boolean hBit = h.testBit(i);
            boolean eBit = e.testBit(i);

            if (hBit != eBit)
            {
                R = R.add(hBit ? p : neg);
            }
        }

        return R;
    }

    private MyECPoint negate() {
    	MyECPoint negated = new MyECPoint(this);
    	negated.y = negated.y.negate().mod(this.p);
    	return negated;
    }



	
	public MyECPoint add(MyECPoint b)
	{
//		if (this.isInfinity())
//		{
//			return b;
//		}
//
//		if (b.isInfinity())
//		{
//			return this;
//		}

		// Check if b = this or b = -this
				if (this.x.equals(b.x))
				{
					if (this.y.equals(b.y))
					{
						// this = b, i.e. this must be doubled
						return this.twice();
					}

					// this = -b, i.e. the result is the point at infinity
//					return this.curve.getInfinity();
				}

				BigInteger gamma = b.y.subtract(this.y).multiply(b.x.subtract(this.x).modInverse(this.p));
//				BigInteger gamma = b.y.subtract(this.y).divide(b.x.subtract(this.x));

				BigInteger x3 = gamma.pow(2).subtract(this.x).subtract(b.x).mod(this.p);
				BigInteger y3 = gamma.multiply(this.x.subtract(x3)).subtract(this.y).mod(this.p);

				return new MyECPoint(x3, y3);
	}

	// B.3 pg 62
	public MyECPoint twice()
	{
//		if (this.isInfinity())
//		{
//			// Twice identity element (point at infinity) is identity
//			return this;
//		}
//
//		if (this.y.toBigInteger().signum() == 0) 
//		{
//			// if y1 == 0, then (x1, y1) == (x1, -y1)
//			// and hence this = -this and thus 2(x1, y1) == infinity
//			return this.curve.getInfinity();
//		}

//		ECFieldElement TWO = this.curve.fromBigInteger(BigInteger.valueOf(2));
//		ECFieldElement THREE = this.curve.fromBigInteger(BigInteger.valueOf(3));
		BigInteger gamma = this.x.pow(2).multiply(bigThree).add(this.a).multiply(y.multiply(bigTwo).modInverse(this.p));
//System.out.println("p is: " + this.p);
		BigInteger x3 = gamma.pow(2).subtract(this.x.multiply(bigTwo)).mod(this.p);
		BigInteger y3 = gamma.multiply(this.x.subtract(x3)).subtract(this.y).mod(this.p);

		return new MyECPoint(x3, y3);
	}
	
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyECPoint other = (MyECPoint) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	
}
