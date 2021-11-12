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
configName = Java

# Filenames
fast      = knucleotide.java
runner    = JvmRunner.java

default    = ${fast}
impl-file  = ${default} 

# Environment
sRAPLPath  = ../../Scala/sRAPL
sRAPLClassPath = ${SCALA_HOME}/lib/scala-library-2.13.6.jar:${SCALA_HOME}/lib/scala3-library_3-3.1.0.jar:${sRAPLPath}/sRAPL.jar:${sRAPLPath}/jRAPL-1.0.jar 
javaPath  = /usr/bin/java -cp .:../fastutil-7.0.13.jar
javacPath = /usr/bin/javac -cp .:../fastutil-7.0.13.jar
jvmOpts   = 

# Logic (do not edit)
ifeq ($(mode),idiomatic)
  	impl-file=${idiomatic}
endif

before-compile:


compile:
	make before-compile
	${javacPath}:${sRAPLClassPath} -d . ${impl-file} ${runner}

test:
	${javaPath} ${jvmOpts} ${mainClass} ${input-test} ${output} | diff test-output.txt -

run:
	${javaPath} ${jvmOpts} ${mainClass} ${input-benchmark} ${output}

measure:
	sudo modprobe msr
	sudo ../../RAPL/main "${javaPath}  ${mainClass} ${input-benchmark} ${output}" ${configName} ${benchmarkName}

measureWithWarmup:
	sudo modprobe msr
	sudo ${javaPath}:${sRAPLClassPath} \
   ${jvmOpts} \
   JvmRunner ${input-jvm-runner} \
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
   ${javaPath} ${jvmOpts} ${mainClass} ${input-benchmark} ${output}

valgrind:
	valgrind --tool=massif --stacks=yes ${javaPath} ${mainClass} ${input-benchmark} ${output}

clean:
	rm -rf *.class
