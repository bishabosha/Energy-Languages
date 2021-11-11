compile:
	cp fasta.java-5.java fasta.java
	javac -d .  fasta.java
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/java   fasta 25000000" Java fasta

run:
	/usr/bin/java   fasta 25000000

mem:
	/usr/bin/time -v /usr/bin/java   fasta 25000000

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/java   fasta 25000000
