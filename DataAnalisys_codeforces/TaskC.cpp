#include <iostream>
#include <vector>
#include <unordered_map>
#include <cmath>
#include <iomanip>

using namespace std;

int main() {
    int N, K;
    cin >> N >> K;

    vector<int> class_labels(N);
    vector<int> values(N);

    for (int i = 0; i < N; ++i) {
        cin >> class_labels[i];
    }

    for (int i = 0; i < N; ++i) {
        cin >> values[i];
    }

    double mean_values = 0;
    for (int i = 0; i < N; ++i) {
        mean_values += values[i];
    }
    mean_values /= N;

    double sum_squared_diffs = 0;
    double total_deviation = 0;

    unordered_map<int, vector<int>> class_to_indices;

    for (int i = 0; i < N; ++i) {
        int class_index = class_labels[i] - 1;
        class_to_indices[class_index].push_back(i);
        double deviation = values[i] - mean_values;
        total_deviation += deviation;
        sum_squared_diffs += deviation * deviation;
    }

    double weighted_sum = 0;

    for (int class_index = 0; class_index < K; ++class_index) {
        if (class_to_indices.find(class_index) == class_to_indices.end()) {
            continue;
        }
        vector<int>& current_indices = class_to_indices[class_index];
        int current_class_size = current_indices.size();
        double class_size_ratio = static_cast<double>(current_class_size) / N;

        double numerator = 0;
        double class_deviation_sum = 0;

        for (int index : current_indices) {
            double deviation = values[index] - mean_values;
            numerator += (1 - class_size_ratio) * deviation;
            class_deviation_sum += deviation;
        }
        numerator += (total_deviation - class_deviation_sum) * (-class_size_ratio);

        double denominator = ((1 - class_size_ratio) * (1 - class_size_ratio)) * current_class_size + (class_size_ratio * class_size_ratio) * (N - current_class_size);

        double pearson_coefficient;
        if ((denominator * sum_squared_diffs) > 0) {
            pearson_coefficient = numerator / sqrt(denominator * sum_squared_diffs);
        } else {
            pearson_coefficient = 0;
        }

        weighted_sum += current_class_size * pearson_coefficient;
    }

    double result = weighted_sum / N;

    cout << fixed << setprecision(9) << result << endl;

    return 0;
}
