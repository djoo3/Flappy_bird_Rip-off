import ddf.minim.*;

Minim minim;
AudioPlayer playSong;
AudioPlayer startSound;
AudioPlayer flapSound;
AudioPlayer scoreSound;
AudioPlayer endSound;
AudioPlayer waterSound;
AudioInput input;

Bird flappy;
Background backdrop;
Obstacle[] obs;
int o = 3;

PImage background;
PImage bird1;
PImage bird2;
PImage bird3;
PImage water;
PFont fontMain;

int mode;
int score = 0;
int highScore = 0;


final int INTRO = 0;
final int PLAYING = 1;
final int PAUSED = 2;
final int END = 3;


void setup() {
  flappy = new Bird();
  backdrop = new Background();
  obs = new Obstacle[o];
  background = loadImage("cave background.png");
  bird1 = loadImage("birdA.png");
  bird2 = loadImage("birdB.png");
  bird3 = loadImage("birdC.png");
  water = loadImage("bird water.png");
  fontMain = loadFont("AvenirLTStd-Roman-48.vlw");
  minim = new Minim(this);
  playSong = minim.loadFile("theloop.mp3");
  startSound = minim.loadFile("startSound.mp3");
  flapSound = minim.loadFile("flapSound.mp3");
  scoreSound = minim.loadFile("pipeSound.mp3");
  endSound = minim.loadFile("endSound.mp3");
  waterSound = minim.loadFile("splashSound.mp3");
  
  input = minim.getLineIn();
  float pipeLoc = width;
  for (int i = 0; i < o; i++) {
    obs[i] = new Obstacle(pipeLoc);
    pipeLoc += width/2;
  }
  playSong.loop();
  mode = INTRO;
  size(540, 800, FX2D);
}

void draw() {
  if (mode == INTRO) {
    backdrop.show();
    noStroke();
    image(water, 0, 700, width, 150);
    fill(0,100);
    rect(0,0,width,height);
    fill(255);
    textFont(fontMain);
    textAlign(CENTER, CENTER);
    textSize(75);
    text("YET ANOTHER", width/2, height/3 - 100);
    text("FLAPPY BIRD", width/2, height/3);
    text("RIP-OFF", width/2, height/3 + 100);
    textSize(50);
    text("CLICK TO BEGIN.", width/2, height/2 + 100);
    textSize(20);
    text("click mouse to flap bird", width/2, 2*height/3 + 30);
    text("p to pause", width/2, 2*height/3 + 50);
    if (mousePressed) {
      mode = PLAYING;
      playSong.rewind();
      startSound.play();
    }
  }
  if (mode == PLAYING) {
    backdrop.show();
    for (int i = 0; i < o; i++) {
      obs[i].show();
      obs[i].move();
      obs[i].reset();
      obs[i].scoreKeep();
      if (obs[i].collided()) {
        mode = END;
        endSound.play();
        endSound.rewind();
        playSong.pause();
        print("!");
      }
      if (flappy.loc.y >= 665) {
        mode = END;
        playSong.pause();
        waterSound.play();
        waterSound.rewind();
      }
    }
    flappy.show();
    flappy.move();
    noStroke();
    image(water, 0, 700, width, 150);
    fill(255);
    textFont(fontMain);
    textSize(80);
    text(score, width/2, height/3);
    if (keyPressed) {
      if (key == 'p' || key == 'P') {
        mode = PAUSED;
        playSong.pause();
        startSound.play();
        startSound.rewind();
      }
    }
  }
  if (mode == PAUSED) {
    backdrop.show();
    flappy.show();
    for (int i = 0; i < o; i++) {
      obs[i].show();
    }
    image(water, 0, 700, width, 150);
    fill(0, 100);
    rect(0,0,width,height);
    fill(255);
    textFont(fontMain);
    textAlign(CENTER, CENTER);
    textSize(130);
    text("PAUSED", width/2, height/2);
    textSize(50);
    text("CLICK TO CONTINUE", width/2, height/2 + 130);
    if (mousePressed) {
      mode = PLAYING;
      playSong.play();
      startSound.play();
      startSound.rewind();
    }
  }
  if (mode == END) {
    backdrop.show();
    for (int i = 0; i < o; i++) obs[i].show();
    flappy.show();
    flappy.move();
    noStroke();
    image(water, 0, 700, width, 150);
    fill(0, 100);
    rect(0,0,width,height);
    fill(255);
    textFont(fontMain);
    textAlign(CENTER, CENTER);
    textSize(50);
    text("PRESS R TO RESTART", width/2, height/2);
    text("HIGHSCORE", width/2, 2*height/3 + 20);
    textSize(80);
    text(highScore, width/2, 2*height/3 + 90);
    displayScore();
   if (keyPressed) {
     if (key == 'r' || key == 'R') {
       flappy.loc = new PVector(100, height/2);
       flappy.vel = new PVector(0,0);
       resetObs();
       score = 0;
       }
       mode = PLAYING;
       playSong.rewind();
       playSong.play();
       playSong.loop();
     }
    }
  }
  
  void resetObs() {
    float pipeLoc = width;
      for (int i = 0; i < o; i++) {
        obs[i].loc = new PVector(width, random(150, 450));
        obs[i] = new Obstacle(pipeLoc);
        pipeLoc += width/2;
        obs[i].vel = new PVector(-2, 0);
      }
  }

  void displayScore() {
    fill(255);
    textFont(fontMain);
    textAlign(CENTER, CENTER);
    textSize(80);
    text(score, width/2, height/3);
    textSize(50);
    text("YOUR SCORE", width/2, height/3-70);
  }
  
  void mousePressed() {
    if (mode == PLAYING) {
      flappy.flap();
    }
  }