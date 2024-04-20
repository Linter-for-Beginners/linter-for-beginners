package symbol.expression.postfix;

import symbol.expression.primary.string.StringLiteralPrimaryExpression;
import symbol.foundation.code.Code;
import symbol.foundation.node.Node;
import symbol.foundation.type.Table;
import symbol.expression.postfix.compound.CompoundLiteral;
import symbol.expression.comma.CommaExpression;
import symbol.expression.postfix.array.ArraySubscripting;
import symbol.expression.postfix.decrement.PostfixDecrementOperation;
import symbol.expression.postfix.decrement.PostfixDecrementSign;
import symbol.expression.postfix.function.ArgumentExpressionList;
import symbol.expression.postfix.function.FunctionCall;
import symbol.expression.postfix.increment.PostfixIncrementOperation;
import symbol.expression.postfix.increment.PostfixIncrementSign;
import symbol.expression.primary.PrimaryExpression;
import symbol.expression.unary.UnaryExpression;
import symbol.base.blank.Blank;
import symbol.base.punctuator.bracket.LeftBracket;
import symbol.base.punctuator.bracket.RightBracket;
import symbol.base.punctuator.parenthesis.LeftParenthesis;
import symbol.base.punctuator.parenthesis.RightParenthesis;
import symbol.foundation.type.SymbolTypeName;
import symbol.foundation.invalidity.InvalidityException;
import symbol.foundation.warning.Danger;
import symbol.foundation.warning.Discouragement;

import java.util.ArrayList;

public abstract class PostfixExpression extends UnaryExpression {
    public PostfixExpression(SymbolTypeName type, Node[] nodes) {
        super(type, nodes);
    }

