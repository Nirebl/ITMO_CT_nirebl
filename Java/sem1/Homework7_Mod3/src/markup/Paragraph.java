package markup;

import java.util.List;

public final class Paragraph implements ListPutable{
    List<MarkupInterface> elements;

    public Paragraph(List<MarkupInterface> elements) {
        this.elements = elements;
    }

    @Override
    public void toBBCode(StringBuilder s) {
        for (MarkupInterface element : elements) {
            element.toBBCode(s);
        }
    }

    public void toMarkdown(StringBuilder s) {
        for (MarkupInterface element : elements) {
            element.toMarkdown(s);
        }
    }

}
