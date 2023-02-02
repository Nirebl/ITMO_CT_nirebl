package markup;

import java.util.List;

public final class Strong extends AbstractMarkup implements MarkupInterface {
    public Strong(List<MarkupInterface> value) {
        super(value);
    }

    @Override
    protected void putMarkdownOpenTag(StringBuilder s)
    {
        s.append("__");
    }

    @Override
    protected void putMarkdownCloseTag(StringBuilder s)
    {
        s.append("__");
    }

    @Override
    protected void putBBCodeOpenTag(StringBuilder s)
    {
        s.append("[b]");
    }

    @Override
    protected void putBBCodeCloseTag(StringBuilder s)
    {
        s.append("[/b]");
    }

}
