#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

long long sumPairwiseDistances(const vector<long long> &v) {
    auto n = (long long) v.size();
    if (n < 2) return 0;
    vector<long long> pref(n + 1, 0);
    for (int i = 0; i < n; i++) pref[i + 1] = pref[i] + v[i];
    long long s = 0;
    for (int j = 0; j < n; j++) {
        long long leftCount = j;
        long long sumLeft = pref[j];
        s += leftCount * v[j] - sumLeft;
    }
    return s;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    int K, N;
    cin >> K >> N;
    vector<vector<long long>> cls(K + 1);
    vector<long long> allX;
    allX.reserve(N);
    for (int i = 0; i < N; i++) {
        long long x;
        int y;
        cin >> x >> y;
        allX.push_back(x);
        cls[y].push_back(x);
    }
    sort(allX.begin(), allX.end());
    long long sum_all = sumPairwiseDistances(allX);
    long long sum_intra = 0;
    for (int c = 1; c <= K; c++) {
        if (!cls[c].empty()) {
            sort(cls[c].begin(), cls[c].end());
            sum_intra += sumPairwiseDistances(cls[c]);
        }
    }
    long long sum_inter = sum_all - sum_intra;

    cout << 2LL * sum_intra << "\n" << 2LL * sum_inter << "\n";
    return 0;
}
