//#G5-5582 [공통 부분 문자열]
//유형 - DP / 문자열
//  흔한 DP 문제 ver.2
//  마찬가지로 2차원 배열을 만들어 가능한 경우를 모두 기록함
//  기록함과 동시에 최대여부를 계속 확인하면서 기록

#include<iostream>
using namespace std;
string s1 = " ", s2 = " ", str;
int DP[4001][4001];
int ans = 0;
int main() {
	cin.tie(NULL);
	ios_base::sync_with_stdio(false);

	for (int i = 0; i <= s1.length(); i++) {
		DP[i][0] = 0;
	}
	for (int j = 0; j <= s2.length(); j++) {
		DP[0][j] = 0;
	}
	cin >> str;
	s1 += str;
	cin >> str;
	s2 += str;
	for (int i = 1; i <= s1.length() - 1; i++) {
		for (int j = 1; j <= s2.length() - 1; j++) {
			if (s1[i] == s2[j]) {
				DP[i][j] = DP[i - 1][j - 1] + 1;
			}
			else {
				DP[i][j] = 0;
			}
			ans = max(ans, DP[i][j]);
		}
	}
	cout << ans;
	return 0;
}