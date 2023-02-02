package markup;

import java.util.List;

public abstract class AbstractList implements ListPutable{
    List<ListItem> elements;

    AbstractList(List<ListItem> elements) {
        this.elements = elements;
    }

    protected abstract void putBBCodeOpenTag(StringBuilder s);
    protected abstract void putBBCodeCloseTag(StringBuilder s);

    @Override
    public void toBBCode(StringBuilder s) {
        putBBCodeOpenTag(s);
        for (ListItem element : elements) {
            element.toBBCode(s);
        }
        putBBCodeCloseTag(s);
    }
}
