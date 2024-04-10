#include <stdio.h>
int counter = 0;
int f(int a, int b) {
	counter++;
	return a * b - a - b;
}
int main() {
	int (*p)(int,int) = &f;
	int t;
	t = (*p)(3, 5.0);
	return 0;
}
