package com.dkharlamov.qanterium.input.member.util;

import com.dkharlamov.qanterium.input.member.dto.MemberRequest;
import com.dkharlamov.qanterium.input.member.repository.Member;
import org.springframework.util.CollectionUtils;

import java.util.Optional;
import java.util.Set;

public class MemberValidator {

    private MemberValidator() {
        throw new AssertionError("Utility class");
    }

    public static void validateMemberRequest(MemberRequest memberRequest) {
        if (memberRequest == null || CollectionUtils.isEmpty(memberRequest.getNames())) {
            throw new IllegalArgumentException("MemberRequest must not be null and contain at least one name");
        }
    }

    public static void validateMembers(Set<Member> cardMembers, Member ownerMember, Set<Member> debtMembers) {
        boolean isOwnerBelongToCard = isMemberBelongToCard(cardMembers, ownerMember);
        boolean isDebtBelongToCard = debtMembers.stream().allMatch(member -> isMemberBelongToCard(cardMembers, member));

        if (!isOwnerBelongToCard || !isDebtBelongToCard) {
            throw new IllegalStateException("Owner or debts does not belong to current expense card");
        }
        if (debtMembers.stream().anyMatch(member -> member.getId().equals(ownerMember.getId()))) {
            throw new IllegalStateException("Owner cannot be a debtor for this transaction");
        }
    }

    private static boolean isMemberBelongToCard(Set<Member> cardMembers, Member member) {
        return Optional.ofNullable(member)
                .map(Member::getExpenseCard)
                .map(card -> cardMembers.contains(member))
                .orElse(false);
    }
}
