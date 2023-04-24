package antifraud.model.response;

import antifraud.model.enums.TransactionAction;

public record TransactionResultResponse(TransactionAction result) {
}
