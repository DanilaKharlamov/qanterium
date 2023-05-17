package com.dkharlamov.qanterium.exchange.rate.repository;

import com.dkharlamov.qanterium.input.transaction.repository.entity.TransactionCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExchangePairRepository extends JpaRepository<ExchangePair, Long> {
    //jpql
    @Query(value = """
            SELECT ep
            FROM ExchangePair ep
            WHERE ep.baseCurrency.id = :baseCurrencyId
              AND ep.createdAt = (
                  SELECT MAX(ep2.createdAt)
                  FROM ExchangePair ep2
                  WHERE ep2.baseCurrency.id = ep.baseCurrency.id
                    AND ep2.relatedCurrency.id = ep.relatedCurrency.id
                    AND ep2.createdAt <= CURRENT_DATE
                    AND ep2.createdByUser = false
              )
              AND ep.createdByUser = false""")
    List<ExchangePair> findLastByBaseCurrency(@Param("baseCurrencyId") Integer baseCurrencyId);
    List<ExchangePair> findByBaseCurrencyAndCreatedAt(TransactionCurrency currency, LocalDate date);


    //native
/*    @Query(value = "SELECT ep.*\n" +
            "FROM exchange_pair ep\n" +
            "WHERE ep.base_currency_id = ?\n" +
            "  AND ep.created_at = (\n" +
            "      SELECT MAX(created_at)\n" +
            "      FROM exchange_pair\n" +
            "      WHERE base_currency_id = ep.base_currency_id\n" +
            "        AND related_currency_id = ep.related_currency_id\n" +
            "        AND created_at <= CURRENT_DATE\n" +
            "        AND is_created_by_user = false\n" +
            "  )\n" +
            "  AND ep.is_created_by_user = false;\n", nativeQuery = true)
    List<ExchangePair> findLastByBaseCurrency(@Param("baseCurrencyId") Integer baseCurrencyId);*/

}
