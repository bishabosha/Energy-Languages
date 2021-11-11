compile:
	cp -L knucleotide.node-2.node knucleotide.node-2.js
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/node knucleotide.node-2.js 0 < ../../knucleotide-input25000000.txt" JavaScript k-nucleotide

run:
	/usr/bin/node knucleotide.node-2.js 0 < ../../knucleotide-input25000000.txt

mem:
	/usr/bin/time -v /usr/bin/node knucleotide.node-2.js 0 < ../../knucleotide-input25000000.txt

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/node knucleotide.node-2.js 0 < ../../knucleotide-input25000000.txt
