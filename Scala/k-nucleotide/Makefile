# Info
benchmarkName = k-nucleotide
mainClass = knucleotide
input = 0 < ../../knucleotide-input25000000.txt -J-Xmx512M
output = 

# Config
mode = 
# #Filenames
fast      = knucleotide.scala
idiomatic = knucleotide_idiomatic.scala
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
 
