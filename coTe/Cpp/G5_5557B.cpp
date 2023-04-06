//#G5-5557 [1학년]
//유형 - DP
//  흔한 DP 문제
//  2차원 배열을 만들어 가능한 경우를 모두 기록함

#include<iostream>
using namespace std;

int N;
int num[101] = { 0 };
long long DP[101][21] = { 0 };
int main() {
	cin.tie(NULL);	cout.tie(NULL);
	ios_base::sync_with_stdio(false);

	cin >> N;
	for (int i = 1; i <= N; i++) {
		cin >> num[i];
	}
	DP[1][num[1]]++;
	for (int i = 2; i < N; i++) {
		for (int j = 0; j <= 20; j++) {
			if (DP[i - 1][j]) {
				if (j + num[i] <= 20)	DP[i][j + num[i]] += DP[i - 1][j];
				if (j - num[i] >= 0)	DP[i][j - num[i]] += DP[i - 1][j];
			}
		}
	}
	cout << DP[N - 1][num[N]];
	return 0;
}