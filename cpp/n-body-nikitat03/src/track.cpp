#include "track.h"

void Track::add(const Cartesian & value)
{
    m_movies.emplace_back(value);
}

const Cartesian & Track::back() const
{
    return m_movies.back();
}
