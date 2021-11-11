compile:
	cp nbody.java-4.java nbody.java
	javac -d .  nbody.java
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/java   nbody 50000000" Java n-body

run:
	/usr/bin/java   nbody 50000000

mem:
	/usr/bin/time -v /usr/bin/java   nbody 50000000

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/java   nbody 50000000
