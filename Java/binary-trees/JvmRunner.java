import java.util.Arrays;
import sRAPL.*;

public class JvmRunner {
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);

    String[] configArgs = Arrays.copyOfRange(args, 1, args.length);
    Settings config = Settings.parseArray(configArgs);

    Runnable benchmark = () -> {
      try {
        binarytrees.run(n);
      } catch (Exception e) {
        System.err.println("Fatal exception " + e.getMessage());
        System.exit(1);
      }
    };

    Runner.measureEnergyConsumption(config, benchmark);
  }
}
