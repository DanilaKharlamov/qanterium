package com.dkharlamov.qanterium.input.expensecard.service.impl;

import com.dkharlamov.qanterium.input.expensecard.dto.ExpenseCardResponse;
import com.dkharlamov.qanterium.input.expensecard.exception.ExpenseCardException;
import com.dkharlamov.qanterium.input.expensecard.dto.ExpenseCardRequest;
import com.dkharlamov.qanterium.input.member.dto.MemberRequest;
import com.dkharlamov.qanterium.input.expensecard.repository.ExpenseCard;
import com.dkharlamov.qanterium.input.member.repository.Member;
import com.dkharlamov.qanterium.input.expensecard.repository.ExpenseCardRepository;
import com.dkharlamov.qanterium.input.expensecard.service.ExpenseCardService;
import com.dkharlamov.qanterium.input.expensecard.util.ExpenseCardMapper;
import com.dkharlamov.qanterium.security.user.User;
import com.dkharlamov.qanterium.security.user.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dkharlamov.qanterium.common.constant.ExceptionConstants.notFound;
import static com.dkharlamov.qanterium.input.expensecard.util.ExpenseCardValidator.validateExpenseCardRequest;
import static com.dkharlamov.qanterium.input.member.util.MemberMapper.dtoToMembers;
import static com.dkharlamov.qanterium.input.member.util.MemberValidator.validateMemberRequest;

@Slf4j
@Setter
@Getter
@Service
@RequiredArgsConstructor
public class ExpenseCardServiceImpl implements ExpenseCardService {
    private final ExpenseCardRepository expenseCardRepository;
    private final UserRepository userRepository;

    public List<ExpenseCardResponse> getExpenseCards() {
        List<ExpenseCard> expenseCards = expenseCardRepository.findAll();
        return expenseCards.stream().map(ExpenseCardMapper::entityToDto).toList();
    }

    public ExpenseCardResponse getExpenseCard(Long expenseCardId) {
        ExpenseCard expenseCard = expenseCardRepository.findById(expenseCardId)
                .orElseThrow(() -> new ExpenseCardException(String.format(notFound, "expenseCard", expenseCardId)));
        return ExpenseCardMapper.entityToDto(expenseCard);
    }

    public ExpenseCardResponse createExpenseCard(ExpenseCardRequest expenseCardRequest, String username) {
        validateExpenseCardRequest(expenseCardRequest);
        User user = userRepository.findByUsername(username).orElseThrow();

        ExpenseCard expenseCard = ExpenseCard.builder()
                .name(expenseCardRequest.getName())
                .description(expenseCardRequest.getDescription())
                .build();
        expenseCard.setMembers(dtoToMembers(expenseCard, List.of(user)));

        expenseCardRepository.save(expenseCard);
        return ExpenseCardMapper.entityToDto(expenseCard);
    }

    public ExpenseCardResponse updateExpenseCard(Long expenseCardId, ExpenseCardRequest expenseCardRequest) {
        validateExpenseCardRequest(expenseCardRequest);

        var expenseCard = expenseCardRepository.findById(expenseCardId)
                .orElseThrow(() -> new ExpenseCardException(String.format(notFound, "expenseCard", expenseCardId)));
        if (!StringUtils.isEmpty(expenseCardRequest.getName())) {
            expenseCard.setName(expenseCardRequest.getName());
        }
        if (!StringUtils.isEmpty(expenseCardRequest.getDescription())) {
            expenseCard.setDescription(expenseCardRequest.getDescription());
        }
        expenseCardRepository.save(expenseCard);
        return ExpenseCardMapper.entityToDto(expenseCard);
    }

    public void deleteExpenseCard(Long expenseCardId) {
        expenseCardRepository.deleteById(expenseCardId);
    }

    public void addMemberToExpenseCard(Long expenseCardId, MemberRequest memberRequest) {
        validateMemberRequest(memberRequest);

        ExpenseCard expenseCard = expenseCardRepository.findById(expenseCardId)
                .orElseThrow(() -> new ExpenseCardException(String.format(notFound, "expenseCardId", expenseCardId)));

        List<Member> newMembers = dtoToMembers(memberRequest.getNames(), expenseCard);
        expenseCard.getMembers().addAll(newMembers);

        expenseCardRepository.save(expenseCard);
    }
}
