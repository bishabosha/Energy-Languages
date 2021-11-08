# Info
benchmarkName = binary-trees
mainClass = BinaryTrees
input-test = 10
input-benchmark = 21
warmup-iterations=5
output = 

# Config
mode = 
configName = Scala

# Filenames
fast      = BinaryTrees.scala
idiomatic = BinaryTrees.scala
runner    = BinaryTreesRunner.scala

default   = ${fast}
filename  = ${default}

# Environment
scalaPath  = /usr/bin/scala
scalacPath = /usr/bin/scalac
scalacClasspath = -cp ../sRAPL/sRAPL.jar 
scalaClasspath = -cp ../sRAPL/sRAPL.jar -cp ../sRAPL/jRAPL-1.0.jar

# Logic (do not edit)
ifeq ($(mode),idiomatic)
  	filename=${idiomatic}
endif

compile:
	${scalacPath} ${scalacClasspath} -d . ${filename} ${runner}

measure:
	sudo modprobe msr
	sudo ../../RAPL/main "${scalaPath} ${mainClass} ${input-benchmark} ${output}" ${configName} ${benchmarkName}

test:
	${scalaPath} ${mainClass} ${input-test} ${output} | diff test-output.txt -

run:
	${scalaPath} ${mainClass} ${input-benchmark} ${output}

measureWithWarmup:
	sudo modprobe msr
	sudo ${scalaPath} ${scalaClasspath} runner ${input-benchmark} ${output} 

mem:
	/usr/bin/time -v ${scalaPath} ${mainClass} ${input-benchmark} ${output}

valgrind:
	valgrind --tool=massif --stacks=yes ${scalaPath} ${mainClass} ${input-benchmark} ${output}

clean:
	rm -rf *.class *.tasty
