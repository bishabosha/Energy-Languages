# Info
benchmarkName = mandelbrot
mainClass = mandelbrot
input-test = 200
input-benchmark = 16000
output = 

# Config
mode = 
configName = Scala

# Filenames
fast      = mandelbrot.scala
idiomatic = mandelbrot_idiomatic.scala

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
	${scalaPath} ${mainClass} ${input-test} ${output} | cmp test-output.txt -

run:
	${scalaPath} ${mainClass} ${input-benchmark} ${output}

mem:
	/usr/bin/time -v ${scalaPath} ${mainClass} ${input-benchmark} ${output}

valgrind:
	valgrind --tool=massif --stacks=yes ${scalaPath} ${mainClass} ${input-benchmark} ${output}

clean:
	rm -rf *.class *.tasty
