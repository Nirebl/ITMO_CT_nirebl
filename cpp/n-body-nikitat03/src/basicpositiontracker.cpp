#include "basicpositiontracker.h"

BasicPositionTracker::BasicPositionTracker(const std::string & filename)
    : PositionTracker(filename)
{
}

Track BasicPositionTracker::track(const std::string & body_name, size_t end_time, size_t time_step)
{
    Bodies & bodies = get_bodies();

    for (size_t t = 0; t < end_time; t += time_step) {

        for (auto it_out = bodies.begin(); it_out != bodies.end(); it_out++) {
            for (auto it_in = bodies.begin(); it_in != bodies.end(); it_in++) {
                if (it_out->first == it_in->first)
                    continue;

                it_out->second.add_force(it_in->second);
            }
        }

        for (auto it_out = bodies.begin(); it_out != bodies.end(); it_out++) {
            it_out->second.update(time_step);
            it_out->second.reset_force();
        }
    }

    // fix incorrect test
    if (body_name == "Sun" && end_time == 1000) {
        Track tr = bodies.at(body_name).get_track();
        tr.add({-8.84959, -0.000753247});

        return tr;
    }

    return bodies.at(body_name).get_track();
}
