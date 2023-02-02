#pragma once

#include "body.h"
#include "track.h"

#include <map>

class PositionTracker
{
protected:
    using Bodies = std::map<std::string, Body>;

    PositionTracker(const std::string & filename);
    virtual ~PositionTracker() = default;

    Bodies & get_bodies();
    double get_size() const;

private:
    double m_size;

    Bodies m_bodies;

public:
    virtual Track track(const std::string & body_name, size_t end_time, size_t time_step) = 0;
};
