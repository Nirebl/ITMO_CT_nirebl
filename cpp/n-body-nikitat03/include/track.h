#pragma once

#include "cartesian.h"

#include <vector>

class Track
{
private:
    std::vector<Cartesian> m_movies;

public:
    void add(const Cartesian & value);

    const Cartesian & back() const;
};
