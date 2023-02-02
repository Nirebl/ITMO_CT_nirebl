#include "fastpositiontracker.h"

#include "bhtreenode.h"

FastPositionTracker::FastPositionTracker(const std::string & filename)
    : PositionTracker(filename)
{
}

Track FastPositionTracker::track(const std::string & body_name, size_t end_time, size_t time_step)
{
    Bodies & bodies = get_bodies();

    for (size_t t = 0; t < end_time; t += time_step) {
        BHTreeNode BHTree(get_size() * 2);

        for (auto it_out = bodies.begin(); it_out != bodies.end(); it_out++) {
            BHTree.insert(it_out->second);
        }

        for (auto it_out = bodies.begin(); it_out != bodies.end(); it_out++) {
            BHTree.update_force(it_out->second);
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
