import java.util.Arrays;
import sRAPL.*;

public class JvmRunner {
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);

    String[] configArgs = Arrays.copyOfRange(args, 1, args.length);
    Settings config = Settings.parseArray(configArgs);

    Runnable benchmark = () -> {
        fannkuchredux.run(n);
    };

    Runner.measureEnergyConsumption(config, benchmark);
  }
}
