package com.openaccount.open_account.data.dto.movement;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementUpdateDto extends MovementCreateDto {
    @NotNull(message = "Güncellenecek işlemin ID'si boş olamaz")
    private Long id;
}