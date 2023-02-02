#pragma once

#include "positiontracker.h"

#include <string>

class BasicPositionTracker
    : public PositionTracker
{
public:
    BasicPositionTracker(const std::string & filename);

    Track track(const std::string & body_name, size_t end_time, size_t time_step) override;
};
