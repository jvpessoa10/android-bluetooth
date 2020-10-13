#include "SoftwareSerial.h"

SoftwareSerial bluetooth(10, 11);
void setup() {
  Serial.begin(9600);
  Serial.println("Enter AT commands:");
  bluetooth.begin(38400);
}

void loop() {
   // Keep reading from HC-05 and send to Arduino 
  // Serial Monitor
  if (bluetooth.available())
    Serial.write(bluetooth.read());

  // Keep reading from Arduino Serial Monitor 
  //  and send to HC-05
  if (Serial.available())
    bluetooth.write(Serial.read());

}
