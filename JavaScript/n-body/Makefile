compile:
	cp -L nbody.node nbody.js
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/node nbody.js 50000000" JavaScript n-body

run:
	/usr/bin/node nbody.js 50000000

mem:
	/usr/bin/time -v  /usr/bin/node nbody.js 50000000

valgrind:
	valgrind --tool=massif --stacks=yes  /usr/bin/node nbody.js 50000000
