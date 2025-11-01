package com.openaccount.open_account.exeption;

public class CustomMovementException extends RuntimeException{
    public CustomMovementException(String message){
        super(message);
    }
    public CustomMovementException(Long id){
        super("Aranılan " + id + " ID'li hareket kaydı bulunamadı...");
    }
}
