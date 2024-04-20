package statement.jump.returned;

import cache.blank.Blank;
import cache.keyword.Keyword;
import cache.punctuator.semicolon.Semicolon;
import basis.node.Node;
import basis.code.Code;
import basis.type.SymbolTypeName;
import statement.jump.JumpStatement;
import basis.type.Table;
import basis.invalidity.InvalidityException;
import basis.warning.Danger;

public class ReturnNoneStatement extends JumpStatement {
    public final Keyword keywordReturn;
    public final Blank blankBeforeSemicolon;
    public final Semicolon semicolon;

    public ReturnNoneStatement(Keyword keywordReturn,
                               Blank blankBeforeSemicolon,
                               Semicolon semicolon) {
        super(null, new Node[]{
                keywordReturn,
                blankBeforeSemicolon,
                semicolon});
        this.keywordReturn = keywordReturn;
        this.blankBeforeSemicolon = blankBeforeSemicolon;
        this.semicolon = semicolon;
    }

    public static ReturnNoneStatement parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            Keyword keywordReturn = Keyword.parse("return", code, table);
            Blank blankBeforeSemicolon = Blank.parse(code, table);
            Semicolon semicolon = Semicolon.parse(code, table);
            ReturnNoneStatement returnNoneStatement = new ReturnNoneStatement(
                    keywordReturn,
                    blankBeforeSemicolon,
                    semicolon);
            if (!SymbolTypeName.returnType(table.type(table.string)).isVoid()) {
                returnNoneStatement.warnings.add(new Danger(returnNoneStatement, returnNoneStatement, "Lack of expressions in a return statement is dangerous for beginners."));
            }
            return returnNoneStatement;
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
