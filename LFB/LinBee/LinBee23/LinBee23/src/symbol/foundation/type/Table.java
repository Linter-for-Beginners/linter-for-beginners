package symbol.foundation.type;

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
        declare("isalpha", new SymbolTypeName("int (int)"));
        declare("isalnum", new SymbolTypeName("int (int)"));
        declare("isblank", new SymbolTypeName("int (int)"));
        declare("iscntrl", new SymbolTypeName("int (int)"));
        declare("isdigit", new SymbolTypeName("int (int)"));
        declare("isgraph", new SymbolTypeName("int (int)"));
        declare("islower", new SymbolTypeName("int (int)"));
        declare("isprint", new SymbolTypeName("int (int)"));
        declare("ispunct", new SymbolTypeName("int (int)"));
        declare("isspace", new SymbolTypeName("int (int)"));
        declare("isupper", new SymbolTypeName("int (int)"));
        declare("isxdigit", new SymbolTypeName("int (int)"));
        declare("tolower", new SymbolTypeName("int (int)"));
        declare("toupper", new SymbolTypeName("int (int)"));
        // math
        declare("acos", new SymbolTypeName("double (double)"));
        declare("asin", new SymbolTypeName("double (double)"));
        declare("atan", new SymbolTypeName("double (double)"));
        declare("cos", new SymbolTypeName("double (double)"));
        declare("sin", new SymbolTypeName("double (double)"));
        declare("tan", new SymbolTypeName("double (double)"));
        declare("acosh", new SymbolTypeName("double (double)"));
        declare("asinh", new SymbolTypeName("double (double)"));
        declare("atanh", new SymbolTypeName("double (double)"));
        declare("cosh", new SymbolTypeName("double (double)"));
        declare("sinh", new SymbolTypeName("double (double)"));
        declare("tanh", new SymbolTypeName("double (double)"));
        declare("exp", new SymbolTypeName("double (double)"));
        declare("log", new SymbolTypeName("double (double)"));
        declare("log10", new SymbolTypeName("double (double)"));
        declare("log2", new SymbolTypeName("double (double)"));
        declare("fabs", new SymbolTypeName("double (double)"));
        declare("pow", new SymbolTypeName("double (double, double)"));
        declare("sqrt", new SymbolTypeName("double (double)"));
        declare("erf", new SymbolTypeName("double (double)"));
        declare("ceil", new SymbolTypeName("double (double)"));
        declare("floor", new SymbolTypeName("double (double)"));
        declare("round", new SymbolTypeName("double (double)"));
        // stddef
        declare("NULL", new SymbolTypeName("void *"));
        // stdio
        declare("EOF", new SymbolTypeName("int"));
        declare("stdin", new SymbolTypeName("FILE *"));
        declare("stdout", new SymbolTypeName("FILE *"));
        declare("fclose", new SymbolTypeName("int (FILE *)"));
        declare("fflush", new SymbolTypeName("int (FILE *)"));
        declare("fopen", new SymbolTypeName("FILE * (const char * restrict, const char * restrict)"));
        declare("frepen", new SymbolTypeName("FILE * (const char * restrict, const char * restrict, FILE * restrict)"));
        declare("fprintf", new SymbolTypeName("int (FILE * restrict, const char * restrict, ...)"));
        declare("fscanf", new SymbolTypeName("int (FILE * restrict, const char * restrict, ...)"));
        declare("printf", new SymbolTypeName("int (const char * restrict, ...)"));
        declare("scanf", new SymbolTypeName("int (const char * restrict, ...)"));
        declare("sprintf", new SymbolTypeName("int (char * restrict, const char * restrict, ...)"));
        declare("sscanf", new SymbolTypeName("int (const char * restrict, const char * restrict, ...)"));
        declare("fegtc", new SymbolTypeName("int (FILE *)"));
        declare("fgets", new SymbolTypeName("char * (char * restrict, int, FILE * restrict)"));
        declare("fputc", new SymbolTypeName("int (int, FILE *);"));
        declare("fputs", new SymbolTypeName("int (const char * restrict, FILE * restrict);"));
        declare("getc", new SymbolTypeName("int (FILE *)"));
        declare("getchar", new SymbolTypeName("int ()"));
        declare("gets", new SymbolTypeName("char * (char *)"));
        declare("putc", new SymbolTypeName("int (int, FILE *)"));
        declare("putchar", new SymbolTypeName("int (int)"));
        declare("puts", new SymbolTypeName("int (const char *)"));
        declare("fread", new SymbolTypeName("size_t (void * restrict, size_t, size_t, FILE * restrict)"));
        declare("fwrite", new SymbolTypeName("size_t (const void * restrict, size_t, size_t, FILE * restrict)"));
        // stdlib
        declare("rand", new SymbolTypeName("int ()"));
        declare("srand", new SymbolTypeName("void (unsigned int)"));
        declare("calloc", new SymbolTypeName("void * (size_t, size_t)"));
        declare("free", new SymbolTypeName("void (void *)"));
        declare("malloc", new SymbolTypeName("void * (size_t)"));
        declare("realloc", new SymbolTypeName("void * (void *, size_t)"));
        declare("bsearch", new SymbolTypeName("void * (const void *, const void *, size_t, size_t, int (*)(const void *, const void *))"));
        declare("qsort", new SymbolTypeName("void (void *, size_t, size_t, int (*)(const void *, const void *))"));
        declare("abs", new SymbolTypeName("int (int)"));
        declare("labs", new SymbolTypeName("long int (long int)"));
        declare("llabs", new SymbolTypeName("long long int (long long int)"));
        // string
        declare("memcpy", new SymbolTypeName("void * (void * restrict, const void * restrict, size_t)"));
        declare("memmove", new SymbolTypeName("void * (void *, const void *, size_t)"));
        declare("strncpy", new SymbolTypeName("char * (char * restrict, const char * restrict, size_t)"));
        declare("strcat", new SymbolTypeName("char * (char * restrict, const char * restrict)"));
        declare("strncat", new SymbolTypeName("char * (char * restrict, const char * restrict, size_t)"));
        declare("strcmp", new SymbolTypeName("int (const char *, const char *)"));
        declare("strncmp", new SymbolTypeName("int (const char *, const char *, size_t)"));
        declare("strchr", new SymbolTypeName("char * (const char *, int c)"));
        declare("strcspn", new SymbolTypeName("size_t (const char *, const char *)"));
        declare("strrchr", new SymbolTypeName("char * (const char *, int)"));
        declare("strspn", new SymbolTypeName("size_t (const char *, const char *)"));
        declare("strstr", new SymbolTypeName("char * (const char *, const char *)"));
        declare("memset", new SymbolTypeName("void * (void *, int, size_t)"));
        declare("strlen", new SymbolTypeName("size_t (const char *)"));
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
            return new SymbolTypeName();
        }
        return type.get(string);
    }

    public void declare(String string, SymbolTypeName type) {
        declared.add(string);
        this.type.put(string, type);
    }


}
