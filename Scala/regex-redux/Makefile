compile:
		/usr/bin/scalac -d . regexredux.scala

measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/scala regexredux 0 < ../../regexredux-input5000000.txt" Scala regex-redux

run:
	/usr/bin/scala regexredux 0 < ../../regexredux-input5000000.txt


mem:
	/usr/bin/time -v /usr/bin/scala regexredux 0 < ../../regexredux-input5000000.txt

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/scala regexredux 0 < ../../regexredux-input5000000.txt

clean:
	rm -rf *.class *.tasty
