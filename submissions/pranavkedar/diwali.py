# ==============================================
# Program: Diwali Spark Deluxe
# Author: Pranav S. Kedar
# Purpose: Celebrate Diwali with animated fireworks and sparkling text
# Challenge: GitLit — The Diwali Code Fest (MSC-PRPCEM)
# ==============================================

import time
import sys
import random
import os

# Function to print text with sparkle effect
def sparkle_text(text, delay=0.1):
    colors = ['\033[93m', '\033[91m', '\033[92m', '\033[94m', '\033[95m']  # yellow, red, green, blue, magenta
    reset = '\033[0m'
    for char in text:
        color = random.choice(colors)
        sys.stdout.write(color + char + reset)
        sys.stdout.flush()
        time.sleep(delay)
    print("\n")

# Function for animated fireworks
def fireworks():
    colors = ['\033[91m', '\033[93m', '\033[92m', '\033[94m', '\033[95m']
    for _ in range(3):
        for _ in range(10):
            x = random.randint(0, 60)
            y = random.randint(0, 10)
            char = random.choice(['*', '+', 'x', '•', '✦', '❋'])
            color = random.choice(colors)
            sys.stdout.write(f"\033[{y};{x}H{color}{char}\033[0m")
        sys.stdout.flush()
        time.sleep(0.2)
    print("\033[15;0H")  # Move cursor below fireworks

# ASCII Diya (lamp) for decoration
diya = r"""
        .-.
      .'   `.
     :       :
     :       :
      `.___.'
       _|_|_
      |     |
"""

# Clear terminal and celebration start
os.system('cls' if os.name == 'nt' else 'clear')
print("✨✨✨ Welcome to GitLit — The Diwali Code Fest! ✨✨✨\n")
time.sleep(1)

# Display Diya
print(diya)
time.sleep(1)

# Sparkle Happy Diwali message
sparkle_text("🎆 H A P P Y   D I W A L I 🎆", delay=0.15)

# Fireworks animation
fireworks()

# Closing sparkling message
sparkle_text("May your code and life shine bright like Diwali lights! 💡", delay=0.05)

# Extra sparkle countdown for fun
for i in range(3, 0, -1):
    sparkle_text(f"{i}...🪔🪔🪔", delay=0.5)
sparkle_text("🎉 Celebrate and Code! 🎉", delay=0.1)
# ==============================================
# Program: Diwali Spark Ultimate
# Author: Pranav S. Kedar
# Purpose: Celebrate Diwali with animated fireworks, sparkling text, flickering diya, and confetti
# ==============================================

import time
import sys
import random
import os

# Function to print text with sparkle effect
def sparkle_text(text, delay=0.05):
    colors = ['\033[93m', '\033[91m', '\033[92m', '\033[94m', '\033[95m']
    reset = '\033[0m'
    for char in text:
        color = random.choice(colors)
        sys.stdout.write(color + char + reset)
        sys.stdout.flush()
        time.sleep(delay)
    print()

# Function for flickering diya flame
def flickering_diya(times=6):
    flames = ['\033[91m^', '\033[93m~', '\033[91m*', '\033[93m^']
    base_diya = r"""
        .-.
      .'   `.
     :       :
     :       :
      `.___.'
       _|_|_
      |     |
"""
    for _ in range(times):
        os.system('cls' if os.name == 'nt' else 'clear')
        flame = random.choice(flames)
        diya_display = base_diya.replace('^', flame)
        print(diya_display)
        time.sleep(0.3)

# Function for animated fireworks
def fireworks_explosion():
    colors = ['\033[91m', '\033[93m', '\033[92m', '\033[94m', '\033[95m']
    for _ in range(3):
        for y in range(2, 12):
            for _ in range(3):
                x = random.randint(0, 60)
                char = random.choice(['*', '+', 'x', '•', '✦', '❋'])
                color = random.choice(colors)
                sys.stdout.write(f"\033[{y};{x}H{color}{char}\033[0m")
            sys.stdout.flush()
            time.sleep(0.15)
        # Explosion effect
        for _ in range(10):
            x = random.randint(0, 60)
            y = random.randint(2, 12)
            char = random.choice(['*', '+', 'x', '•', '✦', '❋'])
            color = random.choice(colors)
            sys.stdout.write(f"\033[{y};{x}H{color}{char}\033[0m")
        sys.stdout.flush()
        time.sleep(0.3)
    print("\033[15;0H")

# Function for falling confetti
def falling_confetti():
    colors = ['\033[91m', '\033[93m', '\033[92m', '\033[94m', '\033[95m']
    for _ in range(10):
        for _ in range(30):
            x = random.randint(0, 60)
            y = random.randint(12, 20)
            char = random.choice(['*', '+', '•', '❋'])
            color = random.choice(colors)
            sys.stdout.write(f"\033[{y};{x}H{color}{char}\033[0m")
        sys.stdout.flush()
        time.sleep(0.2)
    print("\033[21;0H")

# Clear screen
os.system('cls' if os.name == 'nt' else 'clear')
print("✨✨✨ Welcome to GitLit — The Diwali Code Fest! ✨✨✨\n")
time.sleep(1)

# Flickering Diya
flickering_diya(times=3)

# Sparkle Happy Diwali message
sparkle_text("🎆 H A P P Y   D I W A L I 🎆", delay=0.1)

# Fireworks animation
fireworks_explosion()

# Closing sparkling message
sparkle_text("May your code and life shine bright like Diwali lights! 💡", delay=0.03)

# Falling confetti
falling_confetti()

# Final celebratory message
sparkle_text("🎉 Celebrate, Code, and Sparkle! 🎉", delay=0.05)
