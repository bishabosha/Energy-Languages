# Info
benchmarkName = binary-trees
mainClass = BinaryTrees
input = 21
output = 

# Config
mode = 
# #Filenames
fast      = BinaryTrees.scala
idiomatic = BinaryTrees.scala
default   = ${fast}
filename  = ${default}

scalaPath  = /usr/bin/scala
scalacPath = /usr/bin/scalac

# Logic

ifeq ($(mode),idiomatic)
  	filename=${idiomatic}
endif

compile:
	${scalacPath} -d . ${filename}

measure:
	sudo modprobe msr
	sudo ../../RAPL/main "${scalaPath} ${mainClass} ${input} ${output}" Scala ${benchmarkName}

run:
	${scalaPath} ${mainClass} ${input} ${output}

mem:
	/usr/bin/time -v ${scalaPath} ${mainClass} ${input} ${output}

valgrind:
	valgrind --tool=massif --stacks=yes ${scalaPath} ${mainClass} ${input} ${output}

clean:
	rm -rf *.class *.tasty
 