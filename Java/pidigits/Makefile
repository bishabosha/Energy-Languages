compile:
	g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux GmpInteger.c -o GmpInteger.o
	g++ -shared -fPIC -o libjgmp.so GmpInteger.o -lc -lgmp
	cp pidigits.java-2.java pidigits.java
	javac -d .  pidigits.java
xmeasure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/java -Djava.library.path=Include/java  pidigits 10000" Java pidigits

run:
	/usr/bin/java -Djava.library.path=${PWD}  pidigits 10000

mem:
	/usr/bin/time -v /usr/bin/java -Djava.library.path=Include/java  pidigits 10000

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/java -Djava.library.path=Include/java  pidigits 10000

