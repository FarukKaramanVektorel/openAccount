package com.openaccount.open_account.data.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionType {
	CREDIT("Bakiye Ekle", 1), DEBIT("Bakiye Düş", 2);

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

	@JsonValue
	public String toValue() {
		return this.name();
	}
}
