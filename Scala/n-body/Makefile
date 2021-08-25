compile:
		/usr/bin/scalac -d . nbody.scala

measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/scala nbody 50000000" Scala n-body

run:
	/usr/bin/scala nbody 50000000

mem:
	/usr/bin/time -v /usr/bin/scala nbody 50000000

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/scala nbody 50000000

clean:
	rm -rf *.class *.tasty
