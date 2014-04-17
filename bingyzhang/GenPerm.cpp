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
#include <random>

//using namespace std;

#define  PKFILE "EC_PK.txt"
#define  SKFILE "EC_SK.txt"
#define  CFILE "EC_cipher.txt"
#define  RFILE "EC_rand.txt"
#define  PFILE "EC_plain.txt"

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

// It returns a random permutation of 0..n-1
int * Rperm(int n) {
    int *a = new int[n];
    int k;
    std::random_device rd;
    std::mt19937 gen(rd());
    for (k = 0; k < n; k++)
	a[k] = k;
    for (k = n-1; k > 0; k--) {
	std::uniform_int_distribution<int> dis(0, k);
	int j = dis(gen);
	int temp = a[j];
	a[j] = a[k];
	a[k] = temp;
    }
    return a;
}



int main(int argc, char *argv[])
{
    	int iy,i,j, n=2;
	int *plist;
	char *line = new char[512];//buffer needs char instead of const char
    	ofstream fout,rout,pout;
    	ifstream fin;
    	time_t seed;
    	Big tempB,a,b,p,x,y,r,N;
    	ECn g,h,c1,c2,tempE;
    	miracl *mip=&precision;

    	time(&seed);
    	irand((long)seed);   /* change parameter for different values */
    	if(argc >=2){
		n=atoi(argv[1]);
	}
	//default N
	if(argc ==3){
                N = Big(atoi(argv[2]));
        }
	else{
		N = Big(10);
	}

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
    	//Generate random permutation 
	plist = Rperm(n);
	//print plist
	for(i=0;i<n;i++){
		cout<<plist[i];
		if(i<n-1)cout<<",";
		else cout<<endl;
	}


	//encrypt
	fout.open(CFILE);
	rout.open(RFILE);
	pout.open(PFILE);
	for(i = 0;i<n;i++){
		tempB = pow(N,plist[i]);
		pout<< tempB<<endl;
		r=rand(160,2);
		rout<<r<<endl;//output randomness
        	c1 = r*g;
        	c2 = tempB*g;
        	tempE = r*h;
       		c2+=tempE; //avoid temp variables
		iy = c1.get(x);
       		fout<<x<<endl;
        	fout<<iy<<endl;
        	iy = c2.get(x);
        	fout<<x<<endl;
        	fout<<iy<<endl;
	}
	fout.close();
	rout.close();
	pout.close();


        //free buffer
        delete [] line;
	delete [] plist;
	return 0;
}

