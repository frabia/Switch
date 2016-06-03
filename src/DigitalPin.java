package oscSwitch;

import cc.arduino.Arduino;

public class DigitalPin extends Pin {
    
    public DigitalPin(Arduino arduino,int num){
        super(arduino,"D", num);
    }
    
    public int read(){
        return arduino.digitalRead(pin);
    }

}