    public static PostfixExpression parse(Code code, Table table) throws InvalidityException {
        try {
            return CompoundLiteral.parse(code, table);
        } catch (InvalidityException invalidityException) {
        }
        PostfixExpression postfixExpression = PrimaryExpression.parse(code, table);
        while (true) {
            Code clone = code.clone();
            try {
                Blank blankAfterPostfixExpression = Blank.parse(code, table);
                Code temporary = code.clone();
                try {
                    LeftBracket leftBracket = LeftBracket.parse(code, table);
                    Blank blankBeforeCommaExpression = Blank.parse(code, table);
                    CommaExpression commaExpression = CommaExpression.parse(code, table);
                    Blank blankAfterCommaExpression = Blank.parse(code, table);
                    RightBracket rightBracket = RightBracket.parse(code, table);
                    postfixExpression = new ArraySubscripting(
                            postfixExpression,
                            blankAfterPostfixExpression,
                            leftBracket,
                            blankBeforeCommaExpression,
                            commaExpression,
                            blankAfterCommaExpression,
                            rightBracket);
                    continue;
                } catch (InvalidityException invalidityException) {
                    code.set(temporary);
                }
                try {
                    LeftParenthesis leftParenthesis = LeftParenthesis.parse(code, table);
                    Blank blankBeforeArgumentExpressionList = Blank.parse(code, table);
                    ArgumentExpressionList argumentExpressionList = ArgumentExpressionList.parse(code, table);
                    Blank blankAfterArgumentExpressionList = Blank.parse(code, table);
                    RightParenthesis rightParenthesis = RightParenthesis.parse(code, table);
                    CommaExpression innermostPostfixExpression = CommaExpression.innermost(postfixExpression);
                    SymbolTypeName[] parameterType = SymbolTypeName.parameterType(postfixExpression.type);
                    SymbolTypeName returnType = SymbolTypeName.returnType(postfixExpression.type);
                    postfixExpression = new FunctionCall(returnType,
                            postfixExpression,
                            blankAfterPostfixExpression,
                            leftParenthesis,
                            blankBeforeArgumentExpressionList,
                            argumentExpressionList,
                            blankAfterArgumentExpressionList,
                            rightParenthesis);
                    SymbolTypeName[] argumentType = new SymbolTypeName[argumentExpressionList.assignmentExpression.length];
                    for (int i = 0; i < argumentType.length; i++) {
                        argumentType[i] = argumentExpressionList.assignmentExpression[i].type;
                    }
                    Integer number = null;
                    switch (innermostPostfixExpression.toString()) {
                        case "fprintf" :
                            number = 1;
                            break;
                        case "printf" :
                            number = 0;
                            break;
                        case "sprintf":
                            number = 1;
                            break;
                        case "fscanf" :
                            number = 1;
                            break;
                        case "scanf" :
                            number = 0;
                            break;
                        case "sscanf" :
                            number = 1;
                            break;
                        default :
                            break;
                    }
                    if (number != null) {
                        ArrayList<SymbolTypeName> types = new ArrayList<>();
                        for (SymbolTypeName type : parameterType) {
                            if (!type.isUnknown()) {
                                types.add(type);
                            }
                        }
                        if (argumentExpressionList.assignmentExpression[number] instanceof StringLiteralPrimaryExpression stringLiteralPrimaryExpression) {
                            String format = stringLiteralPrimaryExpression.toString();
                            format = format.substring(format.indexOf("\"") + "\"".length(), format.lastIndexOf("\""));
                            String[] strings = {"+", "-", " ", "#", ".", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "%%"};
                            for (String string : strings) {
                                format = format.replace(string, "");
                            }
                            format = format.replace("i", "d");
                            format = format.replaceAll("o|u|x|X", "u");
                            format = format.replaceAll("f|F|e|E|g|G|a|A", "f");
                            if (innermostPostfixExpression.toString().contains("printf")) {
                                while (format.contains("%")) {
                                    format = format.substring(format.indexOf("%") + "%".length());
                                    if (format.startsWith("z")) {
                                        types.add(new SymbolTypeName("size_t"));
                                        continue;
                                    }
                                    if (format.startsWith("t")) {
                                        types.add(new SymbolTypeName("ptrdiff_t"));
                                        continue;
                                    }
                                    if (format.startsWith("hhd")) {
                                        types.add(new SymbolTypeName("signed char"));
                                        continue;
                                    }
                                    if (format.startsWith("hhu")) {
                                        types.add(new SymbolTypeName("unsigned char"));
                                        continue;
                                    }
                                    if (format.startsWith("hd")) {
                                        types.add(new SymbolTypeName("short int"));
                                        continue;
                                    }
                                    if (format.startsWith("hu")) {
                                        types.add(new SymbolTypeName("unsigned short int"));
                                        continue;
                                    }
                                    if (format.startsWith("d")) {
                                        types.add(new SymbolTypeName("int"));
                                        continue;
                                    }
                                    if (format.startsWith("u")) {
                                        types.add(new SymbolTypeName("unsigned int"));
                                        continue;
                                    }
                                    if (format.startsWith("ld")) {
                                        types.add(new SymbolTypeName("long int"));
                                        continue;
                                    }
                                    if (format.startsWith("lu")) {
                                        types.add(new SymbolTypeName("unsigned long int"));
                                        continue;
                                    }
                                    if (format.startsWith("lld")) {
                                        types.add(new SymbolTypeName("long long int"));
                                        continue;
                                    }
                                    if (format.startsWith("llu")) {
                                        types.add(new SymbolTypeName("unsigned long long int"));
                                        continue;
                                    }
                                    if (format.startsWith("f")) {
                                        types.add(new SymbolTypeName("double"));
                                        continue;
                                    }
                                    if (format.startsWith("lf")) {
                                        types.add(new SymbolTypeName("double"));
                                        continue;
                                    }
                                    if (format.startsWith("Lf")) {
                                        types.add(new SymbolTypeName("long double"));
                                        continue;
                                    }
                                    if (format.startsWith("s")) {
                                        types.add(new SymbolTypeName("char *"));
                                        continue;
                                    }
                                    if (format.startsWith("c")) {
                                        types.add(new SymbolTypeName("int"));
                                        continue;
                                    }
                                    types.add(new SymbolTypeName());
                                }
                            }
                            if (innermostPostfixExpression.toString().contains("scanf")) {
                                while (format.contains("%")) {
                                    format = format.substring(format.indexOf("%") + "%".length());
                                    if (format.startsWith("z")) {
                                        types.add(new SymbolTypeName("size_t *"));
                                        continue;
                                    }
                                    if (format.startsWith("t")) {
                                        types.add(new SymbolTypeName("ptrdiff_t *"));
                                        continue;
                                    }
                                    if (format.startsWith("hhd")) {
                                        types.add(new SymbolTypeName("signed char *"));
                                        continue;
                                    }
                                    if (format.startsWith("hhu")) {
                                        types.add(new SymbolTypeName("unsigned char *"));
                                        continue;
                                    }
                                    if (format.startsWith("hd")) {
                                        types.add(new SymbolTypeName("short int *"));
                                        continue;
                                    }
                                    if (format.startsWith("hu")) {
                                        types.add(new SymbolTypeName("unsigned short int *"));
                                        continue;
                                    }
                                    if (format.startsWith("d")) {
                                        types.add(new SymbolTypeName("int *"));
                                        continue;
                                    }
                                    if (format.startsWith("u")) {
                                        types.add(new SymbolTypeName("unsigned int *"));
                                        continue;
                                    }
                                    if (format.startsWith("ld")) {
                                        types.add(new SymbolTypeName("long int *"));
                                        continue;
                                    }
                                    if (format.startsWith("lu")) {
                                        types.add(new SymbolTypeName("unsigned long int *"));
                                        continue;
                                    }
                                    if (format.startsWith("lld")) {
                                        types.add(new SymbolTypeName("long long int *"));
                                        continue;
                                    }
                                    if (format.startsWith("llu")) {
                                        types.add(new SymbolTypeName("unsigned long long int *"));
                                        continue;
                                    }
                                    if (format.startsWith("f")) {
                                        types.add(new SymbolTypeName("float *"));
                                        continue;
                                    }
                                    if (format.startsWith("lf")) {
                                        types.add(new SymbolTypeName("double *"));
                                        continue;
                                    }
                                    if (format.startsWith("Lf")) {
                                        types.add(new SymbolTypeName("long double *"));
                                        continue;
                                    }
                                    if (format.startsWith("s")) {
                                        types.add(new SymbolTypeName("char *"));
                                        continue;
                                    }
                                    if (format.startsWith("c")) {
                                        types.add(new SymbolTypeName("char *"));
                                        continue;
                                    }
                                    types.add(new SymbolTypeName());
                                }
                            }
                        } else {
                            while (types.size() < argumentType.length) {
                                types.add(new SymbolTypeName());
                            }
                        }
                        parameterType = types.toArray(new SymbolTypeName[0]);
                    }
                    if (parameterType.length != argumentType.length) {
                        if (argumentType.length > parameterType.length) {
                            postfixExpression.warnings.add(new Discouragement(postfixExpression, postfixExpression, "Excess of function arguments is discouraged for beginners."));
                        }
                        if (argumentType.length < parameterType.length) {
                            postfixExpression.warnings.add(new Danger(postfixExpression, postfixExpression, "Lack of function arguments is dangerous for beginners."));
                        }
                    } else {
                        for (int i = 0; i < parameterType.length; i++) {
                            if (!parameterType[i].equals(argumentType[i])) {
                                if (number != null) {
                                    postfixExpression.warnings.add(new Danger(postfixExpression, argumentExpressionList.assignmentExpression[i], "Function argument whose type is incompatible is dangerous for beginners."));
                                } else {
                                    postfixExpression.warnings.add(new Discouragement(postfixExpression, argumentExpressionList.assignmentExpression[i], "Function argument whose type is incompatible is discouraged for beginners."));
                                }
                            }
                        }
                    }
                    continue;
                } catch (InvalidityException invalidityException) {
                    code.set(temporary);
                }
                try {
                    PostfixIncrementSign postfixIncrementSign = PostfixIncrementSign.parse(code, table);
                    postfixExpression = new PostfixIncrementOperation(
                            postfixExpression,
                            blankAfterPostfixExpression,
                            postfixIncrementSign);
                    continue;
                } catch (InvalidityException invalidityException) {
                    code.set(temporary);
                }
                try {
                    PostfixDecrementSign postfixDecrementSign = PostfixDecrementSign.parse(code, table);
                    postfixExpression = new PostfixDecrementOperation(
                            postfixExpression,
                            blankAfterPostfixExpression,
                            postfixDecrementSign);
                    continue;
                } catch (InvalidityException invalidityException) {
                    code.set(temporary);
                }
                throw new InvalidityException();
            } catch (InvalidityException invalidityException) {
                code.set(clone);
                break;
            }
        }
        return postfixExpression;
    }
}
