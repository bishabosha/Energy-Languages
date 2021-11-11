compile:
	cp binarytrees.java-7.java binarytrees.java
	javac -d .  binarytrees.java
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/java binarytrees 21" Java binary-trees

run:
	/usr/bin/java binarytrees 21

mem:
	/usr/bin/time -v/usr/local/src/jdk1.8.0_121/bin/ java binarytrees 21

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/java binarytrees 21
