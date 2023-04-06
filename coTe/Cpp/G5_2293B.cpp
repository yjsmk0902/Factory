//#G5-2293 [동전 1]
//유형 - DP
//  흔하디 흔한 DP 문제

#include<iostream>
using namespace std;

int n, k;
int coin[101];
int DP[10001];
int main() {
	cin.tie(NULL);	cout.tie(NULL);
	ios_base::sync_with_stdio(false);

	cin >> n >> k;
	for (int i = 1; i <= n; i++) {
		cin >> coin[i];
	}
    
	DP[0] = 1;
	for (int i = 1; i <= n; i++) {
		for (int j = coin[i]; j <= k; j++) {
			DP[j] = DP[j] + DP[j - coin[i]];
		}
	}
	cout << DP[k];
	return 0;
}