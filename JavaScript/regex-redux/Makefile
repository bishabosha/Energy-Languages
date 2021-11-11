compile:
	cp -L regexredux.node-2.node regexredux.node-2.js
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/node regexredux.node-2.js 0 < ../../regexredux-input5000000.txt" JavaScript regex-redux

run:
	/usr/bin/node regexredux.node-2.js 0 < ../../regexredux-input5000000.txt

mem:
	/usr/bin/time -v /usr/bin/node regexredux.node-2.js 0 < ../../regexredux-input5000000.txt

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/node regexredux.node-2.js 0 < ../../regexredux-input5000000.txt
