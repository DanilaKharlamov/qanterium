package com.dkharlamov.qanterium.input.counter.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MemberDebtsInfoDto {

    private String debtorName;
    private List<DebtInfoDto> debtsInfo;

}
