package oscSwitch;

import cc.arduino.Arduino;

public class DigitalPin extends Pin {
    
    public DigitalPin(int num){
        super("D", num);
    }
    
    public int read(){
        arduino.pinMode(pin, Arduino.INPUT);
        return arduino.digitalRead(pin);
        //return firm.digitalRead(pin);
    }

}