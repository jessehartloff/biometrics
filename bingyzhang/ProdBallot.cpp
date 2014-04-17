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
#define  SKFILE "EC_SK.txt"
#define  CFILE "EC_cipher.txt"
#define  TFILE "EC_sum.txt"
#define  PFILE "EC_plain.txt"
#define  BFILE "Ballot.txt"
#define  EBFILE "EC_ballot.txt"

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
    	int m,iy,i,j, n = 2, l = 2;
	char *line = new char[512];//buffer needs char instead of const char
    	ofstream fout;
    	ifstream fin;
    	time_t seed;
    	Big tempB,a,b,p,x,y;
    	ECn g,s1,s2;
    	miracl *mip=&precision;

    	time(&seed);
    	irand((long)seed);   /* change parameter for different values */
	if(argc ==3){
                n=atoi(argv[1]);
		l=atoi(argv[2]);
        }
	//we need to read all the ciphertexts in
	ECn *c1 = new ECn[n];
	ECn *c2 = new ECn[n];
	cout << "Producing EC-ElGamal Ballots...." << endl;
    	a=-3;
    	mip->IOBASE=16;
    	b=ecb;
    	p=ecp;
    	ecurve(a,b,p,MR_BEST);  // means use PROJECTIVE if possible, else AFFINE coordinates
    	x=ecx;
    	y=ecy;
    	g=ECn(x,y);
	
	//Read cipher and ballot transpose
	mip->IOBASE=64;
        fin.open(CFILE);
        for(i = 0;i<n;i++){
                fin.getline(line,512);
                x = line;
                fin.getline(line,512);
                iy = atoi(line);
                c1[i] = ECn(x,iy); //decompress
                fin.getline(line,512);
                x = line;
                fin.getline(line,512);
                iy = atoi(line);
                c2[i] = ECn(x,iy); //decompress
        }
	fin.close();

	fin.open(BFILE);
	fout.open(EBFILE);
    for(j=0;j<l;j++){
	for(i = 0;i<n;i++){
		fin>>m;
		if(i == 0){
			s1 = m*c1[i];
			s2 = m*c2[i];
		}
		else{
			s1 += m*c1[i];
                        s2 += m*c2[i];
		}
	}
	//write
	iy = s1.get(x);
        fout<<x<<endl<<iy<<endl;
        iy = s2.get(x);
        fout<<x<<endl<<iy<<endl;
    }
        fin.close();
	fout.close();



	//free buffer
	delete [] line;
	delete [] c1;
	delete [] c2;
	return 0;
}

