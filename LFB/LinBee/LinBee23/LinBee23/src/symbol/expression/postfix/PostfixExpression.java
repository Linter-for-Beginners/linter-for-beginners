package symbol.expression.postfix;

import symbol.base.string.StringLiteral;
import symbol.expression.primary.string.StringLiteralPrimaryExpression;
import symbol.symbol.type.Table;
import symbol.expression.postfix.compound.CompoundLiteral;
import symbol.symbol.*;
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
import symbol.symbol.type.SymbolTypeName;
import symbol.symbol.invalidity.InvalidityException;
import symbol.symbol.sentence.Sentence;
import symbol.symbol.type.unknown.SymbolUnknownTypeName;
import symbol.symbol.warning.Danger;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class PostfixExpression extends UnaryExpression {
    public PostfixExpression(SymbolTypeName type, Symbol[] symbols) {
        super(type, symbols);
    }

    public static PostfixExpression parse(Sentence sentence, Table table) throws InvalidityException {
        try {
            return CompoundLiteral.parse(sentence, table);
        } catch (InvalidityException invalidityException) {
        }
        PostfixExpression postfixExpression = PrimaryExpression.parse(sentence, table);
        while (true) {
            Sentence clone = sentence.clone();
            try {
                Blank blankAfterPostfixExpression = Blank.parse(sentence, table);
                Sentence temporary = sentence.clone();
                try {
                    LeftBracket leftBracket = LeftBracket.parse(sentence, table);
                    Blank blankBeforeCommaExpression = Blank.parse(sentence, table);
                    CommaExpression commaExpression = CommaExpression.parse(sentence, table);
                    Blank blankAfterCommaExpression = Blank.parse(sentence, table);
                    RightBracket rightBracket = RightBracket.parse(sentence, table);
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
                    sentence.set(temporary);
                }
                try {
                    LeftParenthesis leftParenthesis = LeftParenthesis.parse(sentence, table);
                    Blank blankBeforeArgumentExpressionList = Blank.parse(sentence, table);
                    ArgumentExpressionList argumentExpressionList = ArgumentExpressionList.parse(sentence, table);
                    Blank blankAfterArgumentExpressionList = Blank.parse(sentence, table);
                    RightParenthesis rightParenthesis = RightParenthesis.parse(sentence, table);
                    CommaExpression innermostPostfixExpression = CommaExpression.innermost(postfixExpression);
                    SymbolTypeName returnType = table.returnType(innermostPostfixExpression.toString());
                    postfixExpression = new FunctionCall(returnType,
                            postfixExpression,
                            blankAfterPostfixExpression,
                            leftParenthesis,
                            blankBeforeArgumentExpressionList,
                            argumentExpressionList,
                            blankAfterArgumentExpressionList,
                            rightParenthesis);
                    SymbolTypeName[] parameterType = table.parameterType(innermostPostfixExpression.toString());
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
                            if (!(type instanceof SymbolUnknownTypeName)) {
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
                                        types.add(SymbolTypeName.parse("size_t"));
                                        continue;
                                    }
                                    if (format.startsWith("t")) {
                                        types.add(SymbolTypeName.parse("ptrdiff_t"));
                                        continue;
                                    }
                                    if (format.startsWith("hhd")) {
                                        types.add(SymbolTypeName.parse("signed char"));
                                        continue;
                                    }
                                    if (format.startsWith("hhu")) {
                                        types.add(SymbolTypeName.parse("unsigned char"));
                                        continue;
                                    }
                                    if (format.startsWith("hd")) {
                                        types.add(SymbolTypeName.parse("short int"));
                                        continue;
                                    }
                                    if (format.startsWith("hu")) {
                                        types.add(SymbolTypeName.parse("unsigned short int"));
                                        continue;
                                    }
                                    if (format.startsWith("d")) {
                                        types.add(SymbolTypeName.parse("int"));
                                        continue;
                                    }
                                    if (format.startsWith("u")) {
                                        types.add(SymbolTypeName.parse("unsigned int"));
                                        continue;
                                    }
                                    if (format.startsWith("ld")) {
                                        types.add(SymbolTypeName.parse("long int"));
                                        continue;
                                    }
                                    if (format.startsWith("lu")) {
                                        types.add(SymbolTypeName.parse("unsigned long int"));
                                        continue;
                                    }
                                    if (format.startsWith("lld")) {
                                        types.add(SymbolTypeName.parse("long long int"));
                                        continue;
                                    }
                                    if (format.startsWith("llu")) {
                                        types.add(SymbolTypeName.parse("unsigned long long int"));
                                        continue;
                                    }
                                    if (format.startsWith("f")) {
                                        types.add(SymbolTypeName.parse("double"));
                                        continue;
                                    }
                                    if (format.startsWith("lf")) {
                                        types.add(SymbolTypeName.parse("double"));
                                        continue;
                                    }
                                    if (format.startsWith("Lf")) {
                                        types.add(SymbolTypeName.parse("long double"));
                                        continue;
                                    }
                                    if (format.startsWith("s")) {
                                        types.add(SymbolTypeName.parse("char *"));
                                        continue;
                                    }
                                    if (format.startsWith("c")) {
                                        types.add(SymbolTypeName.parse("int"));
                                        continue;
                                    }
                                    types.add(SymbolTypeName.unknown());
                                }
                            }
                            if (innermostPostfixExpression.toString().contains("scanf")) {
                                while (format.contains("%")) {
                                    format = format.substring(format.indexOf("%") + "%".length());
                                    if (format.startsWith("z")) {
                                        types.add(SymbolTypeName.parse("size_t *"));
                                        continue;
                                    }
                                    if (format.startsWith("t")) {
                                        types.add(SymbolTypeName.parse("ptrdiff_t *"));
                                        continue;
                                    }
                                    if (format.startsWith("hhd")) {
                                        types.add(SymbolTypeName.parse("signed char *"));
                                        continue;
                                    }
                                    if (format.startsWith("hhu")) {
                                        types.add(SymbolTypeName.parse("unsigned char *"));
                                        continue;
                                    }
                                    if (format.startsWith("hd")) {
                                        types.add(SymbolTypeName.parse("short int *"));
                                        continue;
                                    }
                                    if (format.startsWith("hu")) {
                                        types.add(SymbolTypeName.parse("unsigned short int *"));
                                        continue;
                                    }
                                    if (format.startsWith("d")) {
                                        types.add(SymbolTypeName.parse("int *"));
                                        continue;
                                    }
                                    if (format.startsWith("u")) {
                                        types.add(SymbolTypeName.parse("unsigned int *"));
                                        continue;
                                    }
                                    if (format.startsWith("ld")) {
                                        types.add(SymbolTypeName.parse("long int *"));
                                        continue;
                                    }
                                    if (format.startsWith("lu")) {
                                        types.add(SymbolTypeName.parse("unsigned long int *"));
                                        continue;
                                    }
                                    if (format.startsWith("lld")) {
                                        types.add(SymbolTypeName.parse("long long int *"));
                                        continue;
                                    }
                                    if (format.startsWith("llu")) {
                                        types.add(SymbolTypeName.parse("unsigned long long int *"));
                                        continue;
                                    }
                                    if (format.startsWith("f")) {
                                        types.add(SymbolTypeName.parse("float *"));
                                        continue;
                                    }
                                    if (format.startsWith("lf")) {
                                        types.add(SymbolTypeName.parse("double *"));
                                        continue;
                                    }
                                    if (format.startsWith("Lf")) {
                                        types.add(SymbolTypeName.parse("long double *"));
                                        continue;
                                    }
                                    if (format.startsWith("s")) {
                                        types.add(SymbolTypeName.parse("char *"));
                                        continue;
                                    }
                                    if (format.startsWith("c")) {
                                        types.add(SymbolTypeName.parse("char *"));
                                        continue;
                                    }
                                    types.add(SymbolTypeName.unknown());
                                }
                            }
                        } else {
                            while (types.size() < argumentType.length) {
                                types.add(SymbolTypeName.unknown());
                            }
                        }
                        parameterType = types.toArray(new SymbolTypeName[0]);
                    }
                    if (parameterType.length != argumentType.length) {
                        postfixExpression.warnings.add(new Danger(postfixExpression, postfixExpression));
                    } else {
                        for (int i = 0; i < parameterType.length; i++) {
                            if (!parameterType[i].equals(argumentType[i])) {
                                postfixExpression.warnings.add(new Danger(postfixExpression, argumentExpressionList.assignmentExpression[i]));
                            }
                        }
                    }
                    continue;
                } catch (InvalidityException invalidityException) {
                    sentence.set(temporary);
                }
                try {
                    PostfixIncrementSign postfixIncrementSign = PostfixIncrementSign.parse(sentence, table);
                    postfixExpression = new PostfixIncrementOperation(
                            postfixExpression,
                            blankAfterPostfixExpression,
                            postfixIncrementSign);
                    continue;
                } catch (InvalidityException invalidityException) {
                    sentence.set(temporary);
                }
                try {
                    PostfixDecrementSign postfixDecrementSign = PostfixDecrementSign.parse(sentence, table);
                    postfixExpression = new PostfixDecrementOperation(
                            postfixExpression,
                            blankAfterPostfixExpression,
                            postfixDecrementSign);
                    continue;
                } catch (InvalidityException invalidityException) {
                    sentence.set(temporary);
                }
                throw new InvalidityException();
            } catch (InvalidityException invalidityException) {
                sentence.set(clone);
                break;
            }
        }
        return postfixExpression;
    }
}
