compile:
	javac -d . -cp .:../fastutil-7.0.13.jar knucleotide.java
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/java  -cp .:../fastutil-7.0.13.jar knucleotide 0 < ../../knucleotide-input25000000.txt" Java k-nucleotide

run:
	/usr/bin/java  -cp .:../fastutil-7.0.13.jar knucleotide 0 < ../../knucleotide-input25000000.txt

mem:
	/usr/local/src/jdk1.8.0_121/bin//usr/bin/time -v  java  -cp .:../fastutil-7.0.13.jar knucleotide 0 < ../../knucleotide-input25000000.txt

valgrind:
	/usr/local/src/jdk1.8.0_121/bin/valgrind --tool=massif --stacks=yes  java  -cp .:../fastutil-7.0.13.jar knucleotide 0 < ../../knucleotide-input25000000.txt
