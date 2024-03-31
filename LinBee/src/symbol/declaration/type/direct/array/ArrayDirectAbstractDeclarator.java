package symbol.declaration.type.direct.array;

import symbol.base.blank.Blank;
import symbol.base.punctuator.bracket.LeftBracket;
import symbol.base.punctuator.bracket.RightBracket;
import symbol.declaration.declaration.DeclarationSpecifierList;
import symbol.declaration.type.direct.DirectAbstractDeclarator;
import symbol.expression.assignment.AssignmentExpression;
import symbol.expression.comma.CommaExpression;
import symbol.symbol.Symbol;
import symbol.symbol.type.Table;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.warning.Dangerous;
import symbol.symbol.warning.Discouraged;
import symbol.symbol.warning.Strange;

public class ArrayDirectAbstractDeclarator extends DirectAbstractDeclarator {
    public final DirectAbstractDeclarator directAbstractDeclarator;
    public final Blank blankAfterDirectAbstractDeclarator;
    public final LeftBracket leftBracket;
    public final Blank blankBeforeDeclarationSpecifierList;
    public final DeclarationSpecifierList declarationSpecifierList;
    public final Blank blankAfterDeclarationSpecifierList;
    public final AssignmentExpression assignmentExpression;
    public final Blank blankAfterAssignmentExpression;
    public final RightBracket rightBracket;

    public ArrayDirectAbstractDeclarator(
            DirectAbstractDeclarator directAbstractDeclarator,
            Blank blankAfterDirectAbstractDeclarator,
            LeftBracket leftBracket,
            Blank blankBeforeDeclarationSpecifierList,
            DeclarationSpecifierList declarationSpecifierList,
            Blank blankAfterDeclarationSpecifierList,
            AssignmentExpression assignmentExpression,
            Blank blankAfterAssignmentExpression,
            RightBracket rightBracket) {
        super(null, new Symbol[] {
                directAbstractDeclarator,
                blankAfterDirectAbstractDeclarator,
                leftBracket,
                blankBeforeDeclarationSpecifierList,
                declarationSpecifierList,
                blankAfterDeclarationSpecifierList,
                assignmentExpression,
                blankAfterAssignmentExpression,
                rightBracket});
        this.directAbstractDeclarator = directAbstractDeclarator;
        this.blankAfterDirectAbstractDeclarator = blankAfterDirectAbstractDeclarator;
        this.leftBracket = leftBracket;
        this.blankBeforeDeclarationSpecifierList = blankBeforeDeclarationSpecifierList;
        this.declarationSpecifierList = declarationSpecifierList;
        this.blankAfterDeclarationSpecifierList = blankAfterDeclarationSpecifierList;
        this.assignmentExpression = assignmentExpression;
        this.blankAfterAssignmentExpression = blankAfterAssignmentExpression;
        this.rightBracket = rightBracket;
        if (assignmentExpression == null) {
            warnings.add(new Strange(this));
        }
        if (assignmentExpression != null) {
            if (CommaExpression.controlling(assignmentExpression)) {
                warnings.add(new Discouraged(this, assignmentExpression));
            }
            if (CommaExpression.effective(assignmentExpression)) {
                warnings.add(new Dangerous(this, assignmentExpression));
            }
        }
    }

    public static ArrayDirectAbstractDeclarator parse(Sentence sentence, Table table) throws InvalidityException {
        Sentence clone = sentence.clone();
        DirectAbstractDeclarator directAbstractDeclarator = DirectAbstractDeclarator.parse(sentence, table);
        if (directAbstractDeclarator instanceof ArrayDirectAbstractDeclarator) {
            return (ArrayDirectAbstractDeclarator) directAbstractDeclarator;
        } else {
            sentence.set(clone);
            throw new InvalidityException();
        }
    }
}
