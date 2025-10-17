# celebrate_light.py
# Streamlit app that embeds a creative p5.js animation celebrating "light".
# Run: pip install streamlit && streamlit run celebrate_light.py

import streamlit as st
from textwrap import dedent

st.set_page_config(page_title="Celebrate Light", layout="wide", initial_sidebar_state="expanded")
st.title("Celebrate Light ✨ — tiny interactive animation")
st.markdown("Use the sliders to tweak the particles, colors, and motion. Export a screenshot with the button if you want bragging rights.")

# Sidebar controls
st.sidebar.header("Animation controls")
particle_count = st.sidebar.slider("Particle count", 50, 1200, 280, step=10)
spread = st.sidebar.slider("Spread (how far particles travel)", 0.1, 2.0, 0.9)
speed = st.sidebar.slider("Speed", 0.1, 3.0, 1.0)
size = st.sidebar.slider("Base particle size", 1, 40, 9)
bloom = st.sidebar.slider("Bloom intensity (glow)", 0.0, 3.0, 1.2, step=0.1)
palette = st.sidebar.selectbox("Color palette", ["Warm sunset", "Aurora", "Cool neon", "Golden candle"])

# Palette definitions (simple)
palettes = {
    "Warm sunset": ["#FF6B6B", "#FFD93D", "#FF9F1C", "#F77F00"],
    "Aurora": ["#7DF9FF", "#8AFFC1", "#3CFFB2", "#6B8CFF"],
    "Cool neon": ["#19FFFD", "#9D19FF", "#FF19E6", "#19FF6B"],
    "Golden candle": ["#FFF3B0", "#FFD166", "#F4A261", "#E76F51"]
}
colors = palettes[palette]

st.sidebar.write("Tip: increase bloom and use fewer, larger particles for dramatic results.")

# A small utility to let user download a screenshot via canvas.toDataURL triggered by button
if st.button("Take screenshot (opens in new tab)"):
    st.markdown(
        """
        <script>
        // Ask the sketch to export via a custom event.
        window.dispatchEvent(new CustomEvent('export_screenshot'));
        </script>
        """,
        unsafe_allow_html=True
    )

# Embed the p5.js sketch via components.html
from streamlit.components.v1 import html

# Pass parameters into the embedded HTML using f-string. Escape carefully.
html_code = dedent(f"""
<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Celebrate Light</title>
    <style>
      html,body {{ margin:0; height:100%; background:#050308; overflow:hidden; }}
      #sketch-holder {{ width:100%; height:80vh; display:block; }}
      .controls-note {{ color: #aaa; font-family: sans-serif; font-size:12px; margin-left:10px; }}
      .screenshot-link {{ display:none; }}
    </style>
  </head>
  <body>
    <div id="sketch-holder"></div>
    <!-- p5.js from CDN -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/p5.js/1.6.0/p5.min.js"></script>
    <script>
      const params = {{
        particle_count: {particle_count},
        spread: {spread},
        speed: {speed},
        base_size: {size},
        bloom: {bloom},
        colors: {colors}
      }};

      let particles = [];
      let cnv;

      function setup() {{
        const container = document.getElementById('sketch-holder');
        cnv = createCanvas(container.clientWidth, container.clientHeight);
        cnv.parent('sketch-holder');
        pixelDensity(1);
        initParticles();
        noStroke();
        // make background dark
        background(5,3,8);
      }}

      function windowResized() {{
        const container = document.getElementById('sketch-holder');
        resizeCanvas(container.clientWidth, container.clientHeight);
      }}

      function initParticles() {{
        particles = [];
        for (let i = 0; i < params.particle_count; i++) {{
          particles.push(new LightParticle());
        }}
      }}

      class LightParticle {{
        constructor() {{
          this.reset(true);
        }}
        reset(startRandom=false) {{
          this.cx = width/2 + (random(-1,1) * width * 0.02);
          this.cy = height/2 + (random(-1,1) * height * 0.02);
          const ang = random(TWO_PI);
          const r = random(10, min(width,height) * 0.5 * params.spread);
          this.x = this.cx + cos(ang) * r * (startRandom ? random() : 0.01);
          this.y = this.cy + sin(ang) * r * (startRandom ? random() : 0.01);
          this.vx = (this.x - this.cx) * 0.0005 * params.speed * random(0.4,1.6);
          this.vy = (this.y - this.cy) * 0.0005 * params.speed * random(0.4,1.6);
          this.size = random(params.base_size * 0.4, params.base_size * 1.6);
          this.life = random(0.6, 1.4);
          this.age = random(0, this.life);
          this.col = color(random(params.colors));
          this.hueOffset = random(-20,20);
        }}
        update(dt) {{
          // gentle outward motion with perlin-like wobble
          const t = frameCount * 0.003;
          this.vx += (noise(this.x*0.001, t) - 0.5) * 0.02 * params.speed;
          this.vy += (noise(this.y*0.001, t+10) - 0.5) * 0.02 * params.speed;
          this.x += this.vx * (1 + 0.2 * sin(frameCount*0.01));
          this.y += this.vy * (1 + 0.2 * cos(frameCount*0.01));
          this.age += dt;
          if (this.age > this.life * 60) this.reset();
        }}
        draw() {{
          // draw several soft layers for bloom
          blendMode(ADD);
          const ageRatio = (this.age / (this.life * 60));
          const alpha = (1 - ageRatio) * 0.9;
          const s = this.size * (0.6 + ageRatio*1.6);
          for (let k=4; k>=0; k--) {{
            const r = s * (1 + k*0.8);
            const a = alpha * (0.18 / (k+1));
            fill(red(this.col), green(this.col), blue(this.col), a*255*params.bloom);
            ellipse(this.x, this.y, r, r);
          }}
          blendMode(BLEND);
        }}
      }}

      function draw() {{
        // fade background with a low-alpha rectangle to create trails
        push();
        fill(5,3,8, 12);
        rect(0,0,width,height);
        pop();

        const dt = 1;
        for (let p of particles) {{
          p.update(dt);
          p.draw();
        }}

        // subtle center glow pulse
        push();
        blendMode(ADD);
        const g = max(width,height) * 0.45 * pow(0.97, frameCount*0.02);
        for (let i=0;i<4;i++) {{
          const s = g * (0.2 + i*0.15) * (0.8 + 0.2*sin(frameCount*0.02 + i));
          fill(255, 200, 150, 8 * params.bloom);
          ellipse(width/2, height/2, s, s);
        }}
        blendMode(BLEND);
        pop();
      }}

      // Listen for screenshot export
      window.addEventListener('export_screenshot', () => {{
        // force one crisp frame and open in new tab
        const data = document.querySelector('canvas').toDataURL('image/png');
        const w = window.open('', '_blank');
        const img = new Image();
        img.src = data;
        img.style.width = '100%';
        w.document.body.style.background = '#000';
        w.document.body.style.margin = '0';
        w.document.body.appendChild(img);
      }});

      // Simple keyboard controls
      window.addEventListener('keydown', (e) => {{
        if (e.key === 'r') initParticles(); // reset
      }});

      // small responsiveness tweak: if params change (via streamlit), they'll be reloaded
      // but Streamlit injection will re-run the component, so no special handling required.
    </script>
  </body>
</html>
""")

# width and height look nice
html(html_code, height=720