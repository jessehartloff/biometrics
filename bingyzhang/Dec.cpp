/*
 *   Commutative Encryption Algorithm Dec(d,c) = c^{d}
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

/* elliptic curve parameter B */

char *ecb=(char *)"64210519E59C80E70FA7E9AB72243049FEB8DEECC146B9B1";

#ifndef MR_NOFULLWIDTH
Miracl precision(50,0);
#else
Miracl precision(50,MAXBASE);
#endif


int main(int argc, char *argv[])
{
    int iy;
    Big a,b,p,x,y,d;
    ECn c,c2;
    miracl *mip=&precision;
    
    //init curve
    a=-3;
    mip->IOBASE=16;
    b=ecb;
    p=ecp;
    ecurve(a,b,p,MR_BEST);  // means use PROJECTIVE if possible, else AFFINE coordinates
    
    
    //obtain input
    mip->IOBASE=64; //base 64 encoding
    if(argc ==4){
        d = argv[1];
        x = argv[2];
        iy = atoi(argv[3]);
        c = ECn(x,iy); //decompress
    }
    else{
        cout<< "Usage: ./Dec [key] [cipher_x] [cipher_y]"<<endl;
        return 0;
    }
    
    //encrypt
    c2 = d*c;
    iy=c2.get(x); //<x,y> is compressed form of c
    cout<<x<<" "<<iy<<endl;
    
    
    return 0;
}

