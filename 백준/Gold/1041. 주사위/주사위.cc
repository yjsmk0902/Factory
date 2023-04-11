#include<iostream>
#include<algorithm>
#include<vector>
using namespace std;
int main() {
	int temp;
	long long int N, answer = 0;
	vector<int> face;
	vector<int> sorting_face;
	vector<int> two_face;
	vector<int> three_face;
	cin >> N;
	for (int i = 0; i < 6; i++) {
		cin >> temp;
		face.push_back(temp);
	}
	sorting_face = face;
	sort(sorting_face.begin(), sorting_face.end());
	if (N == 1) {
		for (int i = 0; i < 5; i++) {
			answer += sorting_face[i];
		}
	}
	else {
		answer += sorting_face[0] * (N - 2) * (N - 1) * 4 + sorting_face[0] * (N - 2) * (N - 2);
		for (int i = 0; i < 5; i++) {
			for (int j = i + 1; j < 6; j++) {
				if ((i == 0 && j == 5) || (i == 1 && j == 4) || (i == 2 && j == 3)) {
				}
				else {
					two_face.push_back(face[i] + face[j]);
				}
			}
		}
		sort(two_face.begin(), two_face.end());
		answer += two_face[0] * (N - 1) * 4 + two_face[0] * (N - 2) * 4;
		for (int i = 0; i < 4; i++) {
			for (int j = i + 1; j < 5; j++) {
				for (int k = j + 1; k < 6; k++) {
					if ((i == 0 && k == 5) ||
						(i == 1 && j == 4) || (i == 1 && k == 4) || (j == 1 && k == 4) ||
						(i == 2 && j == 3) || (j == 2 && k == 3)) {
					}
					else {
						three_face.push_back(face[i] + face[j] + face[k]);
					}
				}
			}
		}
		sort(three_face.begin(), three_face.end());
		answer += three_face[0] * 4;
	}
	cout << answer;
	return 0;
}