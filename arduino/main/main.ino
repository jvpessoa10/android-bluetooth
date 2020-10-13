#include "SoftwareSerial.h"

#define LED_PIN 8

SoftwareSerial bluetooth(10, 11);

void setup() {
  pinMode(LED_PIN, OUTPUT);
  
  Serial.begin(38400);
  bluetooth.begin(38400);
}

int ledState = 0;

void loop() {
   // Keep reading from HC-05 and send to Arduino 
  // Serial Monitor
  if (bluetooth.available()) {
    char data = bluetooth.read();

    if (data == '1')  {
      ledState = 1;
    } else if (data == '0') {
      ledState = 0;
    }
  }
   
  if (ledState) {
    digitalWrite(LED_PIN, HIGH);
  } else {
    digitalWrite(LED_PIN, LOW);
  }
}
