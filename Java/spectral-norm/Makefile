compile:
	cp spectralnorm.java-2.java spectralnorm.java
	javac -d .  spectralnorm.java
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "java   spectralnorm 5500" Java spectral-norm

run:
	/usr/bin/java   spectralnorm 5500

mem:
	/usr/bin/time -v /usr/bin/java   spectralnorm 5500

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/java   spectralnorm 5500
