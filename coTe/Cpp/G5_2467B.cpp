//#G5-2467 [용액]
//유형 - 이분탐색 / 두 포인터
//  이분탐색과 포인터를 활용하여 모든 경우에 대한 값을 구해
//  0과 가장 가까운 값을 구함
//  해당 값을 기록하여 출력

#include<iostream>
#include<vector>
#define L long long
#define INF 99876543210
using namespace std;

int N;
vector<L> liquid;
pair<L, L> ans = { INF,INF };
int main() {
	cin.tie(NULL);
	ios_base::sync_with_stdio(false);

	cin >> N;
	int temp;
	while (N--) {
		cin >> temp;
		liquid.emplace_back(temp);
	}
	if (liquid.size() == 2) {
		cout << liquid[0] << " " << liquid[1];
		return 0;
	}
	for (int i = 0; i < liquid.size(); i++) {
		L comp = liquid[i];
		int left = 0, right = liquid.size() - 1;
		while (right - left > 1) {
			int mid = (left + right) / 2;
			if (abs(liquid[mid] + comp) < abs(liquid[mid + 1] + comp)) {
				right = mid;
			}
			else {
				left = mid;
			}
			if (right == i) {
				right = i - 1;
			}
			if (left == i) {
				left = i + 1;
			}
		}
		L ex1 = abs(liquid[left] + comp);
		L ex2 = abs(liquid[right] + comp);
		if (ex1 < ex2) {
			ans = (abs(ans.first + ans.second) < ex1 ? ans : (left > i ? make_pair(liquid[i], liquid[left]) : make_pair(liquid[left], liquid[i])));
		}
		else {
			ans = (abs(ans.first + ans.second) < ex2 ? ans : (right > i ? make_pair(liquid[i], liquid[right]) : make_pair(liquid[right], liquid[i])));
		}
		liquid[i] = comp;
	}
	cout << ans.first << " " << ans.second;
	return 0;
}