package symbol.symbol.type;

import symbol.symbol.type.direct.SymbolDirectAbstractDeclarator;
import symbol.symbol.type.direct.array.SymbolArrayDirectAbstractDeclarator;
import symbol.symbol.type.direct.declarator.SymbolParenthesizedAbstractDeclarator;
import symbol.symbol.type.direct.function.SymbolFunctionDirectAbstractDeclarator;

import java.util.HashMap;
import java.util.HashSet;

public class Table implements Cloneable {
    public final String string;
    private final HashMap<String, SymbolTypeName> type;
    private final HashSet<String> declared;

    public Table() {
        this.string = null;
        this.type = new HashMap<>();
        this.declared = new HashSet<>();
        // ctype
        declare("isalpha", SymbolTypeName.parse("int (int)"));
        declare("isalnum", SymbolTypeName.parse("int (int)"));
        declare("isblank", SymbolTypeName.parse("int (int)"));
        declare("iscntrl", SymbolTypeName.parse("int (int)"));
        declare("isdigit", SymbolTypeName.parse("int (int)"));
        declare("isgraph", SymbolTypeName.parse("int (int)"));
        declare("islower", SymbolTypeName.parse("int (int)"));
        declare("isprint", SymbolTypeName.parse("int (int)"));
        declare("ispunct", SymbolTypeName.parse("int (int)"));
        declare("isspace", SymbolTypeName.parse("int (int)"));
        declare("isupper", SymbolTypeName.parse("int (int)"));
        declare("isxdigit", SymbolTypeName.parse("int (int)"));
        declare("tolower", SymbolTypeName.parse("int (int)"));
        declare("toupper", SymbolTypeName.parse("int (int)"));
        // math
        declare("acos", SymbolTypeName.parse("double (double)"));
        declare("asin", SymbolTypeName.parse("double (double)"));
        declare("atan", SymbolTypeName.parse("double (double)"));
        declare("cos", SymbolTypeName.parse("double (double)"));
        declare("sin", SymbolTypeName.parse("double (double)"));
        declare("tan", SymbolTypeName.parse("double (double)"));
        declare("acosh", SymbolTypeName.parse("double (double)"));
        declare("asinh", SymbolTypeName.parse("double (double)"));
        declare("atanh", SymbolTypeName.parse("double (double)"));
        declare("cosh", SymbolTypeName.parse("double (double)"));
        declare("sinh", SymbolTypeName.parse("double (double)"));
        declare("tanh", SymbolTypeName.parse("double (double)"));
        declare("exp", SymbolTypeName.parse("double (double)"));
        declare("log", SymbolTypeName.parse("double (double)"));
        declare("log10", SymbolTypeName.parse("double (double)"));
        declare("log2", SymbolTypeName.parse("double (double)"));
        declare("fabs", SymbolTypeName.parse("double (double)"));
        declare("pow", SymbolTypeName.parse("double (double, double)"));
        declare("sqrt", SymbolTypeName.parse("double (double)"));
        declare("erf", SymbolTypeName.parse("double (double)"));
        declare("ceil", SymbolTypeName.parse("double (double)"));
        declare("floor", SymbolTypeName.parse("double (double)"));
        declare("round", SymbolTypeName.parse("double (double)"));
        // stddef
        declare("NULL", SymbolTypeName.parse("void *"));
        // stdio
        declare("EOF", SymbolTypeName.parse("int"));
        declare("stdin", SymbolTypeName.parse("FILE *"));
        declare("stdout", SymbolTypeName.parse("FILE *"));
        declare("fclose", SymbolTypeName.parse("int (FILE *)"));
        declare("fflush", SymbolTypeName.parse("int (FILE *)"));
        declare("fopen", SymbolTypeName.parse("FILE * (const char * restrict, const char * restrict)"));
        declare("frepen", SymbolTypeName.parse("FILE * (const char * restrict, const char * restrict, FILE * restrict)"));
        declare("fprintf", SymbolTypeName.parse("int (FILE * restrict, const char * restrict, ...)"));
        declare("fscanf", SymbolTypeName.parse("int (FILE * restrict, const char * restrict, ...)"));
        declare("printf", SymbolTypeName.parse("int (const char * restrict, ...)"));
        declare("scanf", SymbolTypeName.parse("int (const char * restrict, ...)"));
        declare("sprintf", SymbolTypeName.parse("int (char * restrict, const char * restrict, ...)"));
        declare("sscanf", SymbolTypeName.parse("int (const char * restrict, const char * restrict, ...)"));
        declare("fegtc", SymbolTypeName.parse("int (FILE *)"));
        declare("fgets", SymbolTypeName.parse("char * (char * restrict, int, FILE * restrict)"));
        declare("fputc", SymbolTypeName.parse("int (int, FILE *);"));
        declare("fputs", SymbolTypeName.parse("int (const char * restrict, FILE * restrict);"));
        declare("getc", SymbolTypeName.parse("int (FILE *)"));
        declare("getchar", SymbolTypeName.parse("int ()"));
        declare("gets", SymbolTypeName.parse("char * (char *)"));
        declare("putc", SymbolTypeName.parse("int (int, FILE *)"));
        declare("putchar", SymbolTypeName.parse("int (int)"));
        declare("puts", SymbolTypeName.parse("int (const char *)"));
        declare("fread", SymbolTypeName.parse("size_t (void * restrict, size_t, size_t, FILE * restrict)"));
        declare("fwrite", SymbolTypeName.parse("size_t (const void * restrict, size_t, size_t, FILE * restrict)"));
        // stdlib
        declare("rand", SymbolTypeName.parse("int ()"));
        declare("srand", SymbolTypeName.parse("void (unsigned int)"));
        declare("calloc", SymbolTypeName.parse("void * (size_t, size_t)"));
        declare("free", SymbolTypeName.parse("void (void *)"));
        declare("malloc", SymbolTypeName.parse("void * (size_t)"));
        declare("realloc", SymbolTypeName.parse("void * (void *, size_t)"));
        declare("bsearch", SymbolTypeName.parse("void * (const void *, const void *, size_t, size_t, int (*)(const void *, const void *))"));
        declare("qsort", SymbolTypeName.parse("void (void *, size_t, size_t, int (*)(const void *, const void *))"));
        declare("abs", SymbolTypeName.parse("int (int)"));
        declare("labs", SymbolTypeName.parse("long int (long int)"));
        declare("llabs", SymbolTypeName.parse("long long int (long long int)"));
        // string
        declare("memcpy", SymbolTypeName.parse("void * (void * restrict, const void * restrict, size_t)"));
        declare("memmove", SymbolTypeName.parse("void * (void *, const void *, size_t)"));
        declare("strncpy", SymbolTypeName.parse("char * (char * restrict, const char * restrict, size_t)"));
        declare("strcat", SymbolTypeName.parse("char * (char * restrict, const char * restrict)"));
        declare("strncat", SymbolTypeName.parse("char * (char * restrict, const char * restrict, size_t)"));
        declare("strcmp", SymbolTypeName.parse("int (const char *, const char *)"));
        declare("strncmp", SymbolTypeName.parse("int (const char *, const char *, size_t)"));
        declare("strchr", SymbolTypeName.parse("char * (const char *, int c)"));
        declare("strcspn", SymbolTypeName.parse("size_t (const char *, const char *)"));
        declare("strrchr", SymbolTypeName.parse("char * (const char *, int)"));
        declare("strspn", SymbolTypeName.parse("size_t (const char *, const char *)"));
        declare("strstr", SymbolTypeName.parse("char * (const char *, const char *)"));
        declare("memset", SymbolTypeName.parse("void * (void *, int, size_t)"));
        declare("strlen", SymbolTypeName.parse("size_t (const char *)"));
    }

    public Table(String string, HashMap<String, SymbolTypeName> type) {
        this.string = string;
        this.type = type;
        this.declared = new HashSet<>();
    }

    public Table(String string, Table table) {
        this.string = string;
        this.type = (HashMap<String, SymbolTypeName>) table.type.clone();
        this.declared = new HashSet<>();
    }

    public SymbolTypeName type(String string) {
        if (!type.containsKey(string)) {
            return SymbolTypeName.unknown();
        }
        return type.get(string);
    }

    public void declare(String string, SymbolTypeName type) {
        declared.add(string);
        this.type.put(string, type);
    }


}
