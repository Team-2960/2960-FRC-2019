#include <Adafruit_NeoPixel.h>
#ifdef __AVR__
  #include <avr/power.h>
#endif

#define PIN 6

const int dig1 = 1;
const int dig2 = 2;

Adafruit_NeoPixel strip = Adafruit_NeoPixel(74, PIN, NEO_GRB + NEO_KHZ800);

void setup() {
  pinMode(dig1, INPUT);
  pinMode(dig2, INPUT);


  
  strip.begin();
  strip.setBrightness(50);
  strip.show(); // Initialize all pixels to 'off'
}

void loop() {
  int input1 = digitalRead(dig1);
  int input2 = digitalRead(dig2);
  
  if(input1 == 0 && input2 == 0){ //off
    colorWipe(strip.Color(0, 0, 0), 0);// black
  }

  if(input1 == 1 && input2 == 0){ //red
    colorWipe(strip.Color(255, 0, 0), 0);// Red
    delay(1000);
    theaterChase(strip.Color(127, 0, 0), 50); // Red
  }

  if(input1 == 0 && input2 == 1){ //blue
    colorWipe(strip.Color(0, 0, 255), 0);// blue
    delay(1000);
    theaterChase(strip.Color(0, 0, 127), 50); // Blue
  }

  if(input1 == 1 && input2 == 1){ //climb rainbow
      strip.setBrightness(100);
      rainbowCycle(20);
  }
}

// Fill the dots one after the other with a color
void colorWipe(uint32_t c, uint8_t wait) {
  for(uint16_t i=0; i<strip.numPixels(); i++) {
    strip.setPixelColor(i, c);
    delay(wait);
  }
  strip.show();
}

// Slightly different, this makes the rainbow equally distributed throughout
void rainbowCycle(uint8_t wait) {
  uint16_t i, j;

  for(j=0; j<256*5; j++) { // 5 cycles of all colors on wheel
    for(i=0; i< strip.numPixels(); i++) {
      strip.setPixelColor(i, Wheel(((i * 256 / strip.numPixels()) + j) & 255));
    }
    strip.show();
    delay(wait);
  }
}

//Theatre-style crawling lights.
void theaterChase(uint32_t c, uint8_t wait) {
  for (int j=0; j<10; j++) {  //do 10 cycles of chasing
    for (int q=0; q < 3; q++) {
      for (uint16_t i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, c);    //turn every third pixel on
      }
      strip.show();

      delay(wait);

      for (uint16_t i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, 0);        //turn every third pixel off
      }
    }
  }
}



// Input a value 0 to 255 to get a color value.
// The colours are a transition r - g - b - back to r.
uint32_t Wheel(byte WheelPos) {
  WheelPos = 255 - WheelPos;
  if(WheelPos < 85) {
    return strip.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  }
  if(WheelPos < 170) {
    WheelPos -= 85;
    return strip.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
  WheelPos -= 170;
  return strip.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
}
