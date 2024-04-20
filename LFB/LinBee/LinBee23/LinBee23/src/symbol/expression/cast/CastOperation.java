package symbol.expression.cast;

import symbol.base.identifier.Identifier;
import symbol.expression.comma.CommaExpression;
import symbol.foundation.type.Table;
import symbol.foundation.*;
import symbol.base.blank.Blank;
import symbol.declaration.type.TypeName;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.code.Code;
import symbol.foundation.warning.Discouragement;
import symbol.foundation.warning.Danger;

import java.util.ArrayList;
import java.util.HashSet;

public class CastOperation extends CastExpression {
    public final LeftParenthesis leftParenthesis;
    public final Blank blankBeforeTypeName;
    public final TypeName typeName;
    public final Blank blankAfterTypeName;
    public final RightParenthesis rightParenthesis;
    public final Blank blankBeforeCastExpression;
    public final CastExpression castExpression;

    public CastOperation(LeftParenthesis leftParenthesis,
                         Blank blankBeforeTypeName,
                         TypeName typeName,
                         Blank blankAfterTypeName,
                         RightParenthesis rightParenthesis,
                         Blank blankBeforeCastExpression,
                         CastExpression castExpression) {
        super(type(typeName), new Symbol[] {
                leftParenthesis,
                blankBeforeTypeName,
                typeName,
                blankAfterTypeName,
                rightParenthesis,
                blankBeforeCastExpression,
                castExpression});
        this.leftParenthesis = leftParenthesis;
        this.blankBeforeTypeName = blankBeforeTypeName;
        this.typeName = typeName;
        this.blankAfterTypeName = blankAfterTypeName;
        this.rightParenthesis = rightParenthesis;
        this.blankBeforeCastExpression = blankBeforeCastExpression;
        this.castExpression = castExpression;
        if (CommaExpression.controlling(castExpression)) {
            warnings.add(new Discouragement(this, castExpression, "Cast operation of a boolean form is discouraged for beginners."));
        }
        if (CommaExpression.effective(castExpression)) {
            warnings.add(new Danger(this, castExpression, "Cast operation with side effects is dangerous for beginners."));
        }
    }

    private static SymbolTypeName type(TypeName typeName) {
        Symbol[] symbols = typeName.traversal();
        HashSet<Symbol> visited = new HashSet<>();
        ArrayList<Terminal> terminals = new ArrayList<>();
        terminals.add(null);
        for (Symbol symbol : symbols) {
            if (visited.contains(symbol)) {
                visited.remove(symbol);
            } else {
                visited.add(symbol);
                if (symbol instanceof Terminal terminal) {
                    if ((terminals.get(terminals.size() - 1) instanceof LeftParenthesis) && (terminal instanceof Blank)) {
                        continue;
                    }
                    if ((terminals.get(terminals.size() - 1) instanceof RightParenthesis) && (terminal instanceof Blank)) {
                        continue;
                    }
                    if ((terminals.get(terminals.size() - 1) instanceof Identifier) && (terminal instanceof Blank)) {
                        continue;
                    }
                    terminals.add(terminal);
                }
            }
        }
        terminals.add(null);
        while (true) {
            boolean disjunction = false;
            for (Terminal terminal : terminals) {
                if ((terminal instanceof Identifier) && (terminals.get(terminals.indexOf(terminal) - 1) instanceof LeftParenthesis leftParenthesis) && (terminals.get(terminals.indexOf(terminal) + 1) instanceof RightParenthesis rightParenthesis)) {
                    terminals.remove(leftParenthesis);
                    terminals.remove(rightParenthesis);
                    disjunction = true;
                    break;
                }
            }
            if (!disjunction) {
                break;
            }
        }
        String string = null;
        StringBuilder stringBuilder = new StringBuilder();
        for (Terminal terminal : terminals) {
            if (terminal == null) {
                continue;
            }
            if (terminal instanceof Identifier identifier) {
                if (string == null) {
                    string = identifier.toString();
                }
            } else {
                stringBuilder.append(terminal.toString());
            }
        }
        return new SymbolTypeName(stringBuilder.toString().replaceAll("\\s+", " ").trim());
    }

    public static CastOperation parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(code, table);
            Blank blankBeforeTypeName = Blank.parse(code, table);
            TypeName typeName = TypeName.parse(code, table);
            Blank blankAfterTypeName = Blank.parse(code, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(code, table);
            Blank blankBeforeCastExpression = Blank.parse(code, table);
            CastExpression castExpression = CastExpression.parse(code, table);
            return new CastOperation(
                    leftParenthesis,
                    blankBeforeTypeName,
                    typeName,
                    blankAfterTypeName,
                    rightParenthesis,
                    blankBeforeCastExpression,
                    castExpression);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
