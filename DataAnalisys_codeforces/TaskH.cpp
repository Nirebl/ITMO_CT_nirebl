#include <iostream>
#include <vector>
#include <unordered_map>
#include <cmath>
#include <iomanip>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int Kx, Ky;
    cin >> Kx >> Ky;

    int N;
    cin >> N;

    vector<long long> nx(Kx + 1, 0LL);
    unordered_map<long long, long long> nxy;
    nxy.reserve(N);

    auto to_key = [&](int x, int y){
        return ((long long)x << 20) ^ (long long)y;
    };

    for(int i = 0; i < N; i++){
        int x, y;
        cin >> x >> y;
        nx[x]++;
        nxy[to_key(x, y)]++;
    }

    long double B = 0.0L, C = 0.0L;
    for(int x = 1; x <= Kx; x++){
        if(nx[x] > 0) C += (long double)nx[x] * logl((long double)nx[x]);
    }
    for(const auto &kv : nxy){
        long long cnt = kv.second;
        if(cnt > 0) B += (long double)cnt * logl((long double)cnt);
    }

    long double H = (C - B) / (long double)N;
    cout << fixed << setprecision(9) << H << "\n";
    return 0;
}
