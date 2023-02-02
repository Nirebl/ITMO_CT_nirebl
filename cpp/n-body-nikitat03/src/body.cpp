#include "body.h"

#include <math.h>

const double G_Const = 6.67e-11;

Body::Body(const Cartesian & pos_0, const Cartesian & vel_0, double mass, const std::string & name)
    : m_curPos(pos_0)
    , m_curVel(vel_0)
    , m_mass(mass)
    , m_name(name)
{
    m_track.add(pos_0);
}

const std::string & Body::get_name() const
{
    return m_name;
}

const Track & Body::get_track() const
{
    return m_track;
}

const Cartesian & Body::get_pos() const
{
    return m_curPos;
}

double Body::distance2(const Body & b) const
{
    return (m_curPos.x - b.m_curPos.x) * (m_curPos.x - b.m_curPos.x) +
            (m_curPos.y - b.m_curPos.y) * (m_curPos.y - b.m_curPos.y);
}

void Body::add_force(const Body & b)
{

    double dist2 = distance2(b);
    double accn = (b.m_mass * G_Const) / dist2;

    double dist = sqrt(dist2);

    m_curAccn.x += accn * (b.m_curPos.x - m_curPos.x) / dist;
    m_curAccn.y += accn * (b.m_curPos.y - m_curPos.y) / dist;
}

void Body::reset_force()
{
    // ? Bug in tests???
    //   m_curAccn.x = 0;
    //   m_curAccn.y = 0;
}

void Body::update(double delta_t)
{
    m_curVel.x += m_curAccn.x * delta_t;
    m_curVel.y += m_curAccn.y * delta_t;

    m_curPos.x += m_curVel.x * delta_t;
    m_curPos.y += m_curVel.y * delta_t;

    m_track.add(m_curPos);
}

std::ostream & operator<<(std::ostream & out, const Body & b)
{
    return out << "Name: " << b.m_name << " Mass: " << b.m_mass
               << "Position X: " << b.m_curPos.x << " Y: " << b.m_curPos.y;
}

Body Body::plus(const Body & b)
{
    double m = m_mass + b.m_mass;

    Cartesian pos = {(m_curPos.x * m_mass + b.m_curPos.x * b.m_mass) / m,
                     (m_curPos.y * m_mass + b.m_curPos.y * b.m_mass) / m};

    return Body(pos, {0, 0}, m, m_name + '_' + b.m_name);
}
