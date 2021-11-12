// using scala 3.1.0
import scala.annotation._
import java.nio.file._
import java.io.File

@main()
def genMakefile(): Unit = {
  for {
    config <- configs
    file = config.directory.resolve("Makefile")
  } {
    println(s"Generating ${config.benchmarkName}")
    Files.write(
      file,
      template(config).getBytes(),
      StandardOpenOption.CREATE,
      StandardOpenOption.WRITE,
      StandardOpenOption.TRUNCATE_EXISTING
    )
  }
}

val tab = '\t'
val cwd = Paths.get(".").toRealPath()
val configs = List(
  Config(
    benchmarkName = "binary-trees",
    mainClass = "binarytrees",
    inputs = Inputs(test = "10", benchmark = "21"),
    files = Filenames(
      fast = "binarytrees.java"
    )
  ),
  Config(
    benchmarkName = "fannkuch-redux",
    mainClass = "fannkuchredux",
    inputs = Inputs(test = "7", benchmark = "12"),
    files = Filenames(
      fast = "fannkuchredux.java"
    )
  ),
  Config(
    benchmarkName = "fasta",
    mainClass = "fasta",
    inputs = Inputs(test = "1000", benchmark = "25000000"),
    files = Filenames(
      fast = "fasta.java"
    )
  ),
  Config(
    benchmarkName = "k-nucleotide",
    mainClass = "knucleotide",
    inputs = Inputs(
      test = "0 < ../fasta/test-output.txt",
      benchmark = "0 < ../../knucleotide-input25000000.txt -J-Xmx512M"
    ),
    files = Filenames(
      fast = "knucleotide.java"
    )
  ),
  Config(
    benchmarkName = "mandelbrot",
    mainClass = "mandelbrot",
    inputs = Inputs(test = "200", benchmark = "16000"),
    files = Filenames(
      fast = "mandelbrot.java"
    ),
    testCommand = s"cmp test-output.txt -"
  ),
  Config(
    benchmarkName = "n-body",
    mainClass = "nbody",
    inputs = Inputs(test = "1000", benchmark = "50000000"),
    files = Filenames(
      fast = "nbody.java"
    )
  ),
  Config(
    benchmarkName = "pidigits",
    mainClass = "pidigits",
    inputs = Inputs(test = "30", benchmark = "10000"),
    files = Filenames(
      fast = "pidigits.java"
    ),
    beforeCompile =
      s"""${tab}g++ -c -fPIC -I$${JAVA_HOME}/include -I$${JAVA_HOME}/include/linux GmpInteger.c -o GmpInteger.o
	       |${tab}g++ -shared -fPIC -o libjgmp.so GmpInteger.o -lc -lgmp
      """.stripMargin,
    jvmOpts = List(
      "-Djava.library.path=${shell pwd}"
    )
  ),
  Config(
    benchmarkName = "regex-redux",
    mainClass = "regexredux",
    inputs = Inputs(
      test = "0 < ../fasta/test-output.txt",
      benchmark = "0 < ../../regexredux-input5000000.txt -J-Xmx1G"
    ),
    files = Filenames(
      fast = "regexredux.java"
    )
  ),
  Config(
    benchmarkName = "reverse-complement",
    mainClass = "revcomp",
    inputs = Inputs(
      test = "0 < ../fasta/test-output.txt",
      benchmark = "0 < ../../revcomp-input25000000.txt -J-Xmx512M"
    ),
    files = Filenames(
      fast = "revcomp.java"
    )
  ),
  Config(
    benchmarkName = "spectral-norm",
    mainClass = "spectralnorm",
    inputs = Inputs(test = "100", benchmark = "5500"),
    files = Filenames(
      fast = "spectralnorm.java"
    )
  )
)

case class Config(
    benchmarkName: String,
    mainClass: String,
    inputs: Inputs,
    files: Filenames,
    testCommand: String = "diff test-output.txt -",
    beforeCompile: String = "",
    jvmOpts: List[String] = Nil
) {
  def directory: Path = cwd.resolve(benchmarkName)
}

case class Inputs(
    test: String,
    benchmark: String
) {
  def jvmRunner: String = benchmark.stripPrefix("0 < ")
}
case class Filenames(
    fast: String
)

def template(ctx: Config): String = {
  import ctx._
  val jvmOpts = ctx.jvmOpts.mkString(" ")
  s"""# Info
  |benchmarkName = ${benchmarkName}
  |mainClass = ${mainClass}
  |input-test = ${inputs.test}
  |input-benchmark = ${inputs.benchmark}
  |input-jvm-runner= ${inputs.jvmRunner}
  |warmup-iterations=5
  |warmup-measure=true
  |output = 
  |
  |# Config
  |mode = 
  |configName = Java
  |
  |# Filenames
  |fast      = ${files.fast}
  |runner    = JvmRunner.java
  |
  |default    = $${fast}
  |impl-file  = $${default} 
  |
  |# Environment
  |sRAPLPath  = ../../Scala/sRAPL
  |sRAPLClassPath = $${SCALA_HOME}/lib/scala-library-2.13.6.jar:$${SCALA_HOME}/lib/scala3-library_3-3.1.0.jar:$${sRAPLPath}/sRAPL.jar:$${sRAPLPath}/jRAPL-1.0.jar 
  |javaPath  = /usr/bin/java -cp .:../fastutil-7.0.13.jar
  |javacPath = /usr/bin/javac -cp .:../fastutil-7.0.13.jar
  |jvmOpts   = $jvmOpts
  |
  |# Logic (do not edit)
  |ifeq ($$(mode),idiomatic)
  |  	impl-file=$${idiomatic}
  |endif
  |
  |before-compile:
  |${beforeCompile}
  |
  |compile:
  |${tab}make before-compile
  |${tab}$${javacPath}:$${sRAPLClassPath} -d . $${impl-file} $${runner}
  |
  |test:
  |	$${javaPath} $${jvmOpts} $${mainClass} $${input-test} $${output} | ${testCommand}
  |
  |run:
  |	$${javaPath} $${jvmOpts} $${mainClass} $${input-benchmark} $${output}
  |
  |measure:
  |	sudo modprobe msr
  |	sudo ../../RAPL/main "$${javaPath} $jvmOpts $${mainClass} $${input-benchmark} $${output}" $${configName} $${benchmarkName}
  |
  |measureWithWarmup:
	|${tab}sudo modprobe msr
	|${tab}sudo $${javaPath}:$${sRAPLClassPath} \\
  |   $${jvmOpts} \\
  |   JvmRunner $${input-jvm-runner} \\
  |   --label $${benchmarkName} \\
	|   --measure-warmup $${warmup-measure} \\
	|   --warmup-iterations $${warmup-iterations} \\
	|   --output ../$${configName}-warmedUp.csv \\
	|   $${output} 
  | 
  |mem:
  |${tab}/usr/bin/time \\
  |   --format="$${benchmarkName}, %e, %P, %M" \\
  |   --output=../$${configName}-memory.csv \\
  |   --append \\
  |   $${javaPath} $${jvmOpts} $${mainClass} $${input-benchmark} $${output}
  |
  |valgrind:
  |	valgrind --tool=massif --stacks=yes $${javaPath} $${mainClass} $${input-benchmark} $${output}
  |
  |clean:
  |	rm -rf *.class
  |""".stripMargin
}
