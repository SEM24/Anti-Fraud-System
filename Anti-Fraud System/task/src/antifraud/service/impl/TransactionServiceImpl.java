package antifraud.service.impl;

import antifraud.model.enums.TransactionAction;
import antifraud.model.TransactionProperties;
import antifraud.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    TransactionProperties transactionProps;
    @Override
    public TransactionAction checkTransaction(Long amount) {
        if (amount <= transactionProps.maxAmountForAllowed()) {
            return TransactionAction.ALLOWED;
        }
        if (amount <= transactionProps.maxAmountForManualProcessing()) {
            return TransactionAction.MANUAL_PROCESSING;
        } else {
            return TransactionAction.PROHIBITED;
        }
    }
}
