package com.dkharlamov.qanterium.input.counter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DebtInfoDto {

    private String owner;
    private List<DebtValueDto> debtValuesDto;

}
