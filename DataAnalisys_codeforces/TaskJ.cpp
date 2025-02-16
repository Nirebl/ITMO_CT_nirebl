#include <iostream>
#include <vector>
#include <algorithm>
#include <iomanip>
using namespace std;

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    int N;
    cin >> N;
    vector<pair<long long,int>> arrX(N), arrY(N);
    vector<long long> x(N), y(N);
    for(int i = 0; i < N; i++){
        cin >> x[i] >> y[i];
    }
    for(int i = 0; i < N; i++){
        arrX[i] = {x[i], i};
        arrY[i] = {y[i], i};
    }
    sort(arrX.begin(), arrX.end(), [](auto &a, auto &b){ return a.first < b.first; });
    sort(arrY.begin(), arrY.end(), [](auto &a, auto &b){ return a.first < b.first; });
    vector<long long> rankX(N), rankY(N);
    for(int k = 0; k < N; k++){
        rankX[arrX[k].second] = k + 1;
    }
    for(int k = 0; k < N; k++){
        rankY[arrY[k].second] = k + 1;
    }
    long long sumSq = 0;
    for(int i = 0; i < N; i++){
        long long d = rankX[i] - rankY[i];
        sumSq += d * d;
    }
    long long nn = 1LL * N * (N * (long long)N - 1);
    long double rho = 1.0L - 6.0L * (long double)sumSq / (long double)nn;
    cout << fixed << setprecision(9) << rho << "\n";
    return 0;
}
