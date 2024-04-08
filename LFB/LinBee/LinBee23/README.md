LinBee23 is an [SS23C99](../../../SSL/SSC/SS23C99) LFB used in fundamental programming courses of Beihang University in 2023 for beginners in C99 programming.

For almost all C99 codes not containing macros, type definitions, structures, unions or enumerations, their syntax trees constructed by LinBee23 are consistent with C99, and SS23C99 WFBs offered by LinBee23 are based on the syntax trees.


LinBee23 constructs syntax trees which are consistent with C99 and offers SS23C99 WFBs.

For all C99 codes containing macros, type definitions, structures, unions or enumerations, LinBee23 does not construct syntax trees.

For all codes not of C99, LinBee23 rejects them without warnings.


| kind | example |
|:- |:-|
| macro           | `#define gamma 0.577216` |
| type definition | `typedef int Integer;` |
| structure       | `struct Pair { int left, right; };` |
| union | `union Vegetable { struct Potato potato; struct Tomato tomato; };` |
| enumeration | `enum Binary { zero, one };` |