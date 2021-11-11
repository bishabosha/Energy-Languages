compile:
	cp mandelbrot.python3-7.python3 mandelbrot.python3-7.py

measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/python3 -OO mandelbrot.python3-7.py 16000" Python mandelbrot

run:
	/usr/bin/python3 -OO mandelbrot.python3-7.py 16000

mem:
	/usr/bin/time -v /usr/bin/python3 -OO mandelbrot.python3-7.py 16000
