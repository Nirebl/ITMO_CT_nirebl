#include <iostream>
#include <vector>
#include <string>
#include <unordered_map>
#include <cmath>

using namespace std;

unordered_map<string, double> calculateTransitionMatrix(const vector<string>& strings, int excludeIndex) {
    unordered_map<string, double> transitions;
    unordered_map<char, double> total;

    for (int i = 0; i < strings.size(); ++i) {
        if (i == excludeIndex) continue;
        const string& str = strings[i];
        for (size_t j = 0; j + 1 < str.size(); ++j) {
            if (str[j] != ' ' && str[j + 1] != ' ') {
                string pair = string(1, str[j]) + str[j + 1];
                transitions[pair]++;
                total[str[j]]++;
            }
        }
    }

    for (auto& [pair, count] : transitions) {
        transitions[pair] /= total[pair[0]];
    }

    return transitions;
}

double calculateProbability(const string& str, const unordered_map<string, double>& transitions) {
    double probability = 1.0;
    for (size_t j = 0; j + 1 < str.size(); ++j) {
        if (str[j] != ' ' && str[j + 1] != ' ') {
            string pair = string(1, str[j]) + str[j + 1];
            if (transitions.count(pair)) {
                probability *= transitions.at(pair);
            } else {
                probability *= 1e-6;
            }
        }
    }
    return probability;
}

int main() {
    int N;
    cin >> N;
    cin.ignore();

    vector<string> strings(N);
    for (int i = 0; i < N; ++i) {
        getline(cin, strings[i]);
    }

    int anomalyIndex = -1;
    double minProbability = 1e9;

    for (int i = 0; i < N; ++i) {
        unordered_map<string, double> transitions = calculateTransitionMatrix(strings, i);
        double probability = calculateProbability(strings[i], transitions);
        if (probability < minProbability) {
            minProbability = probability;
            anomalyIndex = i;
        }
    }

    cout << anomalyIndex + 1 << endl;

    return 0;
}
