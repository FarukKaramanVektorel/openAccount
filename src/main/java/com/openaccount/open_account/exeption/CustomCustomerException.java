package com.openaccount.open_account.exeption;

import java.math.BigDecimal;

public class CustomCustomerException extends RuntimeException{
    public CustomCustomerException(String message){
        super(message);
    }
    public CustomCustomerException(Long id){
        super("Aranılan " + id + " ID'li müşteri bulunamadı...");
    }
    public CustomCustomerException(BigDecimal balance){
        super("Müşteri silinmeden önce bakiyenin kapanması gerekmektedir, müşteri bakiyesi: "+balance+" TL");
    }

}
