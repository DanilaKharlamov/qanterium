package com.dkharlamov.qanterium.input.member.service;

import com.dkharlamov.qanterium.input.member.dto.MemberResponse;
import com.dkharlamov.qanterium.input.member.dto.MemberRequest;

import java.util.List;

public interface MemberService {

    List<MemberResponse> getMembersByExpenseCardId(Long expenseCardId);

    void updateMember(Long memberId, MemberRequest memberRequest);

    void deleteMember(Long memberId);
}
