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

#include "enigma_machine.h"

enigma_machine::enigma_machine() {
}

void enigma_machine::set_wheel(int slot, int wheel, int set) {
	working_wheels[slot][0] = keyboard;
	working_wheels[slot][1] = wheels[wheel][0];
	working_wheels[slot][2] = wheels[wheel][1];
	for(int i = 0;i<set;i++) {
		rotate(slot);
	}
}

void enigma_machine::rotate(int slot) {
	working_wheels[slot][0] = working_wheels[slot][0].substr(1,std::string::npos).append(1,working_wheels[slot][0][0]);
	working_wheels[slot][1] = working_wheels[slot][1].substr(1,std::string::npos).append(1,working_wheels[slot][1][0]);
}

void enigma_machine::set_reflector(int ref) {
	working_reflector = reflectors[ref];
}

void enigma_machine::set_plugboard(std::string plug) {
	plugboard = plug;
}

bool enigma_machine::notch_active(int slot) {
	if(working_wheels[slot][2].find(working_wheels[slot][0][0]) != std::string::npos) {
		return true;
	} else return false;
}

char enigma_machine::encode_char(int slot, bool reverse, char in) {
	char out = working_wheels[slot][0][keyboard.find(in)];
	if(!reverse) {
		out = working_wheels[slot][1][working_wheels[slot][0].find(out)];
	} else {
		out = working_wheels[slot][0][working_wheels[slot][1].find(out)];
	}
	out = keyboard[working_wheels[slot][0].find(out)];
	return out;
}

std::string enigma_machine::encode(std::string in) {
	if(working_wheels[0][0] != "" && working_wheels[0][1] != "" && working_wheels[0][2] != "" &&
	working_wheels[1][0] != "" && working_wheels[1][1] != "" && working_wheels[1][2] != "" &&
	working_wheels[2][0] != "" && working_wheels[2][1] != "" && working_wheels[2][2] != "" &&
	working_reflector != "") {
		
		for(int i=0;i<in.length();i++) {
			in[i] = toupper(in[i]);
		}
		std::string out = "";
		
		for(int i = 0;i<in.length();i++) {
			//keypress
			char out_char = in[i];

			if(keyboard.find(out_char) == std::string::npos) { //catch non-letters
				continue;
			}

			//rotation
			rotate(2);
			if(notch_active(2)) {
				rotate(1);
				if(notch_active(1)) {
					rotate(0);
				}
			}

			//plugboard
			out_char = plugboard[keyboard.find(out_char)];

			//forward (left) encoding
			for(int j=2;j>-1;j--) {
				out_char = encode_char(j,false,out_char);
			}

			//reflector
			out_char = working_reflector[keyboard.find(out_char)];

			//reverse (right) encoding
			for(int j=0;j<3;j++) {
				out_char = encode_char(j,true,out_char);
			}

			//plugboard
			out_char = plugboard[keyboard.find(out_char)];

			//save
			out.append(1,out_char);
		}
		return out;
	} else {
		return "ERROR: machine not set up";
	}
}
