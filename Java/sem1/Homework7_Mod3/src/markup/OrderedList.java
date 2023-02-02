package markup;

import java.util.List;

public final class OrderedList extends AbstractList implements ListPutable {

    public OrderedList(List<ListItem> elements) {
        super(elements);
    }

    @Override
    protected void putBBCodeOpenTag(StringBuilder s)
    {
        s.append("[list=1]");
    }

    @Override
    protected void putBBCodeCloseTag(StringBuilder s)
    {
        s.append("[/list]");
    }
}
