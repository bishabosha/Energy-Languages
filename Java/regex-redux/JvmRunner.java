import java.util.Arrays;
import java.io.*;
import sRAPL.*;

public class JvmRunner {
  public static void main(String[] args) {
    File inputFile = new File(args[0]);

    String[] configArgs = Arrays.copyOfRange(args, 1, args.length);
    Settings config = Settings.parseArray(configArgs);

    Runnable benchmark = () -> {
      try{
        regexredux.run(new FileInputStream(inputFile));
      } catch(Exception e){
        throw new RuntimeException("Fatal exception: " + e.getMessage());
      }
    };

    Runner.measureEnergyConsumption(config, benchmark);
  }
}
