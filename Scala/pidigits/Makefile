compile:
		/usr/bin/scalac -d . pidigits.scala

measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/scala pidigits 10000" Scala pidigits

run:
	/usr/bin/scala pidigits 10000

mem:
	/usr/bin/time -v /usr/bin/scala pidigits 10000

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/scala pidigits 10000

clean:
	rm -rf *.class *.tasty
