# Info
benchmarkName = k-nucleotide
mainClass = knucleotide
input-test = 0 < ../fasta/test-output.txt
input-benchmark = 0 < ../../knucleotide-input25000000.txt -J-Xmx512M
input-jvm-runner= ../../knucleotide-input25000000.txt -J-Xmx512M
warmup-iterations=5
warmup-measure=true
output = 

# Config
mode = 
configName = Scala

# Filenames
fast      = knucleotide.scala
idiomatic = knucleotide_idiomatic.scala
runner    = JvmRunner.scala

default    = ${fast}
impl-file  = ${default} 

# Environment
sRAPLPath  = ../sRAPL
scalaPath  = /usr/bin/scala
scalacPath = /usr/bin/scalac -cp ${sRAPLPath}/sRAPL.jar

# Logic (do not edit)
ifeq ($(mode),idiomatic)
  	impl-file=${idiomatic}
endif

compile:
	${scalacPath} -d . ${impl-file} ${runner}

test:
	${scalaPath} ${mainClass} ${input-test} ${output} | diff test-output.txt -

run:
	${scalaPath} ${mainClass} ${input-benchmark} ${output}

measure:
	sudo modprobe msr
	sudo ../../RAPL/main "${scalaPath} ${mainClass} ${input-benchmark} ${output}" ${configName} ${benchmarkName}

measureWithWarmup:
	sudo modprobe msr
	sudo ${scalaPath} ${scalaClasspath} \
   -cp ${sRAPLPath}/sRAPL.jar \
   -cp ${sRAPLPath}/jRAPL-1.0.jar \
   run ${input-jvm-runner} \
   --label ${benchmarkName} \
   --measure-warmup ${warmup-measure} \
   --warmup-iterations ${warmup-iterations} \
   --output ../${configName}-warmedUp.csv \
   ${output} 
 
mem:
	/usr/bin/time \
   --format="${benchmarkName}, %e, %P, %M" \
   --output=../${configName}-memory.csv \
   --append \
   ${scalaPath} ${mainClass} ${input-benchmark} ${output}

valgrind:
	valgrind --tool=massif --stacks=yes ${scalaPath} ${mainClass} ${input-benchmark} ${output}

clean:
	rm -rf *.class *.tasty
