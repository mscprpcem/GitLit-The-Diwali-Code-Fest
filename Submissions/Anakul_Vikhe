// diya_rgb.ino
// Connect: Red -> PWM 9, Green -> PWM 10, Blue -> PWM 11
const int R = 9;
const int G = 10;
const int B = 11;

void setup() {
  pinMode(R, OUTPUT);
  pinMode(G, OUTPUT);
  pinMode(B, OUTPUT);
}

void loop() {
  // Smoothly pulse warm flame-like color (mix of red + yellow)
  for (int t=0; t<1024; t++) {
    float phase = (sin(t * 2.0 * PI / 1024.0) + 1.0) / 2.0; // 0..1
    // flame = interpolate between deep orange and bright yellow
    int red = (int)(155 + 100 * phase);   // ~155..255
    int green = (int)(60 + 120 * phase);  // ~60..180
    int blue = (int)(10 + 30 * (1.0-phase)); // small bluish dimming
    analogWrite(R, red);
    analogWrite(G, green);
    analogWrite(B, blue);
    delay(4);
  }
  // random small flicker
  for (int i=0;i<10;i++){
    analogWrite(R, random(170,255));
    analogWrite(G, random(70,150));
    analogWrite(B, random(5,25));
    delay(random(20,120));
  }
}
