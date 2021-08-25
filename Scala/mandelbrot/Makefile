compile:
		/usr/bin/scalac -d . mandelbrot.scala

measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/scala mandelbrot 16000" Scala mandelbrot

run:
	/usr/bin/scala mandelbrot 16000

mem:
	/usr/bin/time -v /usr/bin/scala mandelbrot 16000

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/scala mandelbrot 16000

clean:
	rm -rf *.class *.tasty
