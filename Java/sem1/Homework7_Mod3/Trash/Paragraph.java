package markup;

import java.util.List;

public class Paragraph implements ListPutable {
    List<Markup> p;

    public Paragraph(List<Markup> p) {
        this.p = p;
    }

    public void toMarkdown(StringBuilder markdown) {
        for (Markup i : p) {
            i.toMarkdown(markdown);
        }
    }

    @Override
    public void toBBCode(StringBuilder s) {
        for (Markup i : p) {
            i.toBBCode(s);
        }
    }
}
