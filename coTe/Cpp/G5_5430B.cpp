//#G5-5430 [AC]
//유형 - 구현 / 자료구조 / 문자열 / 파싱 / 덱
//  흔한 노가다 문제
//  큐를 사용하여 자료를 구성하고 활용함

#include<iostream>
#include<vector>
#include<deque>
#include<algorithm>
using namespace std;

int main() {
	cin.tie(NULL);
	ios_base::sync_with_stdio(false);

	int T, num, temp;
	char ch;
	bool button = false, error = false;
	string function;
	deque<int> queue;
	cin >> T;
	for (int i = 0; i < T; i++) {
		cin >> function >> num;
		cin.get(ch);		//enter
		cin.get(ch);		//[
		for (int j = 0; j < num; j++) {
			cin >> temp;
			cin.get(ch);	//,
			queue.push_front(temp);
		}
		cin.get(ch);		//]
		for (int j = 0; j < function.length(); j++) {
			if (function[j] == 'R') {
				button ? button = false : button = true;
			}
			else {
				if (queue.size() == 0) {
					cout << "error\n";
					error = true;
					break;
				}
				if (button) {
					queue.pop_front();
				}
				else {
					queue.pop_back();
				}
			}
		}
		if (error) {
			button = false;
			error = false;
			continue;
		}
		if (button) {
			cout << "[";
			for (int j = 0; j < queue.size(); j++) {
				if (j == queue.size() - 1) {
					cout << queue[j] << "]\n";
					break;
				}
				cout << queue[j] << ",";
			}
			if (queue.size() == 0) {
				cout << "]\n";
			}
		}
		else {
			cout << "[";
			for (int j = queue.size() - 1; j >= 0; j--) {
				if (j == 0) {
					cout << queue[j] << "]\n";
					break;
				}
				cout << queue[j] << ",";
			}
			if (queue.size() == 0) {
				cout << "]\n";
			}
		}
		for (int j = 0; queue.size() != 0; j++) {
			queue.pop_back();
		}
		button = false;
	}
	return 0;
}