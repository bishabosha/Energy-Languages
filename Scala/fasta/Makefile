compile:
		/usr/bin/scalac -d . fasta.scala

measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/scala fasta 25000000" Rust fasta

run:
	/usr/bin/scala fasta 25000000

mem:
	/usr/bin/time -v /usr/bin/scala fasta 25000000

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/scala fasta 25000000

clean:
	rm -rf *.class *.tasty
