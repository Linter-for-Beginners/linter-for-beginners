package expression.assignment;

import basis.code.Code;
import basis.invalidity.InvalidityException;
import basis.type.Table;
import basis.node.Token;

public class AssignmentSign extends Token {
    private static final String[] strings = {"<<=", ">>=", "*=", "/=", "%=", "+=", "-=", "&=", "^=", "|=", "="};

    public AssignmentSign(Integer row, Integer column, String string) {
        super(row, column, null, string);
    }

    public static AssignmentSign parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        for (String string : strings) {
            if (code.startsWith(string)) {
                code.remove(string);
                return new AssignmentSign(row, column, string);
            }
        }
        throw new InvalidityException();
    }
}
