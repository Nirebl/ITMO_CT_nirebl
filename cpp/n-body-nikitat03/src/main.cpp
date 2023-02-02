#include "nbody.h"

#include <iomanip>
#include <iostream>
#include <string>

int main()
{
    std::string filename = "test\\etc\\planets.txt";

    BasicPositionTracker tracker(filename);
    //    FastPositionTracker tracker(filename);

    std::string body_name = "Venus";

    Track sun = tracker.track(body_name, 1000, 1);
    auto pos = sun.back();

    std::cout << std::setprecision(9);
    std::cout << body_name << " X: " << pos.x << " Y: " << pos.y << std::endl;

    std::cout << "To be done..." << std::endl;
}
