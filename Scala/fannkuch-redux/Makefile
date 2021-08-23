compile:
	/usr/bin/scalac -d . FannkuchRedux.scala
	
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/scala FannkuchRedux 12" Scala fannkuch-redux

run:
	/usr/bin/scala FannkuchRedux 12

mem:
	/usr/bin/time -v /usr/bin/scala FannkuchRedux 12

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/scala FannkuchRedux 12
