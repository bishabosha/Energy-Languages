compile:
	cp revcomp.java-3.java revcomp.java
	javac -d .  revcomp.java
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/java   revcomp 0 < ../../revcomp-input25000000.txt" Java reverse-complement

run:
	/usr/bin/java   revcomp 0 < ../../revcomp-input25000000.txt

mem:
	/usr/bin/time -v /usr/bin/java   revcomp 0 < ../../revcomp-input25000000.txt

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/java   revcomp 0 < ../../revcomp-input25000000.txt
