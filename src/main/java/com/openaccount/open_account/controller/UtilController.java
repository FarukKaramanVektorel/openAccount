package com.openaccount.open_account.controller;

import com.openaccount.open_account.data.enums.TransactionType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/utils")
public class UtilController {
    @GetMapping("/transactionType")
    public List<Map<String, Object>> transactionTypes() {
        return Arrays.stream(TransactionType.values())
                .map(t -> Map.<String, Object>of(
                        "key", t.getKey(),
                        "value", t.getValue()
                )).collect(Collectors.toList());
    }
}
