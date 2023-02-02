package markup;

import java.util.List;

public class ListItem implements ListPutable {
    List<ListPutable> list;

    public ListItem(List<ListPutable> list) {
        this.list = list;
    }

    public void toBBCode(StringBuilder s) {
        s.append("[*]");
        for (ListPutable element : list) {
            element.toBBCode(s);
        }
    }
}
