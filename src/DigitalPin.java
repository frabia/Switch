public class DigitalPin extends Pin{
    
    public DigitalPin(int num){
        super("D", num);
    }
    
    public int read(){
        return firm.digitalRead(pin);
    }

}