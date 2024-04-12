package symbol.expression.postfix.compound;

import symbol.base.identifier.Identifier;
import symbol.declaration.initialization.InitializerList;
import symbol.declaration.type.TypeName;
import symbol.symbol.type.Table;
import symbol.expression.postfix.PostfixExpression;
import symbol.base.blank.Blank;
import symbol.base.punctuator.brace.LeftBrace;
import symbol.base.punctuator.brace.RightBrace;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.symbol.*;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Strangeness;

import java.util.ArrayList;
import java.util.HashSet;

public class CompoundLiteral extends PostfixExpression {
    public final LeftParenthesis leftParenthesis;
    public final Blank blankBeforeTypeName;
    public final TypeName typeName;
    public final Blank blankAfterTypeName;
    public final RightParenthesis rightParenthesis;
    public final Blank blankBeforeBracedInitializerList;
    public final LeftBrace leftBrace;
    public final Blank blankBeforeInitializerList;
    public final InitializerList initializerList;
    public final Blank blankAfterInitializerList;
    public final RightBrace rightBrace;

    public CompoundLiteral(LeftParenthesis leftParenthesis,
                           Blank blankBeforeTypeName,
                           TypeName typeName,
                           Blank blankAfterTypeName,
                           RightParenthesis rightParenthesis,
                           Blank blankBeforeBracedInitializerList,
                           LeftBrace leftBrace,
                           Blank blankBeforeInitializerList,
                           InitializerList initializerList,
                           Blank blankAfterInitializerList,
                           RightBrace rightBrace) {
        super(type(typeName), new Symbol[] {
                leftParenthesis,
                blankBeforeTypeName,
                typeName,
                blankAfterTypeName,
                rightParenthesis,
                blankBeforeBracedInitializerList,
                leftBrace,
                blankBeforeInitializerList,
                initializerList,
                blankAfterInitializerList,
                rightBrace});
        this.leftParenthesis = leftParenthesis;
        this.blankBeforeTypeName = blankBeforeTypeName;
        this.typeName = typeName;
        this.blankAfterTypeName = blankAfterTypeName;
        this.rightParenthesis = rightParenthesis;
        this.blankBeforeBracedInitializerList = blankBeforeBracedInitializerList;
        this.leftBrace = leftBrace;
        this.blankBeforeInitializerList = blankBeforeInitializerList;
        this.initializerList = initializerList;
        this.blankAfterInitializerList = blankAfterInitializerList;
        this.rightBrace = rightBrace;
        warnings.add(new Strangeness(this, this, "Compound literal is strange for beginners."));
    }

    private static SymbolTypeName type(TypeName typeName) {
        ArrayList<Symbol> symbols = typeName.traversal(new ArrayList<>());
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
        return SymbolTypeName.parse(stringBuilder.toString().replaceAll("\\s+", " ").trim());
    }

    public static CompoundLiteral parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            LeftParenthesis leftParenthesis = LeftParenthesis.parse(sentence, table);
            Blank blankBeforeTypeName = Blank.parse(sentence, table);
            TypeName typeName = TypeName.parse(sentence, table);
            Blank blankAfterTypeName = Blank.parse(sentence, table);
            RightParenthesis rightParenthesis = RightParenthesis.parse(sentence, table);
            Blank blankBeforeBracedInitializerList = Blank.parse(sentence, table);
            LeftBrace leftBrace = LeftBrace.parse(sentence, table);
            Blank blankBeforeInitializerList = Blank.parse(sentence, table);
            InitializerList initializerList = InitializerList.parse(sentence, table);
            Blank blankAfterInitializerList = Blank.parse(sentence, table);
            RightBrace rightBrace = RightBrace.parse(sentence, table);
            return new CompoundLiteral(
                    leftParenthesis,
                    blankBeforeTypeName,
                    typeName,
                    blankAfterTypeName,
                    rightParenthesis,
                    blankBeforeBracedInitializerList,
                    leftBrace,
                    blankBeforeInitializerList,
                    initializerList,
                    blankAfterInitializerList,
                    rightBrace);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
