package oscSwitch;


import cc.arduino.Arduino;

public class AnalogPin extends Pin {
    
    public AnalogPin(int num){
        super("A", num);
    }
    
    public int read(){
        arduino.pinMode(pin, Arduino.INPUT);
        return arduino.analogRead(pin);
        //return firm.digitalRead(pin);

    }

}