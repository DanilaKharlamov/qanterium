package com.dkharlamov.qanterium.input.counter;

import com.dkharlamov.qanterium.input.counter.dto.DebtInfoDto;
import com.dkharlamov.qanterium.input.counter.dto.DebtValueDto;
import com.dkharlamov.qanterium.input.counter.dto.MemberDebtsInfoDto;
import com.dkharlamov.qanterium.input.member.util.MemberProvider;
import com.dkharlamov.qanterium.input.member.repository.Member;
import com.dkharlamov.qanterium.input.transaction.repository.entity.Transaction;
import com.dkharlamov.qanterium.input.transaction.repository.entity.TransactionCurrency;
import com.dkharlamov.qanterium.input.transaction.util.TransactionProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CounterService {

    private final MemberProvider memberProvider;
    private final TransactionProvider transactionProvider;

    public MemberDebtsInfoDto countDebtsByMemberId(Long memberId) {
        Member member = memberProvider.getMember(memberId);
        List<Member> members = memberProvider.getMembersByExpenseCardId(member.getExpenseCard().getId())
                .stream()
                .filter(it -> !it.getId().equals(memberId))
                .toList();
        List<Transaction> debtTransactions = member.getDebts();

        MemberDebtsInfoDto memberDebtsInfoDto = new MemberDebtsInfoDto();
        memberDebtsInfoDto.setDebtorName(member.getName());

        List<String> currencies = transactionProvider.getCurrencies().stream().map(TransactionCurrency::getCode).toList();
        List<DebtInfoDto> debtInfoDto = members.stream()
                .map(memberItem -> getDebtInfo(debtTransactions, memberItem, currencies))
                .toList();
        memberDebtsInfoDto.setDebtsInfo(debtInfoDto);
        return memberDebtsInfoDto;
    }

    private DebtInfoDto getDebtInfo(List<Transaction> debtTransactions, Member memberItem, List<String> currencies) {
        List<Transaction> transactionDebts = debtTransactions.stream()
                .filter(it -> it.getOwner().getId().equals(memberItem.getId()))
                .toList();

        DebtInfoDto debtInfoDto = new DebtInfoDto();
        debtInfoDto.setOwner(memberItem.getName());

        debtInfoDto.setDebtValuesDto(
                transactionDebts.isEmpty()
                        ? getCurrenciesWithZeroValues(currencies)
                        : getDebtValues(currencies, transactionDebts)
        );
        return debtInfoDto;
    }

    private List<DebtValueDto> getCurrenciesWithZeroValues(List<String> currencies) {
        return currencies.stream()
                .map(currency -> new DebtValueDto(currency, BigDecimal.ZERO))
                .collect(Collectors.toList());
    }

    private List<DebtValueDto> getDebtValues(List<String> currencies, List<Transaction> transactionDebts) {
        Map<String, BigDecimal> totalDebtAmountByCurrency = currencies.stream()
                .collect(Collectors.toMap(currency -> currency, currency -> BigDecimal.ZERO));

        transactionDebts.stream()
                .flatMap(transaction -> transaction.getTransactionValue().stream())
                .forEach(transactionValue -> {
                    String currencyCode = transactionValue.getTransactionCurrency().getCode();
                    BigDecimal debtAmount = calculateDebt(transactionValue.getValue(), transactionValue.getTransaction().getDebts().size() + 1);
                    totalDebtAmountByCurrency.put(currencyCode, totalDebtAmountByCurrency.get(currencyCode).add(debtAmount));
                });

        return totalDebtAmountByCurrency.entrySet().stream()
                .map(entry -> new DebtValueDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private BigDecimal calculateDebt(BigDecimal value, int size) {
        return value.divide(BigDecimal.valueOf(size), 4, RoundingMode.HALF_UP);
    }
}