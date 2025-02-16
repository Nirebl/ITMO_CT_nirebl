#include <ios>
#include <iostream>
#include <vector>
#include <unordered_map>
#include <unordered_set>
#include <iomanip>
#include <cmath>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int K;
    cin >> K;

    vector<double> lambda(K);
    for (int c = 0; c < K; ++c) {
        cin >> lambda[c];
    }

    double alpha;
    cin >> alpha;

    int N;
    cin >> N;

    vector<int> countClass(K, 0);

    unordered_map<string, int> word2id;
    word2id.reserve(2000000);
    word2id.max_load_factor(0.7f);

    int vocabularySize = 0;
    vector<vector<int> > nWC;

    for (int i = 0; i < N; ++i) {
        int c_i;
        cin >> c_i;
        --c_i;
        int L_i;
        cin >> L_i;

        countClass[c_i]++;

        unordered_set<int> usedWords;
        usedWords.reserve(L_i);

        for (int j = 0; j < L_i; ++j) {
            string w;
            cin >> w;
            auto it = word2id.find(w);
            int idx;
            if (it == word2id.end()) {
                idx = vocabularySize;
                word2id[w] = idx;
                vocabularySize++;
                nWC.resize(vocabularySize, vector<int>(K, 0));
            } else {
                idx = it->second;
            }
            usedWords.insert(idx);
        }
        for (auto idx: usedWords) {
            nWC[idx][c_i]++;
        }
    }

    vector<long double> logPrior(K);
    for (int c = 0; c < K; ++c) {
        if (countClass[c] == 0) {
            logPrior[c] = -1e15;
        } else {
            logPrior[c] = logl((long double) countClass[c]) - logl((long double) N);
        }
    }

    vector<long double> sumLog0(K, 0.0L);
    vector<vector<long double> > logP1(vocabularySize, vector<long double>(K));
    vector<vector<long double> > logP0(vocabularySize, vector<long double>(K));

    for (int w = 0; w < vocabularySize; ++w) {
        for (int c = 0; c < K; ++c) {
            long double p1 = 0.0L;
            if (countClass[c] == 0) {
                p1 = alpha / (2.0L * alpha);
            } else {
                p1 = ((long double) nWC[w][c] + alpha)
                     / ((long double) countClass[c] + 2.0L * alpha);
            }
            logP1[w][c] = logl(p1);
            long double tmp = 1.0L - p1;
            if (tmp < 1e-18L) tmp = 1e-18L;
            logP0[w][c] = logl(tmp);
        }
    }

    for (int c = 0; c < K; ++c) {
        long double s = 0.0L;
        for (int w = 0; w < vocabularySize; ++w) {
            s += logP0[w][c];
        }
        sumLog0[c] = s;
    }

    int M;
    cin >> M;


    cout << fixed << setprecision(9);


    for (int _m = 0; _m < M; ++_m) {
        int L;
        cin >> L;
        unordered_set<int> used;
        used.reserve(L);

        for (int j = 0; j < L; ++j) {
            string w;
            cin >> w;
            auto it = word2id.find(w);
            if (it != word2id.end()) {
                used.insert(it->second);
            }
        }

        vector<long double> logScores(K);
        long double maxLog = -1e15L;
        for (int c = 0; c < K; ++c) {
            long double val = logl(lambda[c]) + logPrior[c] + sumLog0[c];
            for (auto widx: used) {
                val += (logP1[widx][c] - logP0[widx][c]);
            }
            logScores[c] = val;
            if (val > maxLog) {
                maxLog = val;
            }
        }

        long double sumExp = 0.0L;
        for (int c = 0; c < K; ++c) {
            long double e = expl(logScores[c] - maxLog);
            logScores[c] = e;
            sumExp += e;
        }

        for (int c = 0; c < K; ++c) {
            long double prob = logScores[c] / sumExp;
            cout << (double) prob;
            if (c + 1 < K) {
                cout << " ";
            }
        }
        cout << "\n";
    }

    return 0;
}
