all: enigma run clean

enigma:
	g++ list.cpp enigma_machine.cpp enigma_front.cpp -lncurses -o enigma
run:
	./enigma
clean:
	rm enigma
