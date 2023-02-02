#pragma once

#include "cartesian.h"
#include "track.h"

#include <iostream>

// Single body representation, required for Problem 1 and Problem 2
class Body
{
private:
    Cartesian m_curPos;
    Cartesian m_curVel;
    const double m_mass;
    const std::string m_name;

    Cartesian m_curAccn;
    Track m_track;

public:
    const std::string & get_name() const;
    const Track & get_track() const;
    const Cartesian & get_pos() const;

public:
    Body(const Cartesian & pos_0, const Cartesian & vel_0, double mass, const std::string & name);

    double distance2(const Body & b) const;

    // calculate the force-on current body by the 'b' and add the value to accumulated force value
    void add_force(const Body & b);
    // reset accumulated force value
    void reset_force();

    // update body's velocity and position
    void update(double delta_t);

    friend std::ostream & operator<<(std::ostream &, const Body &);

    // Create new body representing center-of-mass of the invoking body and 'b'
    Body plus(const Body & b);
};
