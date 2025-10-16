"""
🎆 Happy Diwali Animation by Vaishnavi Nimbalkar 🎆
✨ A festive Python Turtle celebration with fireworks, diya, and greeting ✨
"""

import turtle
import random
import time

# Setup screen
screen = turtle.Screen()
screen.bgcolor("black")
screen.title("🌟 Happy Diwali Celebration 🌟")
screen.setup(width=900, height=700)

# Firework burst animation
def firework(x, y, size=100, layers=3):
    fw = turtle.Turtle()
    fw.hideturtle()
    fw.speed(0)
    fw.penup()
    fw.goto(x, y)
    fw.pendown()
    for layer in range(layers):
        fw.color(random.choice(["red", "yellow", "cyan", "magenta", "orange", "green"]))
        fw.width(layer + 1)
        for _ in range(36):
            fw.forward(size + layer * 10)
            fw.backward(size + layer * 10)
            fw.left(10)
        time.sleep(0.15)

# Glowing diya with flicker
def draw_diya():
    diya = turtle.Turtle()
    diya.hideturtle()
    diya.speed(0)
    diya.penup()
    diya.goto(0, -250)
    diya.color("gold")
    diya.begin_fill()
    diya.circle(60)
    diya.end_fill()

    # Flickering flame
    for _ in range(4):
        diya.goto(0, -170)
        diya.color(random.choice(["orange", "red", "yellow"]))
        diya.begin_fill()
        diya.circle(30)
        diya.end_fill()
        time.sleep(0.3)
        diya.clear()
        diya.penup()
        diya.goto(0, -250)
        diya.color("gold")
        diya.begin_fill()
        diya.circle(60)
        diya.end_fill()

    # Final steady flame
    diya.goto(0, -170)
    diya.color("orange")
    diya.begin_fill()
    diya.circle(30)
    diya.end_fill()

# Animated "Happy Diwali" text
def show_greeting():
    greet = turtle.Turtle()
    greet.hideturtle()
    greet.speed(0)
    greet.penup()
    greet.goto(0, 100)
    message = "🎆✨  Wishing You a Joyous and Bright Diwali! ✨🎆"
    for i in range(len(message)):
        greet.clear()
        greet.color(random.choice(["magenta", "cyan", "yellow", "green", "orange"]))
        greet.write(message[:i+1], align="center", font=("Arial", 36, "bold"))
        time.sleep(0.12)

# Firework show + diya + greeting
def launch_show():
    positions = [(-300, 200), (0, 250), (300, 200), (-150, 300), (150, 300)]
    for pos in positions:
        firework(pos[0], pos[1])
    draw_diya()
    time.sleep(0.5)
    show_greeting()

# Start the show
launch_show()
turtle.done()
