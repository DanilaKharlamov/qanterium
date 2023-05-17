package com.dkharlamov.qanterium.input.member.util;

import com.dkharlamov.qanterium.input.member.exception.MemberException;
import com.dkharlamov.qanterium.input.member.repository.MemberRepository;
import com.dkharlamov.qanterium.input.member.repository.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.dkharlamov.qanterium.common.constant.ExceptionConstants.notFound;

@Component
@RequiredArgsConstructor
public class MemberProvider {
    private final MemberRepository memberRepository;

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(String.format(notFound, "member", memberId)));
    }

    public Set<Member> getMembersByExpenseCardId(Long expenseCardId) {
        return new HashSet<>(memberRepository.findByExpenseCardId(expenseCardId));
    }

    public Set<Member> getMembersById(List<Long> membersId) {
        return new HashSet<>(memberRepository.findAllById(membersId));
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public void deleteById(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
