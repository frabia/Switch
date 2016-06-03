package oscSwitch;

import cc.arduino.Arduino;

public class AnalogPin extends Pin {
    
    public AnalogPin(Arduino arduino,int num){
        super(arduino, "A", num);
    }
    
    public int read(){
        return arduino.analogRead(pin);
    }

}