compile:
	cp -L binarytrees.node binarytrees.js
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/node binarytrees.js 21" JavaScript binary-trees

run:
	/usr/bin/node binarytrees.js 21

mem:
	/usr/bin/time -v /usr/bin/node binarytrees.js 21

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/node binarytrees.js 21
