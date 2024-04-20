package symbol.statement.jump.returned;

import symbol.base.blank.Blank;
import symbol.base.keyword.Keyword;
import symbol.base.punctuator.semicolon.Semicolon;
import symbol.expression.comma.CommaExpression;
import symbol.foundation.code.Code;
import symbol.foundation.type.SymbolTypeName;
import symbol.statement.jump.JumpStatement;
import symbol.foundation.Symbol;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.warning.Danger;
import symbol.foundation.warning.Discouragement;

public class ReturnValueStatement extends JumpStatement {
    public final Keyword keywordReturn;
    public final Blank blankAfterKeywordReturn;
    public final CommaExpression commaExpression;
    public final Blank blankBeforeSemicolon;
    public final Semicolon semicolon;

    public ReturnValueStatement(Keyword keywordReturn,
                                Blank blankAfterKeywordReturn,
                                CommaExpression commaExpression,
                                Blank blankBeforeSemicolon,
                                Semicolon semicolon) {
        super(null, new Symbol[]{
                keywordReturn,
                blankAfterKeywordReturn,
                commaExpression,
                blankBeforeSemicolon,
                semicolon});
        this.keywordReturn = keywordReturn;
        this.blankAfterKeywordReturn = blankAfterKeywordReturn;
        this.commaExpression = commaExpression;
        this.blankBeforeSemicolon = blankBeforeSemicolon;
        this.semicolon = semicolon;
    }

    public static ReturnValueStatement parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            Keyword keywordReturn = Keyword.parse("return", code, table);
            Blank blankAfterKeywordReturn = Blank.parse(code, table);
            CommaExpression commaExpression = CommaExpression.parse(code, table);
            Blank blankBeforeSemicolon = Blank.parse(code, table);
            Semicolon semicolon = Semicolon.parse(code, table);
            ReturnValueStatement returnValueStatement = new ReturnValueStatement(
                    keywordReturn,
                    blankAfterKeywordReturn,
                    commaExpression,
                    blankBeforeSemicolon,
                    semicolon);
            if (SymbolTypeName.returnType(table.type(table.string)).isVoid()) {
                returnValueStatement.warnings.add(new Danger(returnValueStatement, commaExpression, "Excess of expressions in a return statement is dangerous for beginners."));
            } else {
                if (!SymbolTypeName.returnType(table.type(table.string)).equals(commaExpression.type)) {
                    returnValueStatement.warnings.add(new Discouragement(returnValueStatement, commaExpression, "Return statement with a expression whose type is incompatible is discouraged for beginners."));
                }
            }
            return returnValueStatement;
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
