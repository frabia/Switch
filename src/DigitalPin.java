public class DigitalPin extends Pin{
    
    public DigitalPin(int num){
        super("A", num);
    }
    
    public int read(){
        return firm.digitalRead(pin);
    }

}