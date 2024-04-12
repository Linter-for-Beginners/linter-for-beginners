package symbol.declaration.declarator.direct.array;

import symbol.base.blank.Blank;
import symbol.base.punctuator.bracket.LeftBracket;
import symbol.base.punctuator.bracket.RightBracket;
import symbol.declaration.declaration.DeclarationSpecifierList;
import symbol.declaration.declarator.direct.DirectDeclarator;
import symbol.expression.assignment.AssignmentExpression;
import symbol.expression.comma.CommaExpression;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Danger;
import symbol.symbol.warning.Discouragement;
import symbol.symbol.warning.Strangeness;

public class ArrayDirectDeclarator extends DirectDeclarator {
    public final DirectDeclarator directDeclarator;
    public final Blank blankAfterDirectDeclarator;
    public final LeftBracket leftBracket;
    public final Blank blankBeforeDeclarationSpecifierList;
    public final DeclarationSpecifierList declarationSpecifierList;
    public final Blank blankAfterDeclarationSpecifierList;
    public final AssignmentExpression assignmentExpression;
    public final Blank blankAfterAssignmentExpression;
    public final RightBracket rightBracket;

    public ArrayDirectDeclarator(
            DirectDeclarator directDeclarator,
            Blank blankAfterDirectDeclarator,
            LeftBracket leftBracket,
            Blank blankBeforeDeclarationSpecifierList,
            DeclarationSpecifierList declarationSpecifierList,
            Blank blankAfterDeclarationSpecifierList,
            AssignmentExpression assignmentExpression,
            Blank blankAfterAssignmentExpression,
            RightBracket rightBracket) {
        super(null, new Symbol[] {
                directDeclarator,
                blankAfterDirectDeclarator,
                leftBracket,
                blankBeforeDeclarationSpecifierList,
                declarationSpecifierList,
                blankAfterDeclarationSpecifierList,
                assignmentExpression,
                blankAfterAssignmentExpression,
                rightBracket});
        this.directDeclarator = directDeclarator;
        this.blankAfterDirectDeclarator = blankAfterDirectDeclarator;
        this.leftBracket = leftBracket;
        this.blankBeforeDeclarationSpecifierList = blankBeforeDeclarationSpecifierList;
        this.declarationSpecifierList = declarationSpecifierList;
        this.blankAfterDeclarationSpecifierList = blankAfterDeclarationSpecifierList;
        this.assignmentExpression = assignmentExpression;
        this.blankAfterAssignmentExpression = blankAfterAssignmentExpression;
        this.rightBracket = rightBracket;
        if (assignmentExpression == null) {
            warnings.add(new Strangeness(this, this, "Declaration of an array whose length is implicit is strange for beginners."));
        } else {
            if (CommaExpression.controlling(assignmentExpression)) {
                warnings.add(new Discouragement(this, assignmentExpression, "Declaration of an array whose length is a boolean form discouraged for beginners."));
            }
            if (CommaExpression.effective(assignmentExpression)) {
                warnings.add(new Danger(this, assignmentExpression, "Declaration of an array with side effects is dangerous for beginners."));
            }
        }
    }

    public static ArrayDirectDeclarator parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        DirectDeclarator directDeclarator = DirectDeclarator.parse(sentence, table);
        if (directDeclarator instanceof ArrayDirectDeclarator) {
            return (ArrayDirectDeclarator) directDeclarator;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}
