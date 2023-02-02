package prtest.tree;

import base.Selector;
import base.TestCounter;
import prtest.map.MapChecker;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public final class PrologTreeTest {
    public static final Selector SELECTOR = new Selector(PrologTreeTest.class, "easy", "hard")
            .variant("base", variant(tests -> {}))
            ;

    private PrologTreeTest() {
    }

    @SafeVarargs
    /* package-private */ static Consumer<TestCounter> variant(final Consumer<MapChecker<Void>>... addTests) {
        return variant(false, addTests);
    }

    @SafeVarargs
    /* package-private */ static Consumer<TestCounter> variant(final boolean alwaysUpdate, final Consumer<MapChecker<Void>>... addTests) {
        return counter -> {
            final boolean hard = counter.mode() == 1;
            PrologTreeTester.test(
                    counter, hard || alwaysUpdate, !hard,
                    tests -> Arrays.stream(addTests).forEachOrdered(adder -> adder.accept(tests))
            );
        };
    }

    public static void main(final String... args) {
        SELECTOR.main(args);
    }
}
