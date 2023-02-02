#pragma once

struct BoardInfo {
    bool isClosed;
    long long cost;
    const Board * pParent;
};