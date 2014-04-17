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



/* NIST p192 bit elliptic curve prime 2#192-2#64-1 */

char *ecp=(char *)"FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF";

/* elliptic curve parameter B */

char *ecq=(char *)"FFFFFFFFFFFFFFFFFFFFFFFF99DEF836146BC9B1B4D22831";

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
    	int iy,i;
	char *line = new char[512];//buffer needs char instead of const char
    	ofstream fout;
    	ifstream fin;
	char *cipher_file;
    	time_t seed;
    	Big tempB,a,b,p,x,y;
    	ECn g,c1,c2,s1,s2;
    	miracl *mip=&precision;

    	time(&seed);
    	irand((long)seed);   /* change parameter for different values */

        if(argc ==2){
                cipher_file=argv[1];
        }
        else{
                cipher_file=(char *)CFILE;
        }	

	//cout << "Adding EC-ElGamal ciphertexts...." << endl;
    	a=-3;
    	mip->IOBASE=16;
    	b=ecb;
    	p=ecp;
    	ecurve(a,b,p,MR_BEST);  // means use PROJECTIVE if possible, else AFFINE coordinates
    	x=ecx;
    	y=ecy;
    	g=ECn(x,y);
	
	//Read cipher and multiply
	i = 0;
	mip->IOBASE=64;
        fin.open(cipher_file);
	while(fin.getline(line,512)){
	        x = line;
	        fin.getline(line,512);
        	iy = atoi(line);
       		c1 = ECn(x,iy); //decompress
        	fin.getline(line,512);
        	x = line;
        	fin.getline(line,512);
        	iy = atoi(line);
        	c2 = ECn(x,iy); //decompress
	        //add
		if(i==0){
			s1 = c1;
			s2 = c2;
		}
		else{
			s1 += c1;
                        s2 += c2;

		}
		i++;
	}
        fin.close();



	fout.open(TFILE);
	iy = s1.get(x);
	fout<<x<<endl<<iy<<endl;
	iy = s2.get(x);
        fout<<x<<endl<<iy<<endl;
        fout.close();

	//free buffer
	delete [] line;
	return 0;
}

