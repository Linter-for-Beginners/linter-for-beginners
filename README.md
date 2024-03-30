# Linter for Beginners

Designed for beginners in C programming, SSC is a simplification of C. Therefore, each version of SSC has to be a subset of some version of C.

To design a linter for beginners, assume that $\mathcal{L}$ is a programming language and $\mathcal{L}_{x}$ is a version of $\mathcal{L}$, then $\mathrm{SS} \mathcal{L}_{x}$ is a programming language for beginners based on $\mathcal{L}_{x}$ and $\mathrm{SS}_{y} \mathcal{L}_{x}$ is a version of $\mathrm{SS} \mathcal{L}_{x}$. As a simplified $\mathcal{L}_{x}$ for beginners, $\mathrm{SS} \mathcal{L}_{x}$ should be a subset of $\mathcal{L}_{x}$. Thus $\mathcal{L}_{x}$ is called the foundation of $\mathrm{SS} \mathcal{L}_{x}$, and each code of $\mathrm{SS} \mathcal{L}_{x}$ is a code of $\mathcal{L}_{x}$.

Furthermore, a linter is an $\mathrm{SS}_{y} \mathcal{L}_{x}$ LFB if and only if it satisfies:

- Each code of $\mathrm{SS}_{y} \mathcal{L}_{x}$ is accepted without warnings for beginners defined in $\mathrm{SS}_{y} \mathcal{L}_{x}$.

- Each code not of $\mathrm{SS}_{y} \mathcal{L}_{x}$ but of $\mathcal{L}_{x}$ is accepted with warnings for beginners defined in $\mathrm{SS}_{y} \mathcal{L}_{x}$.

- Each code not of $\mathcal{L}_{x}$ is rejected with or without warnings for beginners defined in $\mathrm{SS}_{y} \mathcal{L}_{x}$.