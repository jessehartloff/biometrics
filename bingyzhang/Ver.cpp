/*
 *   Example program demonstrates 1024 bit Diffie-Hellman, El Gamal and RSA
 *   and 168 bit Elliptic Curve Diffie-Hellman 
 *
 *   Requires: big.cpp ecn.cpp
 */

#include <fstream>
#include <iostream>
#include "ecn.h"
#include "big.h"
#include <ctime>

//using namespace std;

#define  PKFILE "EC_PK.txt"

/* NIST p192 bit elliptic curve prime 2#192-2#64-1 */

char *ecp=(char *)"FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF";

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

// If MR_STATIC is defined in mirdef.h, it is assumed to be 100

//Miracl precision(120,(1<<26));




int main(int argc, char *argv[])
{
    	int iy,i,j;
    	ifstream fin;
    	time_t seed;
    	Big a,b,p,x,y,r,m;
    	ECn g,h,c1,c2,tempE, tempC;
    	miracl *mip=&precision;
	char *line = new char[512];//buffer needs char instead of const char
    	time(&seed);
    	irand((long)seed);   /* change parameter for different values */

    	a=-3;
    	mip->IOBASE=16;
    	b=ecb;
    	p=ecp;
    	ecurve(a,b,p,MR_BEST);  // means use PROJECTIVE if possible, else AFFINE coordinates
    	x=ecx;
    	y=ecy;
    	g=ECn(x,y);

    	//Read PK
    	mip->IOBASE=64;
    	fin.open(PKFILE);
    	fin.getline(line,512);
        x = line;
        fin.getline(line,512);
        iy = atoi(line);
    	fin.close();
	h = ECn(x,iy); //decompress pk


        if(argc ==7){
                m = argv[1];
		r = argv[2];
		x = argv[3];
		iy = atoi(argv[4]);
		c1 = ECn(x,iy);
		x = argv[5];
                iy = atoi(argv[6]);
                c2 = ECn(x,iy);
        }
        else{
		cout<<"Wrong arguments."<<endl;
		//free buffer
        	delete [] line;
                return 0;
        }


	//check c1
	if (c1 != r*g){
		cout<<"0"<<endl;
		//free buffer
        	delete [] line;
		return 0;
	}
	//check c2
	tempC = m*g;
	tempE = r*h;
	tempC +=tempE; 
	if(c2 != tempC){
		cout<<"0"<<endl;
        	//free buffer
        	delete [] line;
                return 0;
	}
	//verified
	cout<<"1"<<endl;
	//free buffer
        delete [] line;
	return 0;
}

