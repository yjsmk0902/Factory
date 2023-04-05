//#G5-2096 [내려가기]
//유형 - DP / 슬라이딩 윈도우
//  최솟값과 최댓값의 저장공간을 만들어 마지막부터 거꾸로
//	DP 방식을 활용하여 순차적으로 찾음

#include<iostream>
#include<vector>
#define LEFT 0
#define CENTER 1
#define RIGHT 2
using namespace std;

int N, ans_min, ans_max;
int Line[100001][3];
vector<int> DP_min;
vector<int> DP_max;
void Input();
void Output();
void Solve();
int main() {
	cin.tie(NULL);
	ios_base::sync_with_stdio(false);

	Input();
	Solve();
	Output();

	return 0;
}
void Solve() {
	DP_min.emplace_back(Line[1][LEFT]);
	DP_min.emplace_back(Line[1][CENTER]);
	DP_min.emplace_back(Line[1][RIGHT]);
	DP_max.emplace_back(Line[1][LEFT]);
	DP_max.emplace_back(Line[1][CENTER]);
	DP_max.emplace_back(Line[1][RIGHT]);
	for (int i = 2; i <= N; i++) {
		int Left_min = min(DP_min[LEFT], DP_min[CENTER]);
		int Left_max = max(DP_max[LEFT], DP_max[CENTER]);
		int Right_min = min(DP_min[CENTER], DP_min[RIGHT]);
		int Right_max = max(DP_max[CENTER], DP_max[RIGHT]);
		DP_min.clear();
		DP_max.clear();
		DP_min.emplace_back(Left_min + Line[i][LEFT]);
		DP_min.emplace_back(min(Left_min, Right_min) + Line[i][CENTER]);
		DP_min.emplace_back(Right_min + Line[i][RIGHT]);
		DP_max.emplace_back(Left_max + Line[i][LEFT]);
		DP_max.emplace_back(max(Left_max, Right_max) + Line[i][CENTER]);
		DP_max.emplace_back(Right_max + Line[i][RIGHT]);
	}
	ans_min = min(min(DP_min[LEFT], DP_min[RIGHT]), DP_min[CENTER]);
	ans_max = max(max(DP_max[LEFT], DP_max[RIGHT]), DP_max[CENTER]);
}
void Input() {
	cin >> N;
	for (int i = 1; i <= N; i++) {
		for (int j = 0; j < 3; j++) {
			cin >> Line[i][j];
		}
	}
}
void Output() {
	cout << ans_max << " " << ans_min;
}