#pragma once

#include "body.h"
#include "quadrant.h"

// Burnes-Hut tree representation, required for Problem 2
class BHTreeNode
{
private:
    Quadrant m_root;

public:
    BHTreeNode(double size);

    void insert(const Body & b);

    // Update net acting force-on 'b'
    void update_force(Body & b);
};
