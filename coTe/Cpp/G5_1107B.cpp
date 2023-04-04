//#G5-1107 [리모컨]
//유형 - 브루트포스
//  브루트포스 알고리즘을 사용하는 문제인 만큼
//  하나하나 다 만들어서 풀어야하는 문제
//  리모컨을 하나 만들어서 문제에 적용시킴

#include<iostream>
#include<vector>
using namespace std;

class Remote_control {
public:
	bool button[10];

	Remote_control() {
		for (int i = 0; i < 10; i++) {
			button[i] = true;
		}
	}
	Remote_control(vector<int> a) {
		for (int i = 0; i < 10; i++) {
			button[i] = true;
		}
		for (int i = 0; i < a.size(); i++) {
			button[a[i]] = false;
		}
	}
	bool is_Button(int channel) {
		int temp = channel;
		if (temp == 0) {
			return button[0];
		}
		while (temp != 0) {
			if (!button[temp % 10]) {
				return false;
			}
			else {
				temp /= 10;
			}
		}
		return true;
	}
	int click_count(int channel) {
		int temp = channel, count = 0;
		if (temp == 0) {
			return 1;
		}
		while (temp != 0) {
			temp /= 10;
			count++;
		}
		return count;
	}
};

int main() {
	cin.tie(NULL);
	ios_base::sync_with_stdio(false);

	long int N, M, temp, answer;
	vector<int> Error_button;

	cin >> N >> M;
	for (int i = 0; i < M; i++) {
		cin >> temp;
		Error_button.push_back(temp);
	}
	Remote_control RC(Error_button);
	temp = 0;
	answer = abs(N - 100);
	for (long int i = 0; i <= 999999; i++) {
		if (RC.is_Button(i)) {
			temp += RC.click_count(i);
			temp += abs(N - i);
			if (temp < answer) {
				answer = temp;
			}
		}
		else {
			continue;
		}
		temp = 0;
	}
	cout << answer;

	return 0;
}