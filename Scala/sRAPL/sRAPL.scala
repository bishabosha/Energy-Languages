// using scala 3.1
// using lib jRAPL::jRAPL:1.0

package sRAPL

import jRAPL._
import java.io._

@main def test(): Unit = {
given config: Settings = Settings(warmupConfig = WarmupSettings(measureWarmup = true))
 measureEnergyConsumption("test"){ () =>
   (0 until 10).foreach{_ =>
   util.Random.alphanumeric.take(1000000).count(_ == 'A')
   }
 } 
}

def measureEnergyConsumption(
    label: String
)(fn: () => Unit)(using config: Settings): Unit = {
  withFreshSyncMonitor {
    withConfigPrintStream {
      val warmupConfig = config.warmupConfig
      for
        i <- 0 until warmupConfig.iterations
        // input = warmupConfig.input.fold(
        //   throw RuntimeException("Warmup input not defined")
        // )(_.inputStream())
      do
        println(s"$label warmup - iteration $i")
        if warmupConfig.measureWarmup then
          measureEnergyConsumptionSample(s"$label-warmup_$i")(fn())
        else fn()
      end for

      for i <- 0 until 10
      do  measureEnergyConsumptionSample(label)(fn())
    }
  }
}

private def measureEnergyConsumptionSample[T](
    label: String
)(
    fn: => T
)(using output: PrintStream)(using Settings, SyncEnergyMonitor): T = {
  val (sample, result) = executeAndMeasureEnergyConsumption(fn)
  output.println(sample.toCSV(label))
  result
}

private def executeAndMeasureEnergyConsumption[T](
    fn: => T
)(using monitor: SyncEnergyMonitor): (EnergyDiff, T) = {
  val before = monitor.getSample()
  val result = fn
  val after = monitor.getSample()
  EnergyDiff.between(before, after) -> result
}

private def withFreshSyncMonitor[T](fn: SyncEnergyMonitor ?=> T): T = {
  val monitor = SyncEnergyMonitor()
  monitor.activate()
  try fn(using monitor)
  finally monitor.deactivate()
}

private def withConfigPrintStream[T](using
    config: Settings
)(fn: PrintStream ?=> T): T = {
  val MeasurmentCsvHeader = "Name, Package (J), CPU (J), GPU (J), DRAM(J), Time (s)"
  val (printStream, writeHeader) = config.resultFile match {
    case None => System.out -> true
    case Some(file) =>
      val shouldWriteHeader = file.length() == 0
      PrintStream(file) -> shouldWriteHeader
  }
  if (writeHeader)
    printStream.println(MeasurmentCsvHeader)

  try fn(using printStream)
  finally config.resultFile.foreach(_ => printStream.close())
}

extension (diff: EnergyDiff)
  def toCSV(label: String) = List(
    label,
    diff.getPackage(),
    diff.getCore(),
    f"${diff.getGpu()}%.1f",
    diff.getDram(),
    diff.getTimeElapsed().toMillis / 1000.0
  ).mkString(", ")

case class Settings(
    benchmarkArgs: List[String] = Nil,
    resultFile: Option[File] = None,
    warmupConfig: WarmupSettings = WarmupSettings()
) {
  def withWarmupConfig(fn: WarmupSettings => WarmupSettings): Settings = {
    copy(warmupConfig = fn(warmupConfig))
  }
  override val toString = {
    s"""Settings {
    |  benchmark input: '${benchmarkArgs.mkString(" ")}'
    |  results file: ${resultFile.getOrElse("<stdout>")},
    |  warmupConfig: ${warmupConfig}
    |}""".stripMargin
  }
}

// Config model
case class WarmupSettings(
    iterations: Int = 10,
    input: Option[WarmupInput] = None,
    measureWarmup: Boolean = false
) {
  override val toString = {
    def inputString = input match {
      case None                             => "none"
      case Some(WarmupInput.Argument(arg))  => s"'$arg'"
      case Some(WarmupInput.FromFile(file)) => s"file $file"
    }
    s"""Warmup Settings {
      |  iterations:      $iterations
      |  warmup input:    $inputString
      |  measure warmup:  $measureWarmup
      |}""".stripMargin
  }
}

object Settings {
  def parse(args: Seq[String]): Settings = {
    args
      .collect {
        case str if !str.isEmpty => str.trim
      } // Split argument word into groups
      .foldLeft(List.empty[List[String]]) {
        case (Nil, word) => List(word :: Nil)
        case (groups @ lastGroup :: prevGroups, word) =>
          if word.startsWith("--") then // start of new group
            val newGroup = word :: Nil
            newGroup :: groups
          else
            val updatedGroup = lastGroup :+ word
            updatedGroup :: prevGroups
          end if
      }
      .reverse
      // Parse groups of arguments
      .foldRight(Settings()) {
        case ("--" :: benchArgs, config) =>
          config.copy(benchmarkArgs = benchArgs)
        case ("--output" :: path :: Nil, config) =>
          config.copy(resultFile = Some(new File(path).getAbsoluteFile))
        // Warmup config
        case ("--iterations" :: n :: Nil, config) =>
          config.withWarmupConfig { _.copy(iterations = n.toInt) }
        case ("--input" :: "file" :: path :: Nil, config) =>
          config.withWarmupConfig {
            _.copy(input =
              Some(WarmupInput.FromFile(new File(path).getAbsoluteFile))
            )
          }
        case ("--input" :: arg :: Nil, config) =>
          config.withWarmupConfig {
            _.copy(input = Some(WarmupInput.Argument(arg)))
          }
        case ("--measure-warmup" :: Nil, config) =>
          config.withWarmupConfig { _.copy(measureWarmup = true) }
        case (other, config) =>
          System.err.println(s"Unknown argument: $other")
          config
      }
  }
}

sealed trait WarmupInput:
  def inputStream(): InputStream

object WarmupInput {
  case class Argument(arg: String) extends WarmupInput:
    def inputStream(): InputStream = new ByteArrayInputStream(arg.getBytes)
  case class FromFile(file: File) extends WarmupInput:
    def inputStream(): InputStream = new FileInputStream(file)
}


// Name,         Package,    CPU, GPU,      DRAM, Time (sec)
// binary-trees, 251953.0,  0.0, -167543.0, 5.0, 1.933
// binary-trees, 232422.0,  0.0, 437500.0,  4.0, 1.891
// binary-trees, 208984.0,  0.0, -167544.0, 5.0, 1.928
// binary-trees, -480043.0, 1.0, 54688.0,   5.0, 2.144
// binary-trees, 226562.0,  0.0, 406250.0,  4.0, 1.837
// binary-trees, 267578.0,  0.0, -89418.0,  5.0, 1.94
// binary-trees, 251954.0,  0.0, 148437.0,  5.0, 2.145
// binary-trees, -483950.0, 1.0, 531250.0,  4.0, 1.914
// binary-trees, 203125.0,  0.0, -362856.0, 5.0, 1.833
// binary-trees, 265625.0,  0.0, -11293.0,  5.0, 1.905