package declaration.initialization;

import cache.blank.Blank;
import cache.punctuator.initialization.EqualPunctuator;
import declaration.declaration.InitialDeclarator;
import declaration.declarator.Declarator;
import expression.comma.CommaExpression;
import basis.node.Node;
import basis.code.Code;
import basis.type.Table;
import basis.invalidity.InvalidityException;
import basis.warning.Danger;

public class Initialization extends InitialDeclarator {
    public final Declarator declarator;
    public final Blank blankBeforeEqualPunctuator;
    public final EqualPunctuator equalPunctuator;
    public final Blank blankAfterEqualPunctuator;
    public final Initializer initializer;

    public Initialization(
            Declarator declarator,
            Blank blankBeforeEqualPunctuator,
            EqualPunctuator equalPunctuator,
            Blank blankAfterEqualPunctuator,
            Initializer initializer) {
        super(null, new Node[] {
                declarator,
                blankBeforeEqualPunctuator,
                equalPunctuator,
                blankAfterEqualPunctuator,
                initializer});
        this.declarator = declarator;
        this.blankBeforeEqualPunctuator = blankBeforeEqualPunctuator;
        this.equalPunctuator = equalPunctuator;
        this.blankAfterEqualPunctuator = blankAfterEqualPunctuator;
        this.initializer = initializer;
        if (initializer instanceof AssignmentExpressionInitializer assignmentExpressionInitializer) {
            if (CommaExpression.effective(assignmentExpressionInitializer.assignmentExpression)) {
                warnings.add(new Danger(this, assignmentExpressionInitializer, "Initialization with other side effects is dangerous for beginners."));
            }
        }
    }

    public static Initialization parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        try {
            Declarator declarator = Declarator.parse(code, table);
            Blank blankBeforeEqualPunctuator = Blank.parse(code, table);
            EqualPunctuator equalPunctuator = EqualPunctuator.parse(code, table);
            Blank blankAfterEqualPunctuator = Blank.parse(code, table);
            Initializer initializer = Initializer.parse(code, table);
            return new Initialization(
                    declarator,
                    blankBeforeEqualPunctuator,
                    equalPunctuator,
                    blankAfterEqualPunctuator,
                    initializer);
        } catch (InvalidityException invalidityException) {
            code.set(clone);
            throw invalidityException;
        }
    }
}
