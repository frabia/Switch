package oscSwitch;

public class DigitalPin extends Pin {
    
    public DigitalPin(int num){
        super("D", num);
    }
    
    public int read(){
        return arduino.digitalRead(pin);
        //return firm.digitalRead(pin);
    }

}