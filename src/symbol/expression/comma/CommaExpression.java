package symbol.expression.comma;

import symbol.expression.assignment.AssignmentOperation;
import symbol.expression.equality.EqualityOperation;
import symbol.expression.logical.and.LogicalAndOperation;
import symbol.expression.logical.or.LogicalOrOperation;
import symbol.expression.postfix.decrement.PostfixDecrementOperation;
import symbol.expression.postfix.function.FunctionCall;
import symbol.expression.postfix.increment.PostfixIncrementOperation;
import symbol.expression.primary.expression.ParenthesizedCommaExpression;
import symbol.expression.relation.RelationalOperation;
import symbol.expression.unary.decrement.PrefixDecrementOperation;
import symbol.expression.unary.increment.PrefixIncrementOperation;
import symbol.expression.unary.negation.LogicalNegationOperation;
import symbol.symbol.type.Table;
import symbol.symbol.*;
import symbol.base.blank.Blank;
import symbol.expression.assignment.AssignmentExpression;
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;

public abstract class CommaExpression extends Nonterminal {
    public CommaExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static CommaExpression parse(Sentence sentence, Table table) throws InvalidityException {
        CommaExpression commaExpression = AssignmentExpression.parse(sentence, table);
        while (true) {
            Sentence clone = sentence.clone();
            try {
                Blank blankBeforeCommaSign = Blank.parse(sentence, table);
                CommaSign commaSign = CommaSign.parse(sentence, table);
                Blank blankAfterCommaSign = Blank.parse(sentence, table);
                AssignmentExpression assignmentExpression = AssignmentExpression.parse(sentence, table);
                commaExpression = new CommaOperation(
                        commaExpression,
                        blankBeforeCommaSign,
                        commaSign,
                        blankAfterCommaSign,
                        assignmentExpression);
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
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
