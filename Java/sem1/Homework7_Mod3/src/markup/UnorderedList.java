package markup;

import java.util.List;

public class UnorderedList extends AbstractList implements ListPutable {
    public UnorderedList(List<ListItem> elements) {
        super(elements);
    }

    @Override
    protected void putBBCodeOpenTag(StringBuilder s)
    {
        s.append("[list]");
    }

    @Override
    protected void putBBCodeCloseTag(StringBuilder s)
    {
        s.append("[/list]");
    }

}
