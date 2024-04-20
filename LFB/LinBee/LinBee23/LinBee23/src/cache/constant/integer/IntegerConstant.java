package cache.constant.integer;

import basis.code.Code;
import basis.type.Table;
import cache.constant.Constant;
import basis.type.SymbolTypeName;
import basis.invalidity.InvalidityException;

import java.math.BigInteger;

public class IntegerConstant extends Constant {
    protected IntegerConstant(Integer row, Integer column, SymbolTypeName type, String string) {
        super(row, column, type, string);
    }

    public static IntegerConstant parse(Code code, Table table) throws InvalidityException {
        Integer row = code.getRow();
        Integer column = code.getColumn();
        String codeString = code.toString();
        if (codeString.length() == 0) {
            throw new InvalidityException();
        }
        if (codeString.charAt(0) < '0' || '9' < codeString.charAt(0)) {
            throw new InvalidityException();
        }
        BigInteger maximumInt                 = new BigInteger("2147483647");
        BigInteger maximumUnsignedInt         = new BigInteger("4294967296");
        BigInteger maximumLongInt             = new BigInteger("2147483647");
        BigInteger maximumUnsignedLongInt     = new BigInteger("4294967296");
        BigInteger maximumLongLongInt         = new BigInteger("9223372036854775807");
        BigInteger maximumUnsignedLongLongInt = new BigInteger("18446744073709551616");
        int radix = codeString.startsWith("0b") ? 2 : (codeString.startsWith("0x") ? 16 : codeString.startsWith("0") ? 8 : 10);
        BigInteger value = new BigInteger("0");
        int i;
        for (i = codeString.startsWith("0b") ? "0b".length() : (codeString.startsWith("0x") ? "0x".length() : 0); i < codeString.length(); i++) {
            char c = codeString.charAt(i);
            if        ('0' <= c && c <= '9') {
                value = value.multiply(BigInteger.valueOf(radix));
                value = value.add(BigInteger.valueOf(c - '0'));
            } else if ('a' <= c && c <= 'f') {
                value = value.multiply(BigInteger.valueOf(radix));
                value = value.add(BigInteger.valueOf(c - 'a' + 10));
            } else if ('A' <= c && c <= 'F') {
                value = value.multiply(BigInteger.valueOf(radix));
                value = value.add(BigInteger.valueOf(c - 'A' + 10));
            } else {
                break;
            }
        }
        String suffix = null;
        if (codeString.startsWith("ull", i) || codeString.startsWith("Ull", i) || codeString.startsWith("uLL", i) || codeString.startsWith("ULL", i)) {
            suffix = "ull";
        } else if (codeString.startsWith("ll", i) || codeString.startsWith("LL", i)) {
            suffix = "ll";
        } else if (codeString.startsWith("ul", i) || codeString.startsWith("Ul", i) || codeString.startsWith("uL", i) || codeString.startsWith("UL", i)) {
            suffix = "ul";
        } else if (codeString.startsWith("l", i) || codeString.startsWith("L", i)) {
            suffix = "l";
        } else if (codeString.startsWith("u", i) || codeString.startsWith("U", i)) {
            suffix = "u";
        } else {
            suffix = "";
        }
        i += suffix.length();
        String type = null;
        if (suffix.equals("") && radix == 10) {
            type = maximumInt.compareTo(value) >= 0 ? "int" :
                    maximumLongInt.compareTo(value) >= 0 ? "long int" :
                            maximumLongLongInt.compareTo(value) >= 0 ? "long long int" :
                                    "unknown";
        }
        if (suffix.equals("") && radix != 10) {
            type = maximumInt.compareTo(value) >= 0 ? "int" :
                    maximumUnsignedInt.compareTo(value) >= 0 ? "unsigned int" :
                            maximumLongInt.compareTo(value) >= 0 ? "long int" :
                                    maximumUnsignedLongInt.compareTo(value) >= 0 ? "unsigned long int" :
                                            maximumLongLongInt.compareTo(value) >= 0 ? "long long int" :
                                                    maximumUnsignedLongLongInt.compareTo(value) >= 0 ? "unsigned long long int" :
                                                            "unknown";
        }
        if (suffix.equals("u")) {
            type = maximumUnsignedInt.compareTo(value) >= 0 ? "unsigned int" :
                    maximumUnsignedLongInt.compareTo(value) >= 0 ? "unsigned long int" :
                            maximumUnsignedLongLongInt.compareTo(value) >= 0 ? "unsigned long long int" :
                                    "unknown";
        }
        if (suffix.equals("l") && radix == 10) {
            type = maximumLongInt.compareTo(value) >= 0 ? "long int" :
                            maximumLongLongInt.compareTo(value) >= 0 ? "long long int" :
                                    "unknown";
        }
        if (suffix.equals("l") && radix != 10) {
            type = maximumLongInt.compareTo(value) >= 0 ? "long int" :
                                    maximumUnsignedLongInt.compareTo(value) >= 0 ? "unsigned long int" :
                                            maximumLongLongInt.compareTo(value) >= 0 ? "long long int" :
                                                    maximumUnsignedLongLongInt.compareTo(value) >= 0 ? "unsigned long long int" :
                                                            "unknown";
        }
        if (suffix.equals("ul")) {
            type = maximumUnsignedLongInt.compareTo(value) >= 0 ? "unsigned long int" :
                            maximumUnsignedLongLongInt.compareTo(value) >= 0 ? "unsigned long long int" :
                                    "unknown";
        }
        if (suffix.equals("ll" ) && radix == 10) {
            type = maximumLongLongInt.compareTo(value) >= 0 ? "long long int" : "unknown";
        }
        if (suffix.equals("ll" ) && radix != 10) {
            type = maximumLongLongInt.compareTo(value) >= 0 ? "long long int" :
                                    maximumUnsignedLongLongInt.compareTo(value) >= 0 ? "unsigned long long int" :
                                            "unknown";
        }
        if (suffix.equals("ull")) {
            type = maximumUnsignedLongLongInt.compareTo(value) >= 0 ? "unsigned long long int" : "unknown";
        }
        String string = codeString.substring(0, i);
        code.remove(string);
        return new IntegerConstant(row, column, new SymbolTypeName(type), string);
    }
}
