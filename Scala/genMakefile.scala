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
      StandardOpenOption.WRITE
    )
  }
}

val cwd = Paths.get(".").toRealPath()
val configs = List(
  Config(
    benchmarkName = "binary-trees",
    mainClass = "BinaryTrees",
    input = "21",
    files = Filenames(
      fast = "BinaryTrees.scala",
      idiomatic = "BinaryTrees.scala"
    )
  ),
  Config(
    benchmarkName = "fannkuch-redux",
    mainClass = "FannkuchRedux",
    input = "12",
    files = Filenames(
      fast = "FannkuchRedux.scala",
      idiomatic = "FannkuchRedux_idiomatic.scala"
    )
  ),
  Config(
    benchmarkName = "fasta",
    mainClass = "fasta",
    input = "25000000",
    files = Filenames(
      fast = "fasta.scala",
      idiomatic = "fasta_idiomatic.scala"
    )
  ),
  Config(
    benchmarkName = "k-nucleotide",
    mainClass = "knucleotide",
    input = "0 < ../../knucleotide-input25000000.txt -J-Xmx512M",
    files = Filenames(
      fast = "knucleotide.scala",
      idiomatic = "knucleotide_idiomatic.scala"
    )
  ),
  Config(
    benchmarkName = "mandelbrot",
    mainClass = "mandelbrot",
    input = "16000",
    files = Filenames(
      fast = "mandelbrot.scala",
      idiomatic = "mandelbrot_idiomatic.scala"
    )
  ),
  Config(
    benchmarkName = "n-body",
    mainClass = "nbody",
    input = "50000000",
    files = Filenames(
      fast = "nbody.scala",
      idiomatic = "nbody_idiomatic.scala"
    )
  ),
  Config(
    benchmarkName = "pidigits",
    mainClass = "pidigits",
    input = "10000",
    files = Filenames(
      fast = "pidigits.scala",
      idiomatic = "pidigits_idiomatic.scala"
    )
  ),
  Config(
    benchmarkName = "regex-redux",
    mainClass = "regexredux",
    input = "0 < ../../regexredux-input5000000.txt -J-Xmx1G",
    files = Filenames(
      fast = "regexredux.scala",
      idiomatic = "regexredux_idiomatic.scala"
    )
  ),
  Config(
    benchmarkName = "reverse-complement",
    mainClass = "revcomp",
    input = "0 < ../../revcomp-input25000000.txt -J-Xmx512M",
    files = Filenames(
      fast = "revcomp.scala",
      idiomatic = "revcomp_idiomatic.scala"
    )
  ),
  Config(
    benchmarkName = "spectral-norm",
    mainClass = "spectralnorm",
    input = "5500",
    files = Filenames(
      fast = "spectralnorm.scala",
      idiomatic = "spectralnorm_idiomatic.scala"
    )
  )
)

case class Config(
    benchmarkName: String,
    mainClass: String,
    input: String,
    files: Filenames
) {
  def directory: Path = cwd.resolve(benchmarkName)
}

case class Filenames(
    fast: String,
    idiomatic: String
)

def template(ctx: Config): String = {
  import ctx._
  s"""# Info
  |benchmarkName = ${benchmarkName}
  |mainClass = ${mainClass}
  |input = ${input}
  |output = 
  |
  |# Config
  |mode = 
  |configName = Scala
  |
  |# Filenames
  |fast      = ${files.fast}
  |idiomatic = ${files.idiomatic}
  |
  |default   = $${fast}
  |filename  = $${default}
  |
  |# Environment
  |scalaPath  = /usr/bin/scala
  |scalacPath = /usr/bin/scalac
  |
  |# Logic (do not edit)
  |ifeq ($$(mode),idiomatic)
  |  	filename=$${idiomatic}
  |endif
  |
  |compile:
  |	$${scalacPath} -d . $${filename}
  |
  |measure:
  |	sudo modprobe msr
  |	sudo ../../RAPL/main "$${scalaPath} $${mainClass} $${input} $${output}" $${configName} $${benchmarkName}
  |
  |run:
  |	$${scalaPath} $${mainClass} $${input} $${output}
  |
  |mem:
  |	/usr/bin/time -v $${scalaPath} $${mainClass} $${input} $${output}
  |
  |valgrind:
  |	valgrind --tool=massif --stacks=yes $${scalaPath} $${mainClass} $${input} $${output}
  |
  |clean:
  |	rm -rf *.class *.tasty
  |""".stripMargin
}
end template
