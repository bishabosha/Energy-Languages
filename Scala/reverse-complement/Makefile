compile:
		/usr/bin/scalac -d . revcomp.scala

measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/scala revcomp 0 < ../../revcomp-input25000000.txt" Scala reverse-complement

run:
	/usr/bin/scala revcomp 0 < ../../revcomp-input25000000.txt

mem:
	/usr/bin/time -v /usr/bin/scala revcomp 0 < ../../revcomp-input25000000.txt

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/scala revcomp 0 < ../../revcomp-input25000000.txt

clean:
	rm -rf *.class *.tasty
	