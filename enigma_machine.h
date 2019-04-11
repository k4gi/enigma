/*
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

#ifndef ENIGMA_MACHINE_H
#define ENIGMA_MACHINE_H

#include <string>

class enigma_machine {
	std::string wheels[8][2] = {
		{"EKMFLGDQVZNTOWYHXUSPAIBRCJ", "R"},
		{"AJDKSIRUXBLHWTMCQGZNPYFVOE", "F"},
		{"BDFHJLCPRTXVZNYEIWGAKMUSQO", "W"},
		{"ESOVPZJAYQUIRHXLNFTGKDCMWB", "K"},
		{"VZBRGITYUPSDNHLXAWMJQOFECK", "A"},
		{"JPGVOUMFYQBENHZRDKASXLICTW", "AN"},
		{"NZJHGRCXMYSWBOUFAIVLPEKQDT", "AN"},
		{"FKQHTLXOCBJSPDZRAMEWNIUYGV", "AN"}
	};
	std::string reflectors[3] = {
		"EJMZALYXVBWFCRQUONTSPIKHGD",
		"YRUHQSLDPXNGOKMIEBFZCWVJAT",
		"FVPJIAOYEDRZXWGCTKUQSBNMHL"
	};
	std::string keyboard = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	std::string plugboard = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	std::string working_wheels[3][3] = {
		{"","",""},
		{"","",""},
		{"","",""}
	};
	std::string working_reflector = "";

	
	char encode_char(int slot, bool reverse, char in);
	void rotate(int slot);
	bool notch_active(int slot);
public:
	enigma_machine();
	void set_wheel(int slot, int wheel, int set);
	void set_reflector(int ref);
	void set_plugboard(std::string plug);
	std::string encode(std::string in);
};

#endif
