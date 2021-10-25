# Info
benchmarkName = spectral-norm
mainClass = spectralnorm
input = 5500
output = 

# Config
mode = 
configName = Scala

# Filenames
fast      = spectralnorm.scala
idiomatic = spectralnorm_idiomatic.scala

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
