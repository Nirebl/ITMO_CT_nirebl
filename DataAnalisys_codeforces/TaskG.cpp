#include <iostream>
#include <vector>
#include <iomanip>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int N, K;
    cin >> N >> K;

    vector<int> c(N);
    for (int i = 0; i < N; i++) {
        cin >> c[i];
    }

    vector<long long> countA(K + 1, 0LL), countB(K + 1, 0LL);

    for (int i = 0; i < N; i++) {
        countB[c[i]]++;
    }

    long long sumSqA = 0, sumSqB = 0;
    for (int j = 1; j <= K; j++) {
        sumSqB += countB[j] * countB[j];
    }

    long long sizeA = 0, sizeB = N;

    cout << fixed << setprecision(9);
    for (int i = 0; i < N - 1; i++) {
        int cls = c[i];

        {
            long long oldCnt = countB[cls];
            long long newCnt = oldCnt - 1;
            sumSqB = sumSqB - oldCnt * oldCnt + newCnt * newCnt;
            countB[cls] = newCnt;
        }
        {
            long long oldCnt = countA[cls];
            long long newCnt = oldCnt + 1;
            sumSqA = sumSqA - oldCnt * oldCnt + newCnt * newCnt;
            countA[cls] = newCnt;
        }

        sizeA++;
        sizeB--;

        long double partA = (long double) sumSqA / (long double) sizeA;
        long double partB = (long double) sumSqB / (long double) sizeB;
        long double score = 1.0L - (1.0L / (long double) N) * (partA + partB);

        cout << (long double) score << "\n";
    }

    return 0;
}
