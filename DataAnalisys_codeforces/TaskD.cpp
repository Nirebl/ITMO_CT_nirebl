#include <ios>
#include <iostream>
#include <vector>
#include <iomanip>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    int K, N;
    cin >> K >> N;
    vector<long long> count(K + 1, 0);
    vector<long long> sumY(K + 1, 0);
    vector<long long> sumY2(K + 1, 0);
    for (int i = 0; i < N; i++) {
        int x;
        long long y;
        cin >> x >> y;
        count[x]++;
        sumY[x] += y;
        sumY2[x] += (long long) y * y;
    }
    long double result = 0.0L;
    for (int x = 1; x <= K; x++) {
        if (count[x] == 0) continue;
        auto nx = (long double) count[x];
        auto meanY = (long double) sumY[x] / nx;
        auto meanY2 = (long double) sumY2[x] / nx;
        long double varY = meanY2 - meanY * meanY;
        auto px = nx / (long double) N;
        result += px * varY;
    }
    cout << fixed << setprecision(9) << (long double) result << "\n";
    return 0;
}
