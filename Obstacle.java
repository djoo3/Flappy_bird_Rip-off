class Obstacle {

  PVector loc, vel;
  float gapH, gapW, w, h, hue;
  boolean check;

  Obstacle(float x) {
    loc = new PVector(x, random(150, 450));
    vel = new PVector(-2, 0);
    gapH = 230;
    gapW = 120;
    h = 600;
    hue = random(50, 100);
    check = false;
  }

  void show() {
    fill(hue);
    rect(loc.x - gapW/2, 0, gapW, loc.y - gapH/2);
    rect(loc.x - gapW/2, loc.y + gapH/2, gapW, h);
  }

  void move() {
    loc.add(vel);
  }

  void reset() {
    if (loc.x <= -120) {
      loc.x = width + 145;
      loc.y = random(200, 600);
      check = false;
    }
  }
  
  void scoreKeep() {
    if (!check){
      if (flappy.loc.x >= loc.x) {
        scoreSound.play();
        score += 1;
        highScore = max(score, highScore);
        scoreSound.rewind();
        check = true;
      }
    }
  }

  boolean collided() {
    return (rectRect(flappy.loc.x, flappy.loc.y, flappy.s, flappy.s, 
                     loc.x - gapW/2, 0, gapW, loc.y - gapH/2) ||
            rectRect(flappy.loc.x, flappy.loc.y, flappy.s, flappy.s, 
                     loc.x - gapW/2, loc.y + gapH/2, gapW, h));
  }
}