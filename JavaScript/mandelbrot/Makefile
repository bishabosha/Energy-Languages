compile:
	cp -L mandelbrot.node mandelbrot.js
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/node mandelbrot.js 16000" JavaScript mandelbrot

run:
	/usr/bin/node mandelbrot.js 16000

mem:
	/usr/bin/time -v /usr/bin/node mandelbrot.js 16000

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/node mandelbrot.js 16000
