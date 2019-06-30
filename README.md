## About this project
This is a software implementation of the classic World War II Enigma machine. I've implemented eight wheels, three reflectors, and three wheel slots, which is far from the only configuration these machines were available in. Instead of pressing keys one at a time and having them light up bulbs, my implementation encodes a whole string of words all at once. I also allow for using the same wheel more than once, because why not.

There are two versions, the first written in Java with a GUI, the second in C++ with NCurses. The Java version is very messy! But eventually I'll clean it up.

Special thanks to [craig9 for his Python implementation](https://github.com/craig9/k4gi-enigma) that helped me understand my own code a lot better!

## About the implementation
Wheel specs are recorded here:

[en.wikipedia.org](https://en.wikipedia.org/wiki/Enigma_rotor_details)

[users.telenet.be](http://users.telenet.be/d.rijmenants/en/enigmatech.htm) (this is the page I relied on)

[codesandciphers.org](https://www.codesandciphers.org.uk/enigma/rotorspec.htm)

Actions speak louder than words, so here is a video of a paper enigma machine in action.

[YouTube](https://www.youtube.com/watch?v=pZsuxZXN33g)

Notice that the wheels, rotors, whatever, move BEFORE encoding the letter typed!

Here also is an Arduino implementation, code included.

[Instructables](https://www.instructables.com/id/Make-your-own-Enigma-Replica/)

The Enigma machine works by sending an electrical signal...
From the keyboard, to the plugboard, to a static wheel that connects to the moving wheels...
From there to wheel III, to wheel II, to wheel I, to the reflector, then back again...
Wheel I, II, III, static wheel, plugboard, then the lampboard. Result!
Oh right and in some versions there are four wheels!

So in short:
1. Input character
2. Shift wheels
3. Plugboard
4. Wheel III
5. Wheel II
6. Wheel I
7. Reflector
8. Wheel I
9. Wheel II
10. Wheel III
11. Plugboard
12. Output character!

the steps to move forward (left) through the enigma machine are...
1. Identify the entry point
	To make it easier I'm going to transform code_letter into its un-rotated form between each wheel
	This is derived from the position of string[0] compared to keyboard
2. Move through the wheel
	Going forward, this is a simple case of matching the entry character in string [0] with its counterpart in [1]
3. Identify exit point
	So you have the new letter from string[1]. But it needs to exit at the position of string[0]!
	Tranform code_letter into its un-rotated form, based on that position.
	
the steps to move backward (right) are...
1. Identify the entry point
	This is the same as going forward. Transform the un-rotated letter with string[0]
2. Move through the wheel
	We need to find code_letter in string[1], and transform it into the matching char in string [0]
3. Identify the exit point
	Since we're going backwards we're already in the right position to un-rotate code_letter

steps to move through the reflector and plugboard
1. transform code_letter
	The reflector doesn't rotate so just find code_letter in keyboard and match the position to the reflector

## Compiling

### Java

This version requires (?) OpenJDK11, which can be installed under Debian-based Linux with:

    # apt install openjdk-11-jdk

To compile:

    $ javac Cipher3.java

Then to run:

    $ java Cipher3

It's also possible to run the source code directly:

    $ java Cipher3.java

### C++

This version requires a C++ compiler such as g++, as well as ncurses, which can be installed under Debian-based Linux with:

    # apt install g++ ncurses

To compile:

    $ g++ list.cpp enigma_machine.cpp enigma_front.cpp -lncurses -o enigma

Then to run:

    $ ./enigma

## Licence

This software is licensed under the GNU GPL version 3 or later. A copy is included in COPYING.
