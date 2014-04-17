/*
 *   Example program demonstrates 1024 bit Diffie-Hellman, El Gamal and RSA
 *   and 168 bit Elliptic Curve Diffie-Hellman 
 *
 *   Requires: big.cpp ecn.cpp
 */

#include <iostream>
#include "ecn.h"
#include "big.h"
#include <ctime>

//using namespace std;

#define MAX 10000



/* NIST p192 bit elliptic curve prime 2#192-2#64-1 */

char *ecp=(char *)"FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF";

/* elliptic curve parameter B */

char *ecb=(char *)"64210519E59C80E70FA7E9AB72243049FEB8DEECC146B9B1";

/* elliptic curve - point of prime order (x,y) */

char *ecx=(char *)"188DA80EB03090F67CBF20EB43A18800F4FF0AFD82FF1012";
char *ecy=(char *)"07192B95FFC8DA78631011ED6B24CDD573F977A11E794811";

char *text=(char *)"Best in the World!\n";

#ifndef MR_NOFULLWIDTH
Miracl precision(50,0);
#else 
Miracl precision(50,MAXBASE);
#endif



// If MR_STATIC is defined in mirdef.h, it is assumed to be 100

//Miracl precision(120,(1<<26));

int main()
{
    int i,ia,ib;
    time_t seed;
    Big m,r,sk,a,b,p,q,n,phi,pa,pb,key,e,d,c,x,y,k,inv,t;
    Big primes[2],pm[2];
    ECn g,h,gm,temp,c1,c2,ea,eb;
    miracl *mip=&precision;

    time(&seed);
    irand((long)seed);   /* change parameter for different values */

    cout << "\nLets try ElGamal using elliptic curves...." << endl;
    a=-3;
    mip->IOBASE=16;
    b=ecb;
    p=ecp;
    ecurve(a,b,p,MR_BEST);  // means use PROJECTIVE if possible, else AFFINE coordinates
    x=ecx;
    y=ecy;
    mip->IOBASE=10;
    g=ECn(x,y);
    ea=eb=g;
	//Generate pk and sk
	sk=rand(160,2);
	h = sk*g;
	 //set a message m 
	m = 10;
	//pick a random r
	r=rand(160,2);
	//encryption -- lifted ElGamal
	c1 = r*g;
	temp = m*g;
	c2 = r*h;
	c2 += temp;
	cout << "Encryption:" << endl;  
	cout << "m = " <<m<< endl;
	c1.get(key);
	mip->IOBASE=64;
	cout << "c1 = " << key << endl;
	c2.get(key);
	cout << "c2 = " <<key<< endl;
	//decryption -- lifted ElGamal
	gm = -sk*c1;
	gm += c2; //no - operation
	//brute force m
	temp = -g;
	for(i=0;i<=MAX;i++){
		temp +=g;
		if(temp == gm){
			break;
		}
	}
	//print message
	cout<< "m = "<<i<<endl;
	

    cout << "Alice's offline calculation" << endl;        
    a=rand(160,2);
    ea*=a;
    ia=ea.get(pa); /* <ia,pa> is compressed form of public key */

    cout << "Bob's offline calculation" << endl;        
    b=rand(160,2);
    eb*=b;
    ib=eb.get(pb); /* <ib,pb> is compressed form of public key */

    cout << "Alice calculates Key=" << endl;
    eb=ECn(pb,ib);  /* decompress eb */
    eb*=a;
    eb.get(key);
    cout << key << endl;

    cout << "Bob calculates Key=" << endl;
    ea=ECn(pa,ia); /* decompress ea */
    ea*=b;
    ea.get(key);
    cout << key << endl;

    cout << "Alice and Bob's keys should be the same! (but much smaller)" << endl;









  
    return 0;
}
 

