package com.openaccount.open_account.data.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum TransactionType {
    CREDIT("Bakiye Ekle", 1),
    DEBIT("Bakiye Düş", 2);

    private String value;
    private int key;

    @JsonCreator
    public static TransactionType from(String name) {
        for (TransactionType t : TransactionType.values()) {
            if (t.name().equalsIgnoreCase(name)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tanımsız işlem: " + name);
    }

    @JsonCreator
    public static TransactionType from(Integer id) {
        for (TransactionType t : TransactionType.values()) {
            if (t.key == id) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tanımsız işlem: " + id);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }

    public static TransactionType reverse(TransactionType t) {
        if (t.equals(CREDIT)) {
            return DEBIT;
        }
        return CREDIT;
    }

    public static List<TransactionType> getList(){
        return Arrays.asList(TransactionType.CREDIT,TransactionType.DEBIT);
    }
}
