compile:
	cp regexredux.java-3.java regexredux.java
	javac -d .  regexredux.java
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/java   regexredux 0 < ../../regexredux-input5000000.txt" Java regex-redux

run:
	/usr/bin/java   regexredux 0 < ../../regexredux-input5000000.txt

mem:
	/usr/bin/time -v /usr/bin/java   regexredux 0 < ../../regexredux-input5000000.txt

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/java   regexredux 0 < ../../regexredux-input5000000.txt
