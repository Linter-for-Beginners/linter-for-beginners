package symbol.symbol.sentence;

public class Sentence implements Cloneable {
    private String string;
    private Integer row;
    private Integer column;

    public Sentence(String string) {
        this.string = string;
        this.row = 0;
        this.column = 0;
    }

    public Sentence(Sentence sentence) {
        this.string = sentence.string;
        this.row = sentence.row;
        this.column = sentence.column;
    }

    @Override
    public String toString() {
        return string;
    }

    @Override
    public Sentence clone() {
        return new Sentence(this);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void set(Sentence sentence) {
        this.string = sentence.string;
        this.row = sentence.row;
        this.column = sentence.column;
    }

    public void remove(int length) {
        for (int i = 0; i < length; i++) {
            if (string.startsWith("\n", i)) {
                row++;
                column = 0;
            } else {
                column++;
            }
        }
        string = string.substring(length);
    }

    public boolean startsWith(String string) {
        return this.string.startsWith(string);
    }
}
