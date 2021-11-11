compile:
	cp -L revcomp.node-7.node revcomp.node-7.js
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/node revcomp.node-7.js 0 < ../../revcomp-input25000000.txt" JavaScript reverse-complement

run:
	/usr/bin/node revcomp.node-7.js 0 < ../../revcomp-input25000000.txt

mem:
	/usr/bin/time -v /usr/bin/node revcomp.node-7.js 0 < ../../revcomp-input25000000.txt

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/node revcomp.node-7.js 0 < ../../revcomp-input25000000.txt
