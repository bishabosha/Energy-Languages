import java.util.Arrays;
import sRAPL.*;

public class JvmRunner {
  public static void main(String[] args) {
    int depth = Integer.parseInt(args[0]);

    String[] configArgs = Arrays.copyOfRange(args, 1, args.length);
    Settings config = Settings.parseArray(configArgs);

    Runnable benchmark = () -> {
      pidigits.run(depth);
    };

    Runner.measureEnergyConsumption(config, benchmark);
  }
}
