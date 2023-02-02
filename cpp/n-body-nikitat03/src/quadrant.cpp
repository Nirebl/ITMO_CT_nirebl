#include "quadrant.h"

#include <math.h>

const double Theta = 0.5;

Quadrant::Quadrant(const Cartesian & center, double length)
    : m_center(center)
    , m_length(length)
{
}

bool Quadrant::is_leaf() const
{
    return !m_nw;
}

void Quadrant::split()
{
    double l2 = m_length / 2;
    double l4 = m_length / 4;

    m_nw = std::make_unique<Quadrant>(Cartesian(m_center.x - l4, m_center.y + l4), l2);
    m_ne = std::make_unique<Quadrant>(Cartesian(m_center.x + l4, m_center.y + l4), l2);
    m_sw = std::make_unique<Quadrant>(Cartesian(m_center.x - l4, m_center.y - l4), l2);
    m_se = std::make_unique<Quadrant>(Cartesian(m_center.x + l4, m_center.y - l4), l2);
}

void Quadrant::insert_to_sub_q(const Body & b)
{
    if (m_nw->contains(b.get_pos()))
        m_nw->insert(b);
    else if (m_ne->contains(b.get_pos()))
        m_ne->insert(b);
    else if (m_sw->contains(b.get_pos()))
        m_sw->insert(b);
    else if (m_se->contains(b.get_pos()))
        m_se->insert(b);
}

void Quadrant::insert(const Body & b)
{
    if (is_empty()) {
        m_body.emplace(b);
        return;
    }

    if (is_leaf()) {
        split();

        insert_to_sub_q(b);
        insert_to_sub_q(m_body.value());
    }
    else {
        insert_to_sub_q(b);
    }

    m_body.emplace(m_body->plus(b));
}

bool Quadrant::contains(const Cartesian & p) const
{
    double l2 = m_length / 2;

    return (p.x >= (m_center.x - l2) && p.x <= (m_center.x + l2) && p.y >= (m_center.y - l2) && p.y <= (m_center.y + l2));
}

bool Quadrant::is_empty() const
{
    return !m_body;
}

void Quadrant::update_force(Body & b)
{
    if (is_empty())
        return;

    if (!is_leaf()) {
        double d = sqrt(m_body->distance2(b));
        if (m_length / d < Theta)
            b.add_force(m_body.value());
        else {
            m_nw->update_force(b);
            m_ne->update_force(b);
            m_sw->update_force(b);
            m_se->update_force(b);
        }
    }
    else {
        if (b.get_name() != m_body->get_name())
            b.add_force(m_body.value());
    }
}

std::ostream & operator<<(std::ostream & out, const Quadrant & qd)
{
    return out << "Center X: " << qd.m_center.x
               << " Y: " << qd.m_center.y
               << " Length: " << qd.m_length;
}
