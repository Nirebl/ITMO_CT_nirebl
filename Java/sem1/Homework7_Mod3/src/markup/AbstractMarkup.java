package markup;

import java.util.List;

public abstract class AbstractMarkup implements MarkupInterface {
    private List<MarkupInterface> elements;

    public AbstractMarkup(List<MarkupInterface> elements) {
        this.elements = elements;
    }

    abstract protected void putMarkdownOpenTag(StringBuilder s);
    abstract protected void putMarkdownCloseTag(StringBuilder s);

    @Override
    public void toMarkdown(StringBuilder s) {
        putMarkdownOpenTag(s);
        for (MarkupInterface element : elements) {
            element.toMarkdown(s);
        }
        putMarkdownCloseTag(s);
    }

    protected abstract void putBBCodeOpenTag(StringBuilder s);
    protected abstract void putBBCodeCloseTag(StringBuilder s);

    @Override
    public void toBBCode(StringBuilder s) {
        putBBCodeOpenTag(s);
        for (MarkupInterface element : elements) {
            element.toBBCode(s);
        }
        putBBCodeCloseTag(s);
    }
}

