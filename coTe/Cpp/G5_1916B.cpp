//#G5-1916 [최소비용 구하기]
//유형 - 그래프 / 다익스트라
//	조건으로 입력받은 정보들을 그래프 형태로 저장하고
//  다잌스트라 알고리즘을 통해 최소비용을 구함

#include<iostream>
#include<vector>
#include<queue>
#define INF 987654321
using namespace std;
vector<pair<int, int>> info[300001];
int ans[20001];
void Dijksta_algorithm(int start) {
	ans[start] = 0;
	priority_queue<pair<int, int>> q;
	q.push({ 0,start });
	while (q.size()) {
		int cur = q.top().second;
		int cur_val = -q.top().first;
		q.pop();
		if (ans[cur] < cur_val) {
			continue;
		}
		for (int i = 0; i < info[cur].size(); i++) {
			int next = info[cur][i].first;
			int next_val = cur_val + info[cur][i].second;
			if (ans[next] > next_val) {
				ans[next] = next_val;
				q.push({ -ans[next],next });
			}
		}
	}
}
int main() {
	cin.tie(NULL);
	ios_base::sync_with_stdio(false);

	int N, M, start, end, u, v, w;
	cin >> N >> M;
	for (int i = 1; i <= N; i++) {
		ans[i] = INF;
	}

	for (int i = 0; i < M; i++) {
		cin >> u >> v >> w;
		info[u].push_back({ v,w });
	}
	cin >> start >> end;
	Dijksta_algorithm(start);
	cout << ans[end];
	return 0;
}