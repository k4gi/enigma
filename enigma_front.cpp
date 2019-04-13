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

#include <ncurses.h>
#include <string>
#include "list.h"
#include "enigma_machine.h"

void clearWindow(WINDOW *win) {
	int y,x;
	getmaxyx(win,y,x);
	for(int i=1;i<y-1;i++) {
		for(int j=1;j<x-1;j++) {
			mvwaddch(win,i,j,' ');
		}
	}
}

void clearLine(WINDOW *win, int liney) {
	int y,x;
	getmaxyx(win,y,x);
	for(int i=1;i<x-1;i++) {
		mvwaddch(win,liney,i,' ');
	}
}

std::string getString(WINDOW *display) {
	std::string input;

	nocbreak();
	echo();
	curs_set(1);

	int ch = mvwgetch(display,1,1);

	while( ch != '\n' ) {
		input.push_back( ch );
		ch = wgetch(display);
	}

	cbreak();
	noecho();
	curs_set(0);

	return input;
}

int main() {
	initscr(); //init ncurses
	cbreak(); //ctrl-c works
	noecho(); //dont repeat back
	curs_set(0); //no cursor
	
	int y,x;
	getmaxyx(stdscr,y,x); //how much space
	WINDOW *inwin = newwin(5,x,0,0);
	WINDOW *outwin = newwin(5,x,5,0);
	WINDOW *slotwin = newwin(y-15,x/2,10,0);
	WINDOW *plugwin = newwin(y-15,x/2,10,x/2);
	WINDOW *helpwin = newwin(5,x,y-5,0);
	box(inwin,0,0);
	box(outwin,0,0);
	box(slotwin,0,0);
	box(plugwin,0,0);
	box(helpwin,0,0);
	mvwprintw(inwin,0,3,"1:Input window");
	mvwprintw(outwin,0,3,"2:Output window");
	mvwprintw(slotwin,0,3,"3:Slot settings");
	mvwprintw(plugwin,0,3,"4:Plugboard settings");
	mvwprintw(helpwin,0,3,"5:Information");
	mvwprintw(helpwin,3,1,"Press '12345' to navigate, and 'q' to exit");
	wrefresh(inwin);
	wrefresh(outwin);
	wrefresh(slotwin);
	wrefresh(plugwin);
	wrefresh(helpwin);
	
	//init slot menu here
	std::string slotoptions[] = {"Reflector","Slot I","Slot II","Slot III"};
	int slotsize = 4;
	std::string refoptions[] = {"Reflector A","Reflector B","Reflector C"};
	int refsize = 3;
	std::string wheeloptions[] = {"Wheel I","Wheel II","Wheel III","Wheel IV","Wheel V","Wheel VI","Wheel VII","Wheel VIII"};
	int wheelsize = 8;
	//save the settings we're making
	//also an array would be good for this stuff but...its mostly done now
	int ref = 0;
	int slot1 = 0;
	int slot2 = 0;
	int slot3 = 0;
	//lmao i totally forgot about wheel settings
	char set1 = 'A';
	char set2 = 'A';
	char set3 = 'A';
	//std::string plug = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //is there a better way to store this one?
	list plug; //yes there is
	std::string plug_out; //turns out I need this here anyway tho
	std::string input = ""; //the fabled secret message
	//navigation variables
	char in='q';
	char in2=' '; //oh my i need a second input for the plugboard
	int sel=0;
	int sel2=0;
	//uhh also a 'keyboard' string for checking plugboard input
	std::string keyboard = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	//finally, THE mACHINE
	enigma_machine machine;
	
	//main menu loop
	do {
		in = wgetch(helpwin);
		switch(in) {
		case '1':
			clearWindow(inwin);
			clearWindow(helpwin);
			wrefresh(inwin);
			mvwprintw(helpwin,1,1,"Selected: Input");
			mvwprintw(helpwin,2,1,"Press Enter to finish typing");
			wrefresh(helpwin);
			input = getString(inwin);
			clearWindow(helpwin);
			mvwprintw(helpwin,1,1,"Input done!");
			break;
		case '2':
			clearWindow(outwin);
			clearWindow(helpwin);
			mvwprintw(helpwin,1,1,"Selected: Output");
			mvwprintw(helpwin,2,1,"Press Enter to encode, any other key to exit");
			if(wgetch(helpwin) == '\n') {
				//its encoding time
				//apply settings to enigma machine
				machine.set_reflector(ref);
				machine.set_wheel(0,slot1,keyboard.find(set1));
				machine.set_wheel(1,slot2,keyboard.find(set2));
				machine.set_wheel(2,slot3,keyboard.find(set3));
				plug_out = keyboard;
				for(int i=0;i<plug.show().length();i+=3) {
					plug_out[ keyboard.find( plug.show()[0+i] ) ] = plug.show()[1+i];
					plug_out[ keyboard.find( plug.show()[1+i] ) ] = plug.show()[0+i];
				}
				machine.set_plugboard(plug_out);
				//encoding
				mvwprintw(outwin,1,1,machine.encode(input).c_str());
			}
			wrefresh(outwin);
			clearWindow(helpwin);
			mvwprintw(helpwin,1,1,"Output done!");
			break;
		case '3':
			clearWindow(helpwin);
			mvwprintw(helpwin,1,1,"Selected: Slot settings");
			mvwprintw(helpwin,3,1,"Navigate submenu with 'j' for down, 'k' for up, 'l' to select, press 'Enter' when you're done");
			wrefresh(helpwin);
			do {
				clearWindow(slotwin);
				for(int i=0;i<slotsize;i++) {
					if(i==sel) wattron(slotwin,A_REVERSE); //attribute, reverse colours
					mvwprintw(slotwin,i+1,1,slotoptions[i].c_str());
					wattroff(slotwin,A_REVERSE);
				}
				wrefresh(slotwin);
				in = wgetch(helpwin);
				switch(in) {
				case 'k':
					if(sel>0) sel--;
					break;
				case 'j':
					if(sel<slotsize-1) sel++;
					break;
				case 'l':
					if(sel==0) { //reflector time
						do {
							for(int i=0;i<refsize;i++) {
								if(i==sel2) wattron(slotwin,A_REVERSE);
								mvwprintw(slotwin,i+sel+2,1,"  %s",refoptions[i].c_str());
								wattroff(slotwin,A_REVERSE);
							}
							wrefresh(slotwin);
							in = wgetch(helpwin);
							switch(in) {
							case 'k':
								if(sel2>0) sel2--;
								break;
							case 'j':
								if(sel2<refsize-1) sel2++;
								break;
							case 'l':
								ref=sel2;
								clearLine(helpwin,2);
								mvwprintw(helpwin,2,1,"You have selected %s",refoptions[ref].c_str());
								break;
							}
						} while(in != 'l');
					} else { //wheel time
						do {
							for(int i=0;i<wheelsize;i++) {
								if(i==sel2) wattron(slotwin,A_REVERSE);
								mvwprintw(slotwin,i+sel+2,1,"  %s",wheeloptions[i].c_str());
								wattroff(slotwin,A_REVERSE);
							}
							wrefresh(slotwin);
							in = wgetch(helpwin);
							switch(in) {
							case 'k':
								if(sel2>0) sel2--;
								break;
							case 'j':
								if(sel2<wheelsize-1) sel2++;
								break;
							case 'l':
								switch(sel) {
								case 1:
									slot1=sel2;
									clearLine(helpwin,2);
									mvwprintw(helpwin,2,1,"You have selected %s for Slot I",wheeloptions[slot1].c_str());
									//wheel setting
									clearLine(helpwin,3);
									mvwprintw(helpwin,3,1,"Lastly, press a letter for %s to be set to",wheeloptions[slot1].c_str());
									do {
										in2 = toupper(wgetch(helpwin));
									} while(keyboard.find(in2) == std::string::npos);
									set1 = in2;
									clearLine(helpwin,3);
									mvwprintw(helpwin,3,1,"%s has been set to %c",wheeloptions[slot1].c_str(),set1);
									break;
								case 2:
									slot2=sel2;
									clearLine(helpwin,2);
									mvwprintw(helpwin,2,1,"You have selected %s for Slot II",wheeloptions[slot2].c_str());
									//wheel setting
									clearLine(helpwin,3);
									mvwprintw(helpwin,3,1,"Lastly, press a letter for %s to be set to",wheeloptions[slot2].c_str());
									do {
										in2 = toupper(wgetch(helpwin));
									} while(keyboard.find(in2) == std::string::npos);
									set2 = in2;
									clearLine(helpwin,3);
									mvwprintw(helpwin,3,1,"%s has been set to %c",wheeloptions[slot2].c_str(),set2);
									break;
								case 3:
									slot3=sel2;
									clearLine(helpwin,2);
									mvwprintw(helpwin,2,1,"You have selected %s for Slot III",wheeloptions[slot3].c_str());
									//wheel setting
									clearLine(helpwin,3);
									mvwprintw(helpwin,3,1,"Lastly, press a letter for %s to be set to",wheeloptions[slot3].c_str());
									do {
										in2 = toupper(wgetch(helpwin));
									} while(keyboard.find(in2) == std::string::npos);
									set3 = in2;
									clearLine(helpwin,3);
									mvwprintw(helpwin,3,1,"%s has been set to %c",wheeloptions[slot3].c_str(),set3);
									break;
								}
								break;
							}
						} while(in != 'l');
					}
					sel2=0;
					break;
				}
			} while(in != '\n');
			sel = 0;
			clearWindow(slotwin);
			//put code here to show what settings were actually made, for reference
			mvwprintw(slotwin,1,1,"%s: %s",slotoptions[0].c_str(),refoptions[ref].c_str());
			mvwprintw(slotwin,2,1,"%s: %s at %c",slotoptions[1].c_str(),wheeloptions[slot1].c_str(),set1);
			mvwprintw(slotwin,3,1,"%s: %s at %c",slotoptions[2].c_str(),wheeloptions[slot2].c_str(),set2);
			mvwprintw(slotwin,4,1,"%s: %s at %c",slotoptions[3].c_str(),wheeloptions[slot3].c_str(),set3);
			wrefresh(slotwin);
			clearWindow(helpwin);
			mvwprintw(helpwin,1,1,"Slot settings done!");
			break;
		case '4':
			do {
				clearWindow(plugwin);
				clearWindow(helpwin);
				mvwprintw(helpwin,1,1,"Selected: Plugboard settings");
				mvwprintw(helpwin,2,1,"Press a letter, then another letter, and they get connected");
				mvwprintw(helpwin,3,1,"Press Enter when you're done");
				mvwprintw(plugwin,1,1,"Plugs:");
				mvwprintw(plugwin,2,1,plug.show().c_str()); //like magic
				wrefresh(plugwin);
				in = toupper(wgetch(helpwin));
				if(keyboard.find(in) != std::string::npos) { //if found
					//um i gotta make the user give me another char so
					do {
						clearWindow(helpwin);
						mvwprintw(helpwin,1,1,"You entered %c, time for that second letter",in);
						in2 = toupper(wgetch(helpwin));
					} while(keyboard.find(in2) == std::string::npos);
					//time to alter the list
					plug.remove(in);
					plug.remove(in2);
					if(in != in2) plug.add(in,in2);
				}
			} while(in != '\n');
			clearWindow(helpwin);
			mvwprintw(helpwin,1,1,"Plugboard settings done!");
			break;
		case '5':
			clearWindow(helpwin);
			mvwprintw(helpwin,1,1,"Selected: Information");
			mvwprintw(helpwin,2,1,"Yeah idk why you'd select this one maybe to 'q'uit the program?");
			mvwprintw(helpwin,3,1,"Press '12345' to navigate, and 'q' to exit");
			break;
		default:
			clearWindow(helpwin);
			mvwprintw(helpwin,1,1,"You pressed the wrong key lol");
			mvwprintw(helpwin,3,1,"Press '12345' to navigate, and 'q' to exit");
		}
	} while(in != 'q');

	endwin(); //stop ncurses
	return 0;
}
