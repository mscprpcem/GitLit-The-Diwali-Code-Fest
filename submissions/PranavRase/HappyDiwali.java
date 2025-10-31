/*
 * File   : HappyDiwali.java
 * Author : Pranav Gopalrao Rase
 * Credits: AI-assisted (ChatGPT - GPT-5)
 * Desc   : Diwali animation with fireworks, blinking greeting, diyas & twinkling stars.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.geom.Ellipse2D;

public class HappyDiwali extends JPanel {
    private final java.util.List<Particle> particles = Collections.synchronizedList(new ArrayList<>());
    private final java.util.List<Star> stars = new ArrayList<>();
    private final Random rnd = new Random();
    private float pulseAlpha = 1.0f;
    private boolean fadingOut = true;
    private final javax.swing.Timer timer;

    public HappyDiwali() {
        setPreferredSize(new Dimension(900, 560));
        setBackground(new Color(8, 12, 28));
        createStars(900, 560);

        addMouseListener(new MouseAdapter() { public void mousePressed(MouseEvent e){ launchCracker(e.getX(), e.getY()); }});
        addKeyListener(new KeyAdapter() { public void keyPressed(KeyEvent e){ if(e.getKeyCode()==KeyEvent.VK_SPACE) launchCracker(getWidth()/2,getHeight()/3); }});
        setFocusable(true);

        timer = new javax.swing.Timer(16, e -> { updateAll(); repaint(); });
        timer.start();
    }

    /** Create random stars across sky */
    private void createStars(int w, int h) {
        stars.clear();
        for (int i = 0; i < Math.max(200, w * h / 4000); i++)
            stars.add(new Star(rnd.nextInt(w), rnd.nextInt(h / 2 + 100), rnd.nextFloat()));
    }

    /** Launch upward shell */
    private void launchCracker(int x, int y) {
        particles.add(new Shell(x, getHeight(), -6 - rnd.nextDouble() * 3, randomBrightColor()));
    }

    private Color randomBrightColor() {
        return Color.getHSBColor(rnd.nextFloat(), 0.8f, 0.95f);
    }

    /** Update blink, particles & stars */
    private void updateAll() {
        pulseAlpha += fadingOut ? -0.02f : 0.02f;
        if (pulseAlpha <= 0.3f || pulseAlpha >= 1.0f) fadingOut = !fadingOut;

        stars.forEach(Star::update);
        synchronized (particles) {
            var it = particles.iterator();
            var newOnes = new ArrayList<Particle>();
            while (it.hasNext()) {
                Particle p = it.next(); p.update();
                if (p instanceof Shell s && s.shouldExplode()) {
                    for (int i = 0; i < 40; i++) {
                        double a = rnd.nextDouble() * Math.PI * 2, sp = 2 + rnd.nextDouble() * 2.5;
                        newOnes.add(new Spark(s.x, s.y, Math.cos(a)*sp, Math.sin(a)*sp, s.color));
                    }
                    it.remove();
                } else if (p.isDead()) it.remove();
            }
            particles.addAll(newOnes);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        int w = getWidth(), h = getHeight();

        g2.setPaint(new GradientPaint(0, 0, new Color(12,16,38), 0, h, new Color(40,12,60)));
        g2.fillRect(0, 0, w, h);

        stars.forEach(s -> s.draw(g2));
        synchronized (particles) { particles.forEach(p -> p.draw(g2)); }
        drawGreeting(g2, w, h);
        drawDiyas(g2, w, h);
        drawTopText(g2, w);

        g2.dispose();
    }

    private void drawTopText(Graphics2D g2, int w) {
        String msg = "Click to launch crackers • SPACE for center burst • Made by Pranav";
        g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        int x = (w - g2.getFontMetrics().stringWidth(msg)) / 2;
        g2.setColor(Color.WHITE);
        g2.drawString(msg, x, 24);
    }

    private void drawGreeting(Graphics2D g2, int w, int h) {
        String text = "Happy Diwali!";
        g2.setFont(new Font("Serif", Font.BOLD, Math.max(32, w / 12)));
        int x = (w - g2.getFontMetrics().stringWidth(text)) / 2;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, pulseAlpha));
        g2.setColor(new Color(255, 200, 80));
        g2.drawString(text, x, h / 2);
        g2.setComposite(AlphaComposite.SrcOver);
    }

    private void drawDiyas(Graphics2D g2, int w, int h) {
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        int y = h - 20;
        g2.drawString("🪔", w/8, y);
        g2.drawString("🪔", w/2 - 20, y);
        g2.drawString("🪔", 7*w/8 - 40, y);
    }

    // --- Inner Classes ---
    private class Star {
        float x, y, b; boolean dim = rnd.nextBoolean();
        Star(float x, float y, float b){ this.x=x; this.y=y; this.b=b; }
        void update(){ b += dim ? -0.02f : 0.02f; if(b<0.3f||b>1f) dim=!dim; }
        void draw(Graphics2D g){ g.setColor(new Color(255,255,255,(int)(255*b))); g.fill(new Ellipse2D.Double(x,y,2,2)); }
    }

    private abstract class Particle {
        double x,y,vx,vy; int life,max;
        Particle(double x,double y,double vx,double vy,int life){ this.x=x;this.y=y;this.vx=vx;this.vy=vy;this.life=this.max=life; }
        void update(){ x+=vx; y+=vy; vy+=0.03; vx*=0.996; vy*=0.996; life--; }
        boolean isDead(){ return life<=0; }
        abstract void draw(Graphics2D g2);
    }

    private class Shell extends Particle {
        Color color;
        Shell(double x,double y,double vy,Color c){ super(x,y,0,vy,200); color=c; }
        boolean shouldExplode(){ return vy>0 || y<100; }
        void draw(Graphics2D g2){ g2.setColor(color); g2.fillOval((int)x-3,(int)y-3,6,6); }
    }

    private class Spark extends Particle {
        Color color;
        Spark(double x,double y,double vx,double vy,Color c){ super(x,y,vx,vy,40+rnd.nextInt(50)); color=c; }
        void draw(Graphics2D g2){ float r=(float)life/max; g2.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),(int)(220*r))); g2.fill(new Ellipse2D.Double(x-2,y-2,4,4)); }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Happy Diwali – Crackers, Stars & Diyas");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(new HappyDiwali());
            f.pack(); f.setLocationRelativeTo(null); f.setVisible(true);
        });
    }
}
