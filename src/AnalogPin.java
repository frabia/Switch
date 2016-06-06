package oscSwitch;

import cc.arduino.Arduino;

public class AnalogPin extends Pin {
    
    public AnalogPin(Arduino arduino,int rNum, int wNum){
        super(arduino, "A", rNum, wNum);
    }
    
    public int read(){
        return arduino.analogRead(readPin);
    }


    public int write(int val){
        arduino.analogWrite(writePin, val);
        return val;
    }


}