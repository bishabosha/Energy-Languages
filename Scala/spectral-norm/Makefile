compile:
		/usr/bin/scalac -d . spectralnorm.scala

measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/scala spectralnorm 5500" Scala spectral-norm

run:
	/usr/bin/scala spectralnorm 5500


mem:
	/usr/bin/time -v /usr/bin/scala spectralnorm 5500

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/scala spectralnorm 5500

clean:
	rm -rf *.class *.tasty
