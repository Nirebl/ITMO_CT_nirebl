#include <iostream>
#include <vector>
#include <numeric>

using namespace std;

int main() {
    int k;
    cin >> k;

    vector<vector<int>> m(k, vector<int>(k));
    vector<int> tp, fp, fn;
    vector<double> prec, rc, f_val;

    int cnt = 0;
    vector<int> k_normal;

    for (int i = 0; i < k; ++i) {
        for (int j = 0; j < k; ++j) {
            cin >> m[i][j];
        }
    }

    for (int i = 0; i < k; ++i) {
        int temp = accumulate(m[i].begin(), m[i].end(), 0);
        cnt += temp;
        k_normal.push_back(temp);

        int fps = 0;
        int tps = m[i][i];
        for (int j = 0; j < k; ++j) {
            fps += m[j][i];
        }
        fps -= tps;

        int fneg = temp - tps;

        double pp = (tps + fps > 0) ? static_cast<double>(tps) / (tps + fps) : 0;
        double r = (tps + fneg > 0) ? static_cast<double>(tps) / (tps + fneg) : 0;
        double fval2 = (pp + r > 0) ? 2 * pp * r / (pp + r) : 0;

        tp.push_back(tps);
        fp.push_back(fps);
        fn.push_back(fneg);
        prec.push_back(pp);
        rc.push_back(r);
        f_val.push_back(fval2);
    }

    int tpm = 0, fpm = 0, fnm = 0;
    for (int i = 0; i < k; ++i) {
        tpm += tp[i] * k_normal[i];
        fpm += fp[i] * k_normal[i];
        fnm += fn[i] * k_normal[i];
    }

    double prm = (tpm + fpm > 0) ? static_cast<double>(tpm) /
                                   (tpm + fpm) : 0;
    double rm = (tpm + fnm > 0) ? static_cast<double>(tpm) /
                                  (tpm + fnm) : 0;
    double fvm = (prm + rm > 0) ? (2 * prm * rm) /
                                  (prm + rm) : 0;

    double pm = 0, rcm = 0;
    for (int i = 0; i < k; ++i) {
        pm += prec[i] * k_normal[i];
        rcm += rc[i] * k_normal[i];
    }

    double f_value_macro = (pm + rcm > 0) ? (2 * pm * rcm) /
                                            (pm + rcm) / cnt
                                          : 0;

    double fvn = 0;
    for (int i = 0; i < k; ++i) {
        fvn += static_cast<double>(k_normal[i] * f_val[i]) / cnt;
    }

    cout.precision(6);
    cout << fixed << fvm << endl;
    cout << fixed << f_value_macro << endl;
    cout << fixed << fvn << endl;

    return 0;
}
