# Linter for Beginners

## Warnings for beginners

Warnings for beginners are as follows:

- Warnings for beginners about danger indicate behaviors which are dangerous for beginners, such as implementation-defined behaviors, locale-specific behaviors, undefined behaviors and unspecified behaviors.

- Warnings for beginners about discouragement indicate behaviors which are discouraged for beginners, such as operations about error-prone operator precedence without parentheses.

- Warnings for beginners about strangeness indicate behaviors which are strange for beginners, such as empty statements.

## How

To design a linter for beginners, assume that $\mathcal{L}$ is a programming language and $\mathcal{L}_{x}$ is a version of $\mathcal{L}$, then $\mathrm{SS} \mathcal{L}_{x}$ is a programming language for beginners based on $\mathcal{L}_{x}$ and $\mathrm{SS}_{y} \mathcal{L}_{x}$ is a version of $\mathrm{SS} \mathcal{L}_{x}$. As a simplified $\mathcal{L}_{x}$ for beginners, $\mathrm{SS} \mathcal{L}_{x}$ should be a subset of $\mathcal{L}_{x}$, thus each code of $\mathrm{SS} \mathcal{L}_{x}$ is a code of $\mathcal{L}_{x}$ and $\mathcal{L}_{x}$ is called the foundation of $\mathrm{SS} \mathcal{L}_{x}$.

Furthermore, a linter is an $\mathrm{SS}_{y} \mathcal{L}_{x}$ LFB if and only if it satisfies:

- Each code of $\mathrm{SS}_{y} \mathcal{L}_{x}$ is accepted without warnings for beginners defined in $\mathrm{SS}_{y} \mathcal{L}_{x}$.

- Each code not of $\mathrm{SS}_{y} \mathcal{L}_{x}$ but of $\mathcal{L}_{x}$ is accepted with warnings for beginners defined in $\mathrm{SS}_{y} \mathcal{L}_{x}$.

- Each code not of $\mathcal{L}_{x}$ is rejected with or without warnings for beginners defined in $\mathrm{SS}_{y} \mathcal{L}_{x}$.