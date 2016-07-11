package oscSwitch;

import cc.arduino.Arduino;

public class AnalogPin extends Pin {
    
    public AnalogPin(Arduino arduino, int nPin){
        super(arduino, "A", nPin);
    }
    
    public int read(int nPin){
        return arduino.analogRead(num);
    }


    public int write(int nPin, int val){
        arduino.analogWrite(nPin, val);
        return val;
    }


}