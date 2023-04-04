//#G5-1011 [Fly me to the Alpha Centauri]
//유형 - 일반 수학
//	제곱수를 지날 때마다 횟수가 1회 추가됨
//	따라서 제곱근을 구하고 제곱수일 경우 제곱근 * 2
//	지나지 않을경우 제곱근 * 2 - 1을 구하여 정답을 구함

#include<iostream>
#include<vector>
#include<math.h>
using namespace std;

int main() {
	int Case;
	long long int x, y, temp1;
	long double temp;
	vector<int> answer;
	cin >> Case;
	for (int test = 0; test < Case; test++) {
		cin >> x >> y;
		temp = sqrt(y - x);
		temp1 = sqrt(y - x) + 1;
		if (temp == temp1 - 1) {
			answer.push_back((temp1 - 1) * 2 - 1);
		}
		else if (temp <= sqrt(temp1 * temp1 - temp1)) {
			answer.push_back((temp1 - 1) * 2);
		}
		else if (temp <= sqrt(temp1 * temp1)) {
			answer.push_back(temp1 * 2 - 1);
		}
	}	
	for (int test = 0; test < Case; test++) {
		cout << answer[test] << endl;
	}
	return 0;
}