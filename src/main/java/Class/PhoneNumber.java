/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

/**
 *
 * @author Reyner
 */
public class PhoneNumber {
    private String type;
    private String number;
    private int numberUserId;
    private int numberId;


    public PhoneNumber(String type, String number, int numberUserId, int numberId) {
        this.type = type;
        this.number = number;
        this.numberUserId = numberUserId;
        this.numberId = numberId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumberId() {
        return numberId;
    }

    public void setNumberId(int numberId) {
        this.numberId = numberId;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setNumberUserId(int numberUserId) {
        this.numberUserId = numberUserId;
    }

    public String getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public int getNumberUserId() {
        return numberUserId;
    }
    
    
}
