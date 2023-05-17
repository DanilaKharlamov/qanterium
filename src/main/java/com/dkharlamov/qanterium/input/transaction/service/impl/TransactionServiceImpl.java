package com.dkharlamov.qanterium.input.transaction.service.impl;

import com.dkharlamov.qanterium.exchange.convert.dto.FailedConversionsStore;
import com.dkharlamov.qanterium.exchange.convert.dto.FailedItem;
import com.dkharlamov.qanterium.exchange.ExchangeFacade;
import com.dkharlamov.qanterium.input.transaction.dto.TransactionResponse;
import com.dkharlamov.qanterium.input.transaction.dto.TransactionValueResponse;
import com.dkharlamov.qanterium.input.member.exception.MemberException;
import com.dkharlamov.qanterium.input.transaction.dto.TransactionRequest;
import com.dkharlamov.qanterium.input.member.repository.Member;
import com.dkharlamov.qanterium.input.transaction.repository.entity.Transaction;
import com.dkharlamov.qanterium.input.transaction.repository.entity.TransactionCurrency;
import com.dkharlamov.qanterium.input.transaction.repository.entity.TransactionValue;
import com.dkharlamov.qanterium.input.member.util.MemberProvider;
import com.dkharlamov.qanterium.input.member.util.MemberValidator;
import com.dkharlamov.qanterium.input.transaction.service.TransactionService;
import com.dkharlamov.qanterium.input.transaction.util.TransactionMapper;
import com.dkharlamov.qanterium.input.transaction.util.TransactionProvider;
import com.dkharlamov.qanterium.input.transaction.util.TransactionValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.dkharlamov.qanterium.common.constant.ExceptionConstants.notFound;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final MemberProvider memberProvider;
    private final ExchangeFacade exchangeFacade;
    private final TransactionProvider transactionProvider;
    private final TransactionValueService transactionValueService;
    private final FailedConversionsStore failedConversionsStore;

    public List<TransactionResponse> getTransactions() {
        List<Transaction> transactions = transactionProvider.getAllTransactions();
        return transactions.stream().map(TransactionMapper::transactionEntityToDto).toList();
    }

    public TransactionResponse getTransaction(Long transactionId) {
        Transaction transaction = transactionProvider.getTransactionById(transactionId);
        return TransactionMapper.transactionEntityToDto(transaction);
    }

    public List<TransactionResponse> getTransactionsForMember(Long memberId) {
        Member member = memberProvider.getMember(memberId);
        List<Transaction> transactions = member.getOwner();
        transactions.addAll(member.getDebts());
        return transactions.stream().map(TransactionMapper::transactionEntityToDto).toList();
    }

    public void createTransaction(TransactionRequest request) {
        TransactionValidator.validateTransactionRequest(request);
        Set<Member> cardMembers = memberProvider.getMembersByExpenseCardId(request.getExpenseCardId());
        Member owner = getOwnerMember(cardMembers, request.getOwnerId());
        Set<Member> debts = getDebtMembers(request.getDebtsMemberId(), cardMembers, request.getOwnerId());
        MemberValidator.validateMembers(cardMembers, owner, debts);
        Transaction transaction = buildTransaction(request, owner, debts);
        transactionProvider.save(transaction);

        exchangeFacade.convertValueToOtherCurrencies(request.getValue(), request.getCurrency(), transaction.getId(), true);
    }

    private Member getOwnerMember(Set<Member> cardMembers, Long ownerId) {
        return cardMembers.stream()
                .filter(it -> Objects.equals(it.getId(), ownerId))
                .findFirst()
                .orElseThrow(() -> new MemberException(String.format(notFound, "ownerId", ownerId)));
    }

    private Set<Member> getDebtMembers(List<Long> requestDebts, Set<Member> cardMembers, Long ownerId) {
        if (!CollectionUtils.isEmpty(requestDebts)) {
            return filterMembers(cardMembers, member -> requestDebts.contains(member.getId()));
        } else {
            return filterMembers(cardMembers, member -> !member.getId().equals(ownerId));
        }
    }

    private Set<Member> filterMembers(Set<Member> members, Predicate<Member> predicate) {
        return members.stream()
                .filter(predicate)
                .collect(Collectors.toSet());
    }

    private Transaction buildTransaction(TransactionRequest request, Member ownerMember, Set<Member> debtMembers) {
        TransactionCurrency currency = transactionProvider.getTransactionCurrencyByCode(request.getCurrency());
        return new Transaction.Builder()
                .name(request.getName())
                .description(request.getDescription())
                .owner(ownerMember)
                .debts(debtMembers)
                .transactionValue(List.of(
                        transactionValueService.buildValue(
                                BigDecimal.valueOf(request.getValue()),
                                currency,
                                true,
                                null
                        ))
                )
                .build();
    }

    public void updateTransaction(TransactionRequest request, Long transactionId) {
        Transaction transaction = transactionProvider.getTransactionById(transactionId);

        Transaction updatedTransaction = new Transaction.Builder()
                .id(transaction.getId())
                .name(!Objects.isNull(request.getName()) ? request.getName() : transaction.getName())
                .description(!Objects.isNull(request.getDescription()) ? request.getDescription() : transaction.getDescription())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .owner(transaction.getOwner())
                .debts(transaction.getDebts())
                .transactionValue(transaction.getTransactionValue())
                .build();

        if (!CollectionUtils.isEmpty(request.getDebtsMemberId())) {
            Set<Member> members = memberProvider.getMembersById(request.getDebtsMemberId());
            updatedTransaction.setDebts(members);
        }

        if (request.getOwnerId() != null) {
            Member owner = memberProvider.getMember(request.getOwnerId());
            updatedTransaction.getDebts().remove(owner);
            updatedTransaction.setOwner(owner);
        }

        if (request.getValue() != null && request.getCurrency() == null) {
            transaction.setTransactionValue(
                    transactionValueService.reCalculateTransactionValues(
                            transaction.getTransactionValue(),
                            request.getValue()
                    )
            );
        }

        transactionProvider.save(updatedTransaction);
    }

    public void deleteTransaction(Long transactionId) {
        transactionProvider.deleteTransaction(transactionId);
    }

    public List<TransactionResponse> getExpenseTransactionsForMember(Long memberId) {
        Member member = memberProvider.getMember(memberId);
        return member.getOwner().stream().map(TransactionMapper::transactionEntityToDto).toList();
    }

    public List<TransactionResponse> getDebtTransactionsForMember(Long memberId) {
        Member member = memberProvider.getMember(memberId);
        return member.getDebts().stream().map(TransactionMapper::transactionEntityToDto).toList();
    }

    public List<TransactionValueResponse> getTransactionValues(Long transactionId) {
        List<TransactionValue> transactionValueList = transactionProvider.getTransactionValuesByTransactionId(transactionId);
        if (transactionValueList.size() == 1) {
            addToFailedConversionsStore(transactionValueList.get(0));
        }
        return transactionValueList.stream().map(TransactionMapper::valueEntityToDto).toList();
    }

    private void addToFailedConversionsStore(TransactionValue transactionValue) {
        failedConversionsStore.getValues().putIfAbsent(
                transactionValue.getTransaction().getId(),
                FailedItem.builder()
                        .baseValue(transactionValue.getValue().doubleValue())
                        .baseCurrency(transactionValue.getTransactionCurrency().getCode())
                        .transactionId(transactionValue.getTransaction().getId())
                        .failCounter(0)
                        .build()

        );
    }

    public void removeDebtFromTransaction(Long transactionId, Long memberId) {
        Transaction transaction = transactionProvider.getTransactionById(transactionId);
        Member member = memberProvider.getMember(memberId);
        transaction.getDebts().remove(member);
        transactionProvider.save(transaction);
    }
}
