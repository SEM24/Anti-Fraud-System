package antifraud.service;

import antifraud.model.enums.TransactionAction;
import org.springframework.security.access.prepost.PreAuthorize;

public interface TransactionService {
//    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    TransactionAction checkTransaction(Long amount);
}
