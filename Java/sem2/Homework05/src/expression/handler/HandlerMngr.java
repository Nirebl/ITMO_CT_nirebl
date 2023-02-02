package expression.handler;

import java.util.Map;

public final class HandlerMngr {
    private final static Map<String, Handler<?>> HANDLERS = Map.of(
            "i", new CheckedIntHandler(),
            "d", new DoubleHandler(),
            "bi", new BigIntegerHandler(),
            "u", new IntHandler(),
            "l", new LongHandler(),
            "s", new ShortHandler(),
            "f", new FloatHandler()
    );

    public static Handler<?> getHandlerByMode(String mode) {
        return HANDLERS.get(mode);
    }
}
