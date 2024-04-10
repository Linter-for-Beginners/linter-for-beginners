package symbol.declaration.initialization;

import symbol.base.blank.Blank;
import symbol.base.punctuator.initialization.EqualPunctuator;
import symbol.declaration.declaration.InitialDeclarator;
import symbol.declaration.declarator.Declarator;
import symbol.expression.comma.CommaExpression;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Danger;

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
        super(null, new Symbol[] {
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

    public static Initialization parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        try {
            Declarator declarator = Declarator.parse(sentence, table);
            Blank blankBeforeEqualPunctuator = Blank.parse(sentence, table);
            EqualPunctuator equalPunctuator = EqualPunctuator.parse(sentence, table);
            Blank blankAfterEqualPunctuator = Blank.parse(sentence, table);
            Initializer initializer = Initializer.parse(sentence, table);
            return new Initialization(
                    declarator,
                    blankBeforeEqualPunctuator,
                    equalPunctuator,
                    blankAfterEqualPunctuator,
                    initializer);
        } catch (InvalidityException invalidityException) {
            sentence.set(clone);
            throw invalidityException;
        }
    }
}
