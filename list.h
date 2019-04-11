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

#ifndef LIST_H
#define LIST_H

#include <string>

struct node {
	char data1,data2;
	node *next;
};

class list {
	node *head;
public:
	list();
	void add(char in1,char in2);
	void remove(char in);
	char find(char in);
	std::string show();
};

#endif
