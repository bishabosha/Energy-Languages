compile:
	/usr/bin/scalac -d . BinaryTrees.scala
	
measure:
	sudo modprobe msr
	sudo ../../RAPL/main "/usr/bin/scala BinaryTrees 21" Scala binary-trees

run:
	/usr/bin/scala BinaryTrees 21

mem:
	/usr/bin/time -v /usr/bin/scala BinaryTrees 21

valgrind:
	valgrind --tool=massif --stacks=yes /usr/bin/scala BinaryTrees 21
