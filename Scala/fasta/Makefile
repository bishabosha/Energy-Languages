# Info
benchmarkName = fasta
mainClass = fasta
input-test = 1000
input-benchmark = 25000000
output = 

# Config
mode = 
configName = Scala

# Filenames
fast      = fasta.scala
idiomatic = fasta_idiomatic.scala

default   = ${fast}
filename  = ${default}

# Environment
scalaPath  = /usr/bin/scala
scalacPath = /usr/bin/scalac

# Logic (do not edit)
ifeq ($(mode),idiomatic)
  	filename=${idiomatic}
endif

compile:
	${scalacPath} -d . ${filename}

measure:
	sudo modprobe msr
	sudo ../../RAPL/main "${scalaPath} ${mainClass} ${input-benchmark} ${output}" ${configName} ${benchmarkName}

test:
	${scalaPath} ${mainClass} ${input-test} ${output} | diff test-output.txt -

run:
	${scalaPath} ${mainClass} ${input-benchmark} ${output}

mem:
	/usr/bin/time -v ${scalaPath} ${mainClass} ${input-benchmark} ${output}

valgrind:
	valgrind --tool=massif --stacks=yes ${scalaPath} ${mainClass} ${input-benchmark} ${output}

clean:
	rm -rf *.class *.tasty
