package declaration.type.direct.array;

import cache.blank.Blank;
import cache.punctuator.bracket.LeftBracket;
import cache.punctuator.bracket.RightBracket;
import declaration.declaration.DeclarationSpecifierList;
import declaration.type.direct.DirectAbstractDeclarator;
import expression.assignment.AssignmentExpression;
import expression.comma.CommaExpression;
import basis.node.Node;
import basis.type.Table;
import basis.invalidity.InvalidityException;
import basis.code.Code;
import basis.warning.Danger;
import basis.warning.Discouragement;
import basis.warning.Strangeness;

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
        super(null, new Node[] {
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
            warnings.add(new Strangeness(this, this, "Declarator of an array whose length is implicit is strange for beginners."));
        }
        if (assignmentExpression != null) {
            if (CommaExpression.controlling(assignmentExpression)) {
                warnings.add(new Discouragement(this, assignmentExpression, "Declarator of an array whose length is a boolean form discouraged for beginners."));
            }
            if (CommaExpression.effective(assignmentExpression)) {
                warnings.add(new Danger(this, assignmentExpression, "Declarator of an array with side effects is dangerous for beginners."));
            }
        }
    }

    public static ArrayDirectAbstractDeclarator parse(Code code, Table table) throws InvalidityException {
        Code clone = code.clone();
        DirectAbstractDeclarator directAbstractDeclarator = DirectAbstractDeclarator.parse(code, table);
        if (directAbstractDeclarator instanceof ArrayDirectAbstractDeclarator) {
            return (ArrayDirectAbstractDeclarator) directAbstractDeclarator;
        } else {
            code.set(clone);
            throw new InvalidityException();
        }
    }
}
