package expression.comma;

import expression.assignment.AssignmentOperation;
import expression.equality.EqualityOperation;
import expression.logical.and.LogicalAndOperation;
import expression.logical.or.LogicalOrOperation;
import expression.postfix.decrement.PostfixDecrementOperation;
import expression.postfix.function.FunctionCall;
import expression.postfix.increment.PostfixIncrementOperation;
import expression.primary.expression.ParenthesizedCommaExpression;
import expression.relation.RelationalOperation;
import expression.unary.decrement.PrefixDecrementOperation;
import expression.unary.increment.PrefixIncrementOperation;
import expression.unary.negation.LogicalNegationOperation;
import basis.code.Code;
import basis.node.Node;
import basis.node.Phrase;
import basis.type.Table;
import cache.blank.Blank;
import expression.assignment.AssignmentExpression;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

public abstract class CommaExpression extends Phrase {
    public CommaExpression(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static CommaExpression parse(Code code, Table table) throws InvalidityException {
        CommaExpression commaExpression = AssignmentExpression.parse(code, table);
        while (true) {
            Code clone = code.clone();
            try {
                Blank blankBeforeCommaSign = Blank.parse(code, table);
                CommaSign commaSign = CommaSign.parse(code, table);
                Blank blankAfterCommaSign = Blank.parse(code, table);
                AssignmentExpression assignmentExpression = AssignmentExpression.parse(code, table);
                commaExpression = new CommaOperation(
                        commaExpression,
                        blankBeforeCommaSign,
                        commaSign,
                        blankAfterCommaSign,
                        assignmentExpression);
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        return commaExpression;
    }

    public static CommaExpression innermost(CommaExpression commaExpression) {
        while (commaExpression instanceof ParenthesizedCommaExpression) {
            commaExpression = ((ParenthesizedCommaExpression) commaExpression).commaExpression;
        }
        return commaExpression;
    }

    public static boolean controlling(CommaExpression commaExpression) {
        commaExpression = CommaExpression.innermost(commaExpression);
        return
                (commaExpression instanceof LogicalOrOperation) ||
                (commaExpression instanceof LogicalAndOperation) ||
                (commaExpression instanceof EqualityOperation) ||
                (commaExpression instanceof RelationalOperation) ||
                (commaExpression instanceof LogicalNegationOperation);
    }

    public static boolean effective(CommaExpression commaExpression) {
        commaExpression = CommaExpression.innermost(commaExpression);
        if (!(commaExpression instanceof FunctionCall)) {
            return
                    (commaExpression instanceof AssignmentOperation) ||
                    (commaExpression instanceof PostfixIncrementOperation) ||
                    (commaExpression instanceof PostfixDecrementOperation) ||
                    (commaExpression instanceof PrefixIncrementOperation) ||
                    (commaExpression instanceof PrefixDecrementOperation);
        }
        String string = commaExpression.toString().replaceAll("\\s+", " ");
        String[] strings = {
                "isalpha", "isalnum", "isblank", "iscntrl", "isdigit", "isgraph", "islower", "isprint", "ispunct", "isspace", "isupper", "isxdigit", "tolower", "toupper",
                "acos", "asin", "atan", "cos", "sin", "tan", "acosh", "asinh", "atanh", "cosh", "sinh", "tanh", "exp", "log", "log10", "log2", "fabs", "pow", "sqrt", "erf", "ceil", "floor", "round",
                "NULL",
                "abs", "labs", "llabs",
                "strcmp", "strncmp", "strchr", "strcspn", "strrchr", "strspn", "strstr", "strlen"};
        for (String element : strings) {
            if (string.startsWith(element + "(")) {
                return false;
            }
        }
        return true;
    }
}
