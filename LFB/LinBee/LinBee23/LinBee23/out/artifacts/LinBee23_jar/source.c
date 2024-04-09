#include <stdio.h>
int a, b, c;
int main() {
	scanf("%d%d", &a, b);
	c = a * b - (a == b);
	printf("%d", c);
	return c, 0;
}
