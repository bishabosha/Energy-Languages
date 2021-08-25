compile:
		/usr/bin/scalac -d . knucleotide.scala

measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/scala knucleotide 0 < ../../knucleotide-input25000000.txt" Scala k-nucleotide

run:
	/usr/bin/scala knucleotide 0 < ../../knucleotide-input25000000.txt


mem:
	/usr/bin/time -v /usr/bin/scala /usr/bin/scala knucleotide 0 < ../../knucleotide-input25000000.txt

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/scala /usr/bin/scala knucleotide 0 < ../../knucleotide-input25000000.txt

clean:
	rm -rf *.class *.tasty
