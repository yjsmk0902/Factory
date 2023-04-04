//#G5-1717 [집합의 표현]
//유형 - 자료구조 / 분리집합 / 유니온-파인드
//  유니온-파인드 구조를 활용해 집함을 분리시켜 검수함

#include<bits/stdc++.h>
using namespace std;

int n, m;
int parent[1000001];

//파인드
int find(int x) {
	if (parent[x] < 0)	return x;
	else {
		int temp = find(parent[x]);
		parent[x] = temp;
		return temp;
	}
}

//유니온
void Union(int x, int y) {
	x = find(x);
	y = find(y);
	if (x == y)	 return;
	if (parent[x] < parent[y]) {
		parent[x] += parent[y];
		parent[y] = x;
	}
	else {
		parent[y] += parent[x];
		parent[x] = y;
	}
}

int main() {
	cin.tie(NULL);	cout.tie(NULL);
	ios_base::sync_with_stdio(false);

	memset(parent, -1, sizeof(parent));
	cin >> n >> m;
	while (m--) {
		int inst, a, b;
		cin >> inst >> a >> b;
		switch (inst) {
		case 0:
			Union(a, b);
			break;
		case 1:
			if (find(a) == find(b))	cout << "YES\n";
			else					cout << "NO\n";
			break;
		}
	}
	return 0;
}