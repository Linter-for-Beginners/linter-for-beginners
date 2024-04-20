package basis.code;

public class Code implements Cloneable {
    private String string;
    private Integer row;
    private Integer column;

    public Code(String string) {
        this.string = string;
        this.row = 0;
        this.column = 0;
    }

    private Code(Code code) {
        this.string = code.string;
        this.row = code.row;
        this.column = code.column;
    }

    @Override
    public String toString() {
        return string;
    }

    @Override
    public Code clone() {
        return new Code(this);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void set(Code code) {
        this.string = code.string;
        this.row = code.row;
        this.column = code.column;
    }

    public boolean startsWith(String string) {
        return this.string.startsWith(string);
    }

    public void remove(String string) {
        if (!startsWith(string)) {
            throw new RuntimeException("bug of LinBee23");
        }
        for (int i = 0; i < string.length(); i++) {
            if (this.string.startsWith("\n", i)) {
                row++;
                column = 0;
            } else {
                column += "\n".length();
            }
        }
        this.string = this.string.substring(string.length());
    }
}
