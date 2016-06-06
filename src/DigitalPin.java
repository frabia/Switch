package oscSwitch;

import cc.arduino.Arduino;

public class DigitalPin extends Pin {
    
    public DigitalPin(Arduino arduino, int rNum, int wNum){
        super(arduino,"D", rNum, wNum);
    }
    
    public int read(){
        arduino.pinMode(readPin, Arduino.INPUT);
        return arduino.digitalRead(readPin);
    }

    public int write(int val){
        arduino.pinMode(writePin, Arduino.OUTPUT);
        arduino.digitalWrite(writePin,val);
        return val;
    }


}