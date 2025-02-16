#include <vector>
#include <ios>
#include <iostream>
#include <cmath>

using namespace std;

static const int N = 9;

void solveLinearSystem(vector<vector<double>> &A, vector<double> &b) {
    for (int i = 0; i < N; i++) {
        double maxAbsVal = fabs(A[i][i]);
        int pivot = i;
        for (int r = i + 1; r < N; r++) {
            double val = fabs(A[r][i]);
            if (val > maxAbsVal) {
                maxAbsVal = val;
                pivot = r;
            }
        }
        if (pivot != i) {
            swap(A[i], A[pivot]);
            swap(b[i], b[pivot]);
        }

        if (fabs(A[i][i]) < 1e-12) {
            continue;
        }

        double diagVal = A[i][i];
        for (int c = i; c < N; c++) {
            A[i][c] /= diagVal;
        }
        b[i] /= diagVal;

        for (int r = 0; r < N; r++) {
            if (r == i) continue;
            double ratio = A[r][i];
            for (int c = i; c < N; c++)
                A[r][c] -= ratio * A[i][c];
            b[r] -= ratio * b[i];
        }
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    const double PI = 3.14159265358979323846;
    vector<double> m = {12, 24, 168, 672};

    static const int T = 168;
    vector<double> yData(T);
    for (int t = 0; t < T; t++) {
        int val;
        cin >> val;
        yData[t] = val;
    }

    vector<vector<double>> M(T, vector<double>(N, 0.0));
    for (int t = 0; t < T; t++) {
        int tt = t + 1;
        M[t][0] = 1.0;
        int col = 1;
        for (int i = 0; i < (int) m.size(); i++) {
            double angle = 2.0 * PI * double(tt) / m[i];
            M[t][col++] = sin(angle);
            M[t][col++] = cos(angle);
        }
    }

    vector<vector<double>> A(N, vector<double>(N, 0.0));
    vector<double> B(N, 0.0);

    for (int r = 0; r < N; r++) {
        for (int c = 0; c < N; c++) {
            double sumRC = 0.0;
            for (int t = 0; t < T; t++) {
                sumRC += M[t][r] * M[t][c];
            }
            A[r][c] = sumRC;
        }
        double sumR = 0.0;
        for (int t = 0; t < T; t++) {
            sumR += M[t][r] * yData[t];
        }
        B[r] = sumR;
    }

    solveLinearSystem(A, B);

    for (int t = 0; t < T; t++) {
        int tt = t + 1 + 168;
        double prediction = 0.0;
        prediction += B[0];
        int col = 1;
        for (int i = 0; i < (int) m.size(); i++) {
            double angle = 2.0 * PI * double(tt) / m[i];
            double s = sin(angle);
            double c = cos(angle);
            double part = B[col] * s + B[col + 1] * c;
            col += 2;
            prediction += part;
        }
        long long outVal = (long long) llround(prediction);
        cout << outVal << "\n";
    }

    return 0;
}
