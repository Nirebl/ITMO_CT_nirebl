package md2html;

import java.util.List;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Md2HtmlPreTest extends Md2HtmlTest {
    private boolean nested;

    @Override
    protected void test() {
        test("```код __без__ форматирования```", "<p><pre>код __без__ форматирования</pre></p>");
        test(
                "Это не `\\``код __без__ форматирования``\\`",
                "<p>Это не <code>`</code>код <strong>без</strong> форматирования<code></code>`</p>"
        );
        super.test();

        mediumRandomTest("_", "**", "`", "--", "<<", "}}", "```");
        mediumRandomTest("*", "__", "`", "--", "<<", "}}", "```");
        hugeRandomTest("*", "__", "`", "--", "<<", "}}", "```");
    }

    @Override
    protected void special(final List<String> markup, final StringBuilder input, final StringBuilder output, final String type) {
        if (nested) {
            return;
        }

        nested = true;
        final StringBuilder contents = new StringBuilder();
        generate(markup, contents, new StringBuilder());
        word(contents, new StringBuilder());

        input.append("```").append(contents).append("```");
        output.append("<pre>").append(contents).append("</pre>");
        nested = false;
    }

    public static void main(final String... args) {
        new Md2HtmlPreTest().run();
    }
}