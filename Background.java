class Background {
  
  float posx, vx;
  
  Background() {
    posx = random(0,-760);
    vx = 0.05;
  }
  
  void show() {
    image(background, posx, 0, 1300, 800);
  }
  
}