import oscSwitch.*;
import processing.serial.*;
import cc.arduino.*;
import netP5.*;
import oscP5.*;

OscSwitch com_4;
OscP5 oscP5;
int nDigWPin;
int nAnWPin;
int nAnRPin;
int nDigRPin;


 
color off = color(4, 79, 111);
color on = color(84, 145, 158);
 
void setup() {
     size(470, 280,P3D);
     background(off);
     
     fill(30);
     noStroke();
     rect(30, (height/2)-26, 150, 44);
     fill(205);
     text("Sending port:  12000", 33, (height/2)-14);
     text("Listening port: 12001", 33, height/2);
     text("Addres Pattern: /test", 33, (height/2)+14);
     stroke(0);
    
     fill(255);
     text("D i g i t a l   P i n s", 180, 85);
     text("A n a l o g   P i n s", 300, 265);
     com_4 = new OscSwitch(this, "127.0.0.1", 12000, 12001, "/test");
     
     //Draw write digitals pins    
     for (int i = 0; i <= 13; i++) {       
       fill(255);
       text(i, (420 - i * 30)+4, 25);
       rect(420 - i * 30, 30, 20, 20);
       fill(0, 0, 0);
       text("W", (420 - i * 30)+5, 45);
     }
     
     //Draw read digitals pins  
     for (int i = 0; i <= 13; i++) {  
       fill(255);
       rect(420 - i * 30, 50, 20, 20);
       fill(0);
       text("R", (420 - i * 30)+5, 65);
     }  
     
     //Draw write analog pins  
     for (int i = 0; i <= 5; i++) {   
       fill(255);
       rect(420 - i * 30, 210, 20, 20);
       text(5-i, (420 - i * 30)+5, 205);
       fill(0);
       text("W", (420 - i * 30)+5, 225);
     }
     
     //Draw read analog pins  
     for (int i = 0; i <= 5; i++) {   
       fill(255);
       rect(420 - i * 30, 230, 20, 20);
       fill(0);
       text("R", (420 - i * 30)+5, 245);
     }
    
 }
 
void draw(){
     stroke(on);
     
//Write to Arduino analog pins
  com_4.writeToArd(nAnWPin, 0);
//Write to Arduino digital pins
  com_4.writeToArd(nDigWPin, 0);

      
//Read from Arduino analog pins    
  com_4.readFromArd(nAnRPin);
//Read from Arduino digital pins    
  com_4.readFromArd(nDigRPin);

 }

 void mousePressed(){
   
   //Set Write digitals pins
   for (int i = 0; i <= 13; i++) {       
     if (mouseX >= 420-i*30 && mouseX <= (420-i*30) +20){
       if (mouseY >= 30 && mouseY <= 50){
         boolean b = com_4.isInActive(i+6);
         String s = str(b);
         switch(s){
           case "false":
             com_4.setActiveInPins(i+6);
             nAnWPin=i+6;
             println("Wd: "+i);
             fill(255, 0, 0);
             text("W", (420 - i * 30)+5, 45);
             break;
           case "true":
             com_4.removeInPin(i+6);
             println("rmv Wd: "+i);
             fill(50);
             text("W", (420 - i * 30)+5, 45);
             break;
           }
         }
       }
     }
     
     
     //Set Read digitals pins       
     for (int i = 0; i <= 13; i++) {  
       if (mouseX >= 420-i*30 && mouseX <= (420-i*30) +20){
         if (mouseY >= 50 && mouseY <= 70){
           boolean b = com_4.isOutActive(i+6);
           String s = str(b);
           switch(s){
           case "false":
             com_4.setActiveOutPins(i+6);
             nDigRPin=i+6;
             println("Rd: "+i);
             fill(255, 0, 0);
             text("R", (420 - i * 30)+5, 65);
             break;
           case "true":
             com_4.removeOutPin(i+6);
             println("rmv Rd: "+i);
             fill(0, 0, 0);
             text("R", (420 - i * 30)+5, 65);
             break;
           }
         }
       }
     }  
     
     //Set Write analog pins       
     for (int i = 0; i <= 5; i++) {   
       if (mouseX >= 420-i*30 && mouseX <= (420-i*30) +20){
         if (mouseY >= 210 && mouseY <= 230){
           boolean b = com_4.isInActive(5-i);
           String s = str(b);
           switch(s){
           case "false":
             com_4.setActiveInPins(5-i);
             nAnWPin=5-i;
             println("Wa: "+(5-i));
             fill(255, 0, 0);
             text("W", (420 - i * 30)+5, 225);
             break;
           case "true":
             com_4.removeInPin(5-i);
             println("rmv Wa: "+(5-i));
             fill(0, 0, 0);
             text("W", (420 - i * 30)+5, 225);
             break;
           }
         }
       }
     }
     
     //Set Read analog pins        
     for (int i = 0; i <= 5; i++) {   
       if (mouseX >= 420-i*30 && mouseX <= (420-i*30) +20){
         if (mouseY >= 230 && mouseY <= 250){
           boolean b = com_4.isOutActive(5-i);
           String s = str(b);
           switch(s){
           case "false":
             com_4.setActiveOutPins(5-i);
             nAnRPin=5-i;
             println("Ra: "+(5-i));
             fill(255, 0, 0);
             text("R", (420 - i * 30)+5, 245);
             break;
           case "true":
             com_4.removeOutPin(5-i);
             println("rmv Ra: "+(5-i));
             fill(0, 0, 0);
             text("R", (420 - i * 30)+5, 245);
             break;
           }
         }
       }
     }

 }