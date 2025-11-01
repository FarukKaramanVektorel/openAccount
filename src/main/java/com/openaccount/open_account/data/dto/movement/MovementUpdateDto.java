package com.openaccount.open_account.data.dto.movement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementUpdateDto extends MovementCreateDto{
    private Long id;
}
