package oscSwitch;

import cc.arduino.Arduino;

public class DigitalPin extends Pin {


    public DigitalPin(Arduino arduino, int nPin){
        super(arduino,"D", nPin);
    }


    public int read(int nPin){

        arduino.pinMode(num, Arduino.INPUT);
        return arduino.digitalRead(num);
    }

    public int write(int nPin, int val){
        arduino.pinMode(num, Arduino.OUTPUT);
        arduino.digitalWrite(nPin,val);
        return val;
    }


}