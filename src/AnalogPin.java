public class AnalogPin extends Pin{
    
    public AnalogPin(int num){
        super("A", num);
    }
    
    public int read(){
        return firm.analogRead(pin);
    }

}