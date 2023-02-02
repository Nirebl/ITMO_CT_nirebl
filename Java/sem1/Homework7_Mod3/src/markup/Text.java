package markup;

public class Text implements MarkupInterface {
    String val;

    public Text(String val) {
        this.val = val;
    }

    private void Fill(StringBuilder s) {
        s.append(val);
    }

    @Override
    public void toMarkdown(StringBuilder s) {
        Fill(s);
    }

    @Override
    public void toBBCode(StringBuilder s) {
        Fill(s);
    }
}
