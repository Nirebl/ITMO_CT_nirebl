#pragma once

#include "body.h"
#include "cartesian.h"

#include <iostream>
#include <memory>
#include <optional>

// Quadrant representation, required for Problem 2
class Quadrant
{
private:
    Cartesian m_center;
    double m_length;

    std::optional<Body> m_body;

    std::unique_ptr<Quadrant> m_nw;
    std::unique_ptr<Quadrant> m_ne;
    std::unique_ptr<Quadrant> m_sw;
    std::unique_ptr<Quadrant> m_se;

private:
    bool is_leaf() const;
    void split();

    void insert_to_sub_q(const Body & b);

    bool contains(const Cartesian & p) const;
    bool is_empty() const;

public:
    // Create quadrant with center (x, y) and size 'length'
    Quadrant(const Cartesian & center, double length);

    void insert(const Body & b);

    void update_force(Body & b);

    friend std::ostream & operator<<(std::ostream &, const Quadrant &);
};