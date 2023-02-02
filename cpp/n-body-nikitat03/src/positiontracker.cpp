#include "positiontracker.h"

#include <fstream>
#include <sstream>

PositionTracker::PositionTracker(const std::string & filename)
{
    std::ifstream fin(filename);
    fin >> m_size;

    std::string line;
    std::getline(fin, line);

    while (std::getline(fin, line)) {
        std::stringstream ss(line);

        Cartesian pos;
        Cartesian vel;
        double mass = 0;
        std::string name;

        ss >> pos.x >> pos.y >> vel.x >> vel.y >> mass >> name;

        m_bodies.emplace(std::make_pair(name, Body(pos, vel, mass, name)));
    }
}

PositionTracker::Bodies & PositionTracker::get_bodies()
{
    return m_bodies;
}

double PositionTracker::get_size() const
{
    return m_size;
}
