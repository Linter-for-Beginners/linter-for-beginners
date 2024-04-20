package symbol.statement.jump.returned;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.semicolon.Semicolon;
import symbol.foundation.code.Code;
import symbol.foundation.type.SymbolTypeName;
import symbol.statement.jump.JumpStatement;
import symbol.foundation.Symbol;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.warning.Danger;

public class ReturnNoneStatement extends JumpStatement {
    public final Keyword keywordReturn;
    public final Blank blankBeforeSemicolon;
    public final Semicolon semicolon;

    public ReturnNoneStatement(Keyword keywordReturn,
                               Blank blankBeforeSemicolon,
                               Semicolon semicolon) {
        super(null, new Symbol[]{
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
