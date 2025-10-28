#include <iostream>
#include <thread>
#include <chrono>
#include <cstdlib>
#include <ctime>
using namespace std;

// ANSI color codes
string colors[] = {
    "\033[31m", "\033[33m", "\033[32m", "\033[36m", "\033[35m", "\033[34m"
};
const string RESET = "\033[0m";
const string CLEAR = "\033[2J\033[H";

const int WIDTH = 150;
const int HEIGHT = 35;

// ASCII Art for "HAPPY DIWALI"
string banner[] = {
"##   ##    ###    #####   #####   ##   ##       #####   ##  ##     ##    ###    ##       ## ",
"##   ##   ## ##   ##  ##  ##  ##   ## ##        ##  ##  ##  ##     ##   ## ##   ##       ## ",
"#######  ##   ##  #####   ######    ###         ##  ##  ##  ##  #  ##  ##   ##  ##       ## ",
"##   ##  #######  ##      ##        ###         ##  ##  ##  ## ### ##  #######  ##       ## ",
"##   ##  ##   ##  ##      ##        ###         #####   ##   ### ###   ##   ##  #######  ## "
};

// Firework particles (now non-accumulating)
void print_fireworks(int color_index) {
    string sparks[4] = {"*", "o", ".", "+"};

    // Clear only the firework area before redrawing
    for (int y = 2; y < 8; y++) {
        cout << "\033[" << y << ";1H" << string(WIDTH, ' ');
    }

    // Draw new sparks each frame
    for (int i = 0; i < 8; i++) { // moderate density
        int x = rand() % (WIDTH - 10) + 5;
        int y = rand() % 6 + 2;
        cout << "\033[" << y << ";" << x << "H"
             << colors[(color_index + i) % 6] << sparks[rand() % 4] << RESET;
    }
}

// Banner in the center
void print_banner(int color_index) {
    int start_y = 10;
    int start_x = 20;
    for (int i = 0; i < 5; i++) {
        cout << "\033[" << (start_y + i) << ";" << start_x << "H"
             << colors[(color_index + i) % 6] << banner[i] << RESET;
    }
}

// Diyas flickering at the bottom
void print_diyas(int color_index, bool flip) {
    int base_y = HEIGHT - 4;
    string flame = flip ? "^" : "'";
    cout << "\033[" << base_y << ";10H";
    for (int i = 0; i < 15; i++) {
        cout << colors[(color_index + i) % 6] << "   ( )   ";
    }
    cout << "\n";
    cout << "\033[" << (base_y + 1) << ";10H";
    for (int i = 0; i < 15; i++) {
        cout << colors[(color_index + 2 + i) % 6] << "  ( " << flame << " )  ";
    }
    cout << "\n";
    cout << "\033[" << (base_y + 2) << ";10H";
    for (int i = 0; i < 15; i++) {
        cout << colors[(color_index + 4 + i) % 6] << " --'---'--";
    }
    cout << RESET;
}

int main() {
    srand(time(0));

    cout << CLEAR;

    bool flip = false;
    for (int frame = 0; frame < 150; frame++) {
        print_fireworks(frame);
        print_banner(frame);
        print_diyas(frame, flip);
        flip = !flip;
        this_thread::sleep_for(chrono::milliseconds(120)); // smooth timing
    }

    cout << "\033[" << (HEIGHT + 1) << ";1H" << colors[3]
         << " Wishing you a Bright, Colorful and HAPPY DIWALI! " << RESET << endl;

    return 0;
}
