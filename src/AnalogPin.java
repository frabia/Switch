package oscSwitch;


import cc.arduino.Arduino;

import java.applet.Applet;

public class AnalogPin extends Pin {
    
    public AnalogPin(Arduino arduino,int num){
        super(arduino, "A", num);
    }
    
    public int read(){
        return arduino.analogRead(pin);
        //return firm.digitalRead(pin);

    }

}