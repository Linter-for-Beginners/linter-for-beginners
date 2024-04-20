package expression.cast;

import cache.identifier.Identifier;
import expression.comma.CommaExpression;
import basis.node.Node;
import basis.node.Token;
import basis.type.Table;
import cache.blank.Blank;
import declaration.type.TypeName;
import cache.punctuator.parenthesis.LeftParenthesis;
import cache.punctuator.parenthesis.RightParenthesis;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.warning.Discouragement;
import basis.warning.Danger;

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
        super(type(typeName), new Node[] {
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
        Node[] nodes = typeName.traversal();
        HashSet<Node> visited = new HashSet<>();
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(null);
        for (Node node : nodes) {
            if (visited.contains(node)) {
                visited.remove(node);
            } else {
                visited.add(node);
                if (node instanceof Token token) {
                    if ((tokens.get(tokens.size() - 1) instanceof LeftParenthesis) && (token instanceof Blank)) {
                        continue;
                    }
                    if ((tokens.get(tokens.size() - 1) instanceof RightParenthesis) && (token instanceof Blank)) {
                        continue;
                    }
                    if ((tokens.get(tokens.size() - 1) instanceof Identifier) && (token instanceof Blank)) {
                        continue;
                    }
                    tokens.add(token);
                }
            }
        }
        tokens.add(null);
        while (true) {
            boolean disjunction = false;
            for (Token token : tokens) {
                if ((token instanceof Identifier) && (tokens.get(tokens.indexOf(token) - 1) instanceof LeftParenthesis leftParenthesis) && (tokens.get(tokens.indexOf(token) + 1) instanceof RightParenthesis rightParenthesis)) {
                    tokens.remove(leftParenthesis);
                    tokens.remove(rightParenthesis);
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
        for (Token token : tokens) {
            if (token == null) {
                continue;
            }
            if (token instanceof Identifier identifier) {
                if (string == null) {
                    string = identifier.toString();
                }
            } else {
                stringBuilder.append(token.toString());
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
