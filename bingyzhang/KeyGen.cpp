/*
 *   Commutative Encryption Key Generation Algorithm Gen(1^k) --> e,d
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
/* group order q */

char *ecq=(char *)"FFFFFFFFFFFFFFFFFFFFFFFF99DEF836146BC9B1B4D22831";



#ifndef MR_NOFULLWIDTH
Miracl precision(50,0);
#else
Miracl precision(50,MAXBASE);
#endif


int main(int argc, char *argv[])
{
    Big q,e,d;
    miracl *mip=&precision;
    time_t seed;
    
    time(&seed);
    irand((long)seed);   /* change parameter for different values */
    
    //init
    mip->IOBASE=16;
    q = ecq;
    
    
    //Key Gen
    e=rand(160,2);
    d = inverse(e,q);
    mip->IOBASE=64;
    cout<<"e: "<< e<<endl<<"d: "<<d<<endl;
    
    //verification
    //mip->IOBASE=2;
    //e=modmult(e,d,q);
    //cout<<e<<endl;
    
    return 0;
}

