# enigma
A separate repo for my enigma machine.

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

##Licence
This software is licensed under the GNU GPL version 3 or later. A copy is included in COPYING.
