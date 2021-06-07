# DobbleMaker
Java program that uses principle of a finite projective plane to create your own dobble game - 
just add your own symbol images and this programme can create a set of pages that can be printed and 
cut out to make your personalised set of Dobble cards!

This program is designed as much as possible to be adaptable to how many symbols you want on your dobble 
card - it can take any prime number to make your dobble game. The default for this code is 7, meaning
8 symbols per page and a total of 57 images required, producing 57 cards. However, you could make it an 
easier game by changing the PRIME_VALUE object in the CardGenerator class to 5, or harder if you
changed it to 11, say. Just bear in mind that the higher you set it, the more pictures are required.

The only thing that will nto adjust automatically with changing the PRIME_VALUE object's value are the
drawing fields for the cards and pages, on the DobbleCard and CardPage classes. These are currently set
to be optimal for a PRIME_VALUE of 7 - changing this value will require additional symbol positions to be
given, due to the increased amount of symbols per card.

