compile:
	cp -L spectralnorm.node-2.node spectralnorm.node-2.js
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/node spectralnorm.node-2.js 5500" JavaScript spectral-norm

run:
	/usr/bin/node spectralnorm.node-2.js 5500

mem:
	/usr/bin/time -v /usr/bin/node spectralnorm.node-2.js 5500

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/node spectralnorm.node-2.js 5500
