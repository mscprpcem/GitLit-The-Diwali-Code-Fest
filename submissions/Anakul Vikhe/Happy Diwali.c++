#include <iostream>
#include <string>
#include <unistd.h> // For usleep (pause), often included with <chrono> in modern C++

// ANSI Color Codes (may not work on all terminals, e.g., default Windows cmd)
#define RED "\033[31m"
#define YELLOW "\033[33m"
#define RESET "\033[0m"
#define BOLD "\033[1m"

using namespace std;

void print_diwali_burst(int iterations) {
    string diya = "🪔";
    string firework = "💥";
    string stars = "🌟";
    string spaces(20, ' ');

    cout << "\n\n";

    for (int i = 0; i < iterations; ++i) {
        // Clear the line and move the cursor back to the start
        cout << "\r" << spaces;

        // Print the animation elements
        if (i % 2 == 0) {
            cout << YELLOW << BOLD << diya << RESET << " " << firework;
        } else {
            cout << RED << BOLD << diya << RESET << " " << stars;
        }

        // Print the message
        if (i == iterations - 1) {
             cout << "\t" << BOLD << YELLOW << "Happy Diwali!" << RESET;
        } else {
            cout << "\t" << YELLOW << "Lighting the way..." << RESET;
        }

        cout.flush(); // Ensure the output is immediately shown
        usleep(300000); // Pause for 0.3 seconds (300,000 microseconds)
    }
}

int main() {
    // Print a header
    cout << "\n\n" << string(50, '*') << endl;
    cout << BOLD << YELLOW << "\t\tTHE FESTIVAL OF LIGHTS" << RESET << endl;
    cout << string(50, '*') << endl;

    // Run the animated burst
    print_diwali_burst(10); // 10 iterations of the animation

    // Final, static message
    cout << "\n" << BOLD << YELLOW << "\n\tWISHING YOU AND YOUR FAMILY PEACE, PROSPERITY, AND HAPPINESS!" << RESET << endl;
    cout << string(50, '*') << "\n" << endl;

    return 0;
}
