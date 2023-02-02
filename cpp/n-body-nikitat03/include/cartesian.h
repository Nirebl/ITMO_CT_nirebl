#pragma once

struct Cartesian
{
    double x;
    double y;

    Cartesian() = default;

    Cartesian(double ax, double ay)
        : x(ax)
        , y(ay)
    {
    }
};