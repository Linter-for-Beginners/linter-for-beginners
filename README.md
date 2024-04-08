# Introduction

Linters have been widely used in programming, especially in industrial programming, whereas little attention has been paid to linters for beginners.

Fundamental programming courses of universities and colleges has invariably concentrated on important and useful programming languages, such as the programming language C. However, because these programming languages are not designed for beginners, some grammars of them are too intractable to be completely mastered and can simply lead to a lot of troubles.

Therefore, in order to help beginners in programming, especially students in fundamental programming courses of universities and colleges, we design LFBs to provide WFBs. For example, [LinBee23](LFB/LinBee/LinBee23) is an [SS23C99](SSL/SSC/SS23C99) LFB used in fundamental programming courses of Beihang University in 2023.

Notice that some pivotal concepts mentioned above are defined as follows.

## LFB

To design a linter for beginners, assume that $\mathcal{L}$ is a programming language and $\mathcal{L}_ {x}$ is a version of $\mathcal{L}$, then $\mathrm{SS} \mathcal{L}_ {x}$ is a programming language for beginners based on $\mathcal{L}_ {x}$ and $\mathrm{SS}_ {y} \mathcal{L}_ {x}$ is a version of $\mathrm{SS} \mathcal{L}_ {x}$. As a simplification of $\mathcal{L}_ {x}$ for beginners, $\mathrm{SS} \mathcal{L}_ {x}$ should be a subset of $\mathcal{L}_ {x}$, thus each code of $\mathrm{SS} \mathcal{L}_ {x}$ is a code of $\mathcal{L}_ {x}$ and $\mathcal{L}_ {x}$ is called the foundation of $\mathrm{SS} \mathcal{L}_ {x}$.

A linter is an $\mathrm{SS}_ {y} \mathcal{L}_ {x}$ LFB if and only if it satisfies:

- Each code of $\mathrm{SS}_ {y} \mathcal{L}_ {x}$ is accepted without $\mathrm{SS}_ {y} \mathcal{L}_ {x}$ WFBs.

- Each code not of $\mathrm{SS}_ {y} \mathcal{L}_ {x}$ but of $\mathcal{L}_ {x}$ is accepted with $\mathrm{SS}_ {y} \mathcal{L}_ {x}$ WFBs.

- Each code not of $\mathcal{L}_ {x}$ is rejected with or without $\mathrm{SS}_ {y} \mathcal{L}_ {x}$ WFBs.

For example, SS23C99 is a subset of C99.

## WFB

A warning is an $\mathrm{SS}_ {y} \mathcal{L}_ {x}$ WFB if and only if it is contained by one of the following:

- Warnings for beginners about danger defined in $\mathrm{SS}_ {y} \mathcal{L}_ {x}$ indicate behaviors which are dangerous for beginners, such as implementation-defined behaviors, locale-specific behaviors, undefined behaviors and unspecified behaviors. For beginners, these behaviors need to be avoided, because they are too intractable to be completely mastered and can simply lead to a lot of troubles.

- Warnings for beginners about discouragement defined in $\mathrm{SS}_ {y} \mathcal{L}_ {x}$ indicate behaviors which are discouraged for beginners, such as operations about tricky operator precedence without parentheses. For beginners, these behaviors should be avoided, because they are often caused by some common misunderstandings or often lead to some common misunderstandings.

- Warnings for beginners about strangeness defined in $\mathrm{SS}_ {y} \mathcal{L}_ {x}$ indicate behaviors which are strange for beginners, such as comma operations and empty statements. For beginners, these behaviors are suggested to be avoided, because they can be simply replaced by other behaviors which are more formal.
