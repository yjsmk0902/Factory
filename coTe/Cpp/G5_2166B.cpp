//#G5-2166 [다각형의 면적]
//유형 - 기하학 / 다각형의 넓이
//  CCW(Counter Clock Wise) 알고리즘을 활용하여 넓이를 구함

#include<iostream>
#include<vector>
using namespace std;

int N;
double ans = 0;
vector<pair<double, double>> point;
double CCW(pair<double, double> a, pair<double, double> b, pair<double, double> c) {
	return a.first * b.second + b.first * c.second + c.first * a.second -
		(a.second * b.first + b.second * c.first + c.second * a.first);
}
int main() {
	cin.tie(NULL);
	ios_base::sync_with_stdio(false);

	cin >> N;
	double a, b;
	while (N--) {
		cin >> a >> b;
		point.push_back({ a,b });
	}
	for (int i = 1; i < point.size() - 1; i++) {
		ans += CCW(point[0], point[i], point[i + 1]) / (double)2;
	}
	cout << fixed;
	cout.precision(1);
	cout << abs(ans);
	return 0;
}