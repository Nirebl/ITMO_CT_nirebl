package markup;

import java.util.List;

public final class Emphasis extends AbstractMarkup implements MarkupInterface {
    public Emphasis(List<MarkupInterface> elements) {
        super(elements);
    }

    @Override
    protected void putMarkdownOpenTag(StringBuilder s)
    {
        s.append("*");
    }

    @Override
    protected void putMarkdownCloseTag(StringBuilder s)
    {
        s.append("*");
    }

    @Override
    protected void putBBCodeOpenTag(StringBuilder s)
    {
        s.append("[i]");
    }

    @Override
    protected void putBBCodeCloseTag(StringBuilder s)
    {
        s.append("[/i]");
    }
}
