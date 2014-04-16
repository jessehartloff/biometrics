/*
 *   Commutative Encryption Algorithm Enc(e,m) = g^{m*e}
 *   By: Bingsheng Zhang (bzhang26@di.uoa.gr)
 *
 *   Requires: big.cpp ecn.cpp
 */

#include <fstream>
#include <iostream>
#include "ecn.h"
#include "big.h"
#include <ctime>

//using namespace std;

/* NIST p192 bit elliptic curve prime 2#192-2#64-1 */

char *ecp=(char *)"FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF";

/* group order q */

char *ecq=(char *)"FFFFFFFFFFFFFFFFFFFFFFFF99DEF836146BC9B1B4D22831";

/* elliptic curve parameter B */

char *ecb=(char *)"64210519E59C80E70FA7E9AB72243049FEB8DEECC146B9B1";

/* elliptic curve - point of prime order (x,y) */

char *ecx=(char *)"188DA80EB03090F67CBF20EB43A18800F4FF0AFD82FF1012";
char *ecy=(char *)"07192B95FFC8DA78631011ED6B24CDD573F977A11E794811";

#ifndef MR_NOFULLWIDTH
Miracl precision(50,0);
#else 
Miracl precision(50,MAXBASE);
#endif


int main(int argc, char *argv[])
{
    int iy;
    Big a,b,p,q,x,y,e,m;
    ECn g,c;
    miracl *mip=&precision;
    
    //init curve and g
    a=-3;
    mip->IOBASE=16;
    b=ecb;
    p=ecp;
    q=ecq;
    ecurve(a,b,p,MR_BEST);  // means use PROJECTIVE if possible, else AFFINE coordinates
    x=ecx;
    y=ecy;
    g=ECn(x,y);
    
    //obtain input
    mip->IOBASE=64; //base 64 encoding
	e = argv[1];
	for(int i = 2; i < argc; i++){

		m = argv[i];
		/*//old bingsheng code
		if(argc ==3){
			e = argv[1];
			m = argv[2];
		}
		else{
			cout<< "Usage: ./Enc [key] [message]"<<endl;
			return 0;
		}*/
			
		//encrypt
		e=modmult(m,e,q);
		c = e*g;
		iy=c.get(x); //<x,y> is compressed form of c
		cout<<x<<" "<<iy<<endl;

		//output g^m for debug
		//c = m*g;
		//iy=c.get(x); //<x,y> is compressed form of c
		//cout<<x<<" "<<iy<<endl;
	}
    
    return 0;
}

