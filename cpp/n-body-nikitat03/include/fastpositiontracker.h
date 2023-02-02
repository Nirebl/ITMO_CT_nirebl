#pragma once

#include "positiontracker.h"

class FastPositionTracker
    : public PositionTracker
{
public:
    FastPositionTracker(const std::string & filename);

    Track track(const std::string & body_name, size_t end_time, size_t time_step) override;
};
