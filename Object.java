class Bird {

  PVector loc, vel, grav, acc, mouse;
  float a, s;

  Bird() {
    loc = new PVector(100, height/2);
    vel = new PVector(0, 0);
    grav = new PVector(0, 0.4);
    acc = new PVector(0, 0);
    a = alpha(100);
    s = 70;
  }

  void show() {
    pushMatrix();
    translate(loc.x, loc.y);
    float angle = map(vel.y, -30, 50, -PI/6, PI/2); 
    rotate(angle);
    if (mousePressed && mode == PLAYING) {
     image(bird1, 0, 0, s, s);
    } else {
      image(bird3, 0, 0, s, s);
    }
    popMatrix();
  }

  void move() {
    if (loc.y <= 650) {
      loc.add(vel);
      vel.add(grav);
      vel.limit(15);
    } else if (loc.y >= 650) {  
      loc.add(0, 2);
    }
  }

  void flap() {
    if (mousePressed) {
      flapSound.play();
      flapSound.rewind();
    }
    mouse = new PVector(mouseX, -200);
    acc = PVector.sub(mouse, loc);
    acc.normalize();
    acc.mult(18);
    vel.add(acc);
    vel.limit(8);
    if (loc.y <= -30.0) {
      vel.limit(0);
    }
  }
}
