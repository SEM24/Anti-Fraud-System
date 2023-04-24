package antifraud.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "transaction")
public record TransactionProperties(
        int maxAmountForAllowed,
        int maxAmountForManualProcessing) {
}