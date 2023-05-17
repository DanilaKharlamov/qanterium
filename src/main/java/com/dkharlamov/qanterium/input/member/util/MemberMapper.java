package com.dkharlamov.qanterium.input.member.util;

import com.dkharlamov.qanterium.input.member.dto.MemberResponse;
import com.dkharlamov.qanterium.input.expensecard.repository.ExpenseCard;
import com.dkharlamov.qanterium.input.member.repository.Member;
import com.dkharlamov.qanterium.input.member.repository.MemberRole;
import com.dkharlamov.qanterium.security.user.User;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class MemberMapper {
    private MemberMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static MemberResponse entityToDto(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .build();
    }

    public static List<Member> dtoToMembers(List<String> memberNames, ExpenseCard expenseCard) {
        return memberNames.stream()
                .filter(it -> !StringUtils.isEmpty(it))
                .map(memberName -> createMember(memberName, expenseCard, null))
                .collect(Collectors.toList());
    }

    public static List<Member> dtoToMembers(ExpenseCard expenseCard, List<User> users) {
        return users.stream()
                .filter(Objects::nonNull)
                .map(user -> createMember(user.getUsername(), expenseCard, user))
                .collect(Collectors.toList());
    }


    private static Member createMember(String memberName, ExpenseCard expenseCard, User user) {
        return Member.builder()
                .name(memberName)
                .role(MemberRole.MEMBER)
                .expenseCard(expenseCard)
                .user(user)
                .build();
    }
}
