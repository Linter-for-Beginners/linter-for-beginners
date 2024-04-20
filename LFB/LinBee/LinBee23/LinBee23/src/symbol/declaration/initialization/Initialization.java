package symbol.declaration.initialization;

import symbol.base.blank.Blank;
import symbol.base.punctuator.initialization.EqualPunctuator;
import symbol.declaration.declaration.InitialDeclarator;
import symbol.declaration.declarator.Declarator;
import symbol.expression.comma.CommaExpression;
import symbol.foundation.node.Node;
import symbol.foundation.code.Code;
import symbol.foundation.type.Table;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.warning.Danger;

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
