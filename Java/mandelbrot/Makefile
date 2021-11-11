compile:
	cp mandelbrot.java-2.java mandelbrot.java
	javac -d .  mandelbrot.java
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/java   mandelbrot 16000" Java mandelbrot

run:
	/usr/bin/java   mandelbrot 16000

mem:
	/usr/bin/time -v /usr/bin/java   mandelbrot 16000

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/java   mandelbrot 16000
