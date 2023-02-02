#include "bhtreenode.h"

BHTreeNode::BHTreeNode(double size)
    : m_root({0, 0}, size)
{
}

void BHTreeNode::insert(const Body & b)
{
    m_root.insert(b);
}

void BHTreeNode::update_force(Body & b)
{
    m_root.update_force(b);
}
