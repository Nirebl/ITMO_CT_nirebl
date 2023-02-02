package markup;

import java.util.List;

public final class Strikeout extends AbstractMarkup implements MarkupInterface {
    public Strikeout(List<MarkupInterface> value) {
        super(value);
    }

    @Override
    protected void putMarkdownOpenTag(StringBuilder s)
    {
        s.append("~");
    }

    @Override
    protected void putMarkdownCloseTag(StringBuilder s)
    {
        s.append("~");
    }

    @Override
    protected void putBBCodeOpenTag(StringBuilder s)
    {
        s.append("[s]");
    }

    @Override
    protected void putBBCodeCloseTag(StringBuilder s)
    {
        s.append("[/s]");
    }
}
