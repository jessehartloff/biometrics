Commutative Encryption
==========

Commutative encryption based on MIRACL

To compile, run command

bash linux64

Usage:

a. Key generation

./KeyGen

It outputs e,d such that ed = 1.

b. Encryption

./Enc [key] [message]

Both key and message should use base64 encoding.

It computes c = g^{m*e}, and c is represented by (x,y).

It outputs x y. Note that y is just one bit.

c. Re-encryption

./ReEnc [Key] [cipher_x] [cipher_y]

It computes c'= c^{key} and outputs x y.

d. Decryption

./Dec [Key] [cipher_x] [cipher_y]

It computes c'= c^{key} and outputs x y. (The same as Re-encryption.)

Note that it only decrypts to g^m.
