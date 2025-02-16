#include <ios>
#include <iostream>
#include <vector>
#include <algorithm>
#include <iomanip>
#include <sstream>
#include <queue>

using namespace std;
static const int BSZ = 1 << 22;
static char inbuf[BSZ];
int iptr = 0, ilen = 0;

inline int getChar() {
    if (iptr == ilen) {
        ilen = (int) fread(inbuf, 1, BSZ, stdin);
        iptr = 0;
    }
    if (iptr == ilen) return EOF;
    return inbuf[iptr++];
}

template<typename T>
void readInt(T &val) {
    int c = getChar();
    while (c <= ' ') {
        if (c == EOF) return;
        c = getChar();
    }
    bool neg = false;
    if (c == '-') {
        neg = true;
        c = getChar();
    }
    val = 0;
    while (c >= '0' && c <= '9') {
        val = val * 10 + (c - '0');
        c = getChar();
    }
    if (neg) val = -val;
}

static char outbuf[BSZ];
int optr = 0;

inline void flushOut() {
    fwrite(outbuf, 1, optr, stdout);
    optr = 0;
}

inline void putStr(const string &s) {
    for (char c: s) {
        if (optr == BSZ) flushOut();
        outbuf[optr++] = c;
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    readInt(n);
    vector<pair<long long, long long>> pts(n);
    for (int i = 0; i < n; i++) {
        readInt(pts[i].first);
        readInt(pts[i].second);
    }

    sort(pts.begin(), pts.end());

    int m;
    readInt(m);
    vector<string> answers(m);

    for (int qi = 0; qi < m; qi++) {
        long long xq;
        int kq;
        readInt(xq);
        readInt(kq);

        if (kq > n) {
            answers[qi] = "-1\n";
            continue;
        }

        priority_queue<pair<long long, int>> pq;
        for (int i = 0; i < n; i++) {
            long long dist = llabs(pts[i].first - xq);
            pq.push({dist, i});
            if (pq.size() > kq) pq.pop();
        }

        long long sumY = 0;
        while (!pq.empty()) {
            sumY += pts[pq.top().second].second;
            pq.pop();
        }

        long double ans = (long double) sumY / kq;
        ostringstream oss;
        oss << fixed << setprecision(6) << ans << "\n";
        answers[qi] = oss.str();
    }

    for (auto &s: answers) {
        putStr(s);
    }
    flushOut();
    return 0;
}
