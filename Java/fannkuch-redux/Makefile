compile:
	javac -d .  fannkuchredux.java
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/java   fannkuchredux 12" Java fannkuch-redux

run:
	/usr/bin/java   fannkuchredux 12

mem:
	/usr/bin/time -v  /usr/bin/java   fannkuchredux 12

valgrind:
	valgrind --tool=massif --stacks=yes  /usr/bin/java   fannkuchredux 12
