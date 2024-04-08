LinBee23 is an [SS23C99](../../../SSL/SSC/SS23C99) LFB used in fundamental programming courses of Beihang University in 2023 for beginners in C99 programming.

LinBee23 是 SS23C99 LFB，在 2023 年被用于北京航空航天大学的程序设计基础课程。

For almost all C99 codes not containing macros, type definitions, structures, unions or enumerations, the syntax trees constructed by LinBee23 are consistent with C99.

对于几乎全部的不包含宏（macro）、`typedef`（type definition）、结构体（structure）、联合体（union）、枚举（enumeration）的 C99 代码，LinBee23 构建的语法树符合 C99 规范。

For all C99 codes containing macros, type definitions, structures, unions or enumerations, LinBee23 does not construct syntax trees.

For all codes not of C99, LinBee23 rejects them without warnings.


| kind | example |
|:- |:-|
| macro           | `#define gamma 0.577216` |
| type definition | `typedef int Integer;` |
| structure       | `struct Pair { int left, right; };` |
| union | `union Vegetable { struct Potato potato; struct Tomato tomato; };` |
| enumeration | `enum Binary { zero, one };` |