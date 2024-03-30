# Introduction

Linters have been widely used in programming, especially in industrial programming, whereas little attention has been paid to linters for beginners in programming.

Fundamental programming courses of universities and colleges has invariably concentrated on important and functional programming languages, such as programming language C. However, for beginners in programming, only a portion of the programming language C is important and functional, while other portions do not deserve much attention and some of them even need to be avoided to be used.

Therefore, in order to help beginners in programming, especially students in fundamental programming courses of universities and colleges, we design LFBs to provide WFBs.

Notice that some concepts mentioned above are defined as follows.

## LFB

To design a linter for beginners, assume that $\mathcal{L}$ is a programming language and $\mathcal{L}_{x}$ is a version of $\mathcal{L}$, then $\mathrm{SS} \mathcal{L}_{x}$ is a programming language for beginners based on $\mathcal{L}_{x}$ and $\mathrm{SS}_{y} \mathcal{L}_{x}$ is a version of $\mathrm{SS} \mathcal{L}_{x}$. As a simplified $\mathcal{L}_{x}$ for beginners, $\mathrm{SS} \mathcal{L}_{x}$ should be a subset of $\mathcal{L}_{x}$, thus each code of $\mathrm{SS} \mathcal{L}_{x}$ is a code of $\mathcal{L}_{x}$ and $\mathcal{L}_{x}$ is called the foundation of $\mathrm{SS} \mathcal{L}_{x}$.

A linter is an $\mathrm{SS}_{y} \mathcal{L}_{x}$ LFB if and only if it satisfies:

- Each code of $\mathrm{SS}_{y} \mathcal{L}_{x}$ is accepted without $\mathrm{SS}_{y} \mathcal{L}_{x}$ WFBs.

- Each code not of $\mathrm{SS}_{y} \mathcal{L}_{x}$ but of $\mathcal{L}_{x}$ is accepted with $\mathrm{SS}_{y} \mathcal{L}_{x}$ WFBs.

- Each code not of $\mathcal{L}_{x}$ is rejected with or without $\mathrm{SS}_{y} \mathcal{L}_{x}$ WFBs.

## WFB

A warning is a $\mathrm{SS}_{y} \mathcal{L}_{x}$ WFB if and only if it is contained by one of the following:

- Warnings for beginners about danger defined in $\mathrm{SS}_{y} \mathcal{L}_{x}$ indicate behaviors which are dangerous for beginners, such as implementation-defined behaviors, locale-specific behaviors, undefined behaviors and unspecified behaviors. For beginners, these behaviors have to be avoided, because they are too intractable to be completely mastered and can simply lead to a lot of troubles.

- Warnings for beginners about discouragement defined in $\mathrm{SS}_{y} \mathcal{L}_{x}$ indicate behaviors which are discouraged for beginners, such as operations about tricky operator precedence without parentheses. For beginners, these behaviors should be avoided, because they are often caused by some common misunderstandings or often lead to some common misunderstandings.

- Warnings for beginners about strangeness defined in $\mathrm{SS}_{y} \mathcal{L}_{x}$ indicate behaviors which are strange for beginners, such as comma operations and empty statements. For beginners, these behaviors are suggested be avoided, because they can be simply replaced by other behaviors which are more formal.