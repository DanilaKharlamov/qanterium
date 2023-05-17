package com.dkharlamov.qanterium.input.member.service.impl;

import com.dkharlamov.qanterium.input.member.dto.MemberResponse;
import com.dkharlamov.qanterium.input.member.service.MemberService;
import com.dkharlamov.qanterium.input.member.dto.MemberRequest;
import com.dkharlamov.qanterium.input.member.util.MemberProvider;
import com.dkharlamov.qanterium.input.member.repository.Member;
import com.dkharlamov.qanterium.input.member.util.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberProvider memberProvider;

    public List<MemberResponse> getMembersByExpenseCardId(Long expenseCardId) {
        Set<Member> members = memberProvider.getMembersByExpenseCardId(expenseCardId);
        return members.stream().map(MemberMapper::entityToDto).toList();
    }

    public void updateMember(Long memberId, MemberRequest memberRequest) {
        Member member = memberProvider.getMember(memberId);
        member.setName(memberRequest.getNames().get(0));
        memberProvider.save(member);
    }

    public void deleteMember(Long memberId) {
        memberProvider.deleteById(memberId);
    }
}
