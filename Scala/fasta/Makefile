# Info
benchmarkName = fasta
mainClass = fasta
input = 25000000
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
	sudo ../../RAPL/main "${scalaPath} ${mainClass} ${input} ${output}" ${configName} ${benchmarkName}

run:
	${scalaPath} ${mainClass} ${input} ${output}

mem:
	/usr/bin/time -v ${scalaPath} ${mainClass} ${input} ${output}

valgrind:
	valgrind --tool=massif --stacks=yes ${scalaPath} ${mainClass} ${input} ${output}

clean:
	rm -rf *.class *.tasty
