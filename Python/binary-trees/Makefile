compile:
	cp binarytrees.python3 binarytrees.py
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/python3 -OO binarytrees.py 21" Python binary-trees

run:
	/usr/bin/python3 -OO binarytrees.py 21

mem:
	/usr/bin/time -v /usr/bin/python3 -OO binarytrees.py 21
