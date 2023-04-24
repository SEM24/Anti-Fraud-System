package antifraud.controller;

import antifraud.model.request.TransactionRequest;
import antifraud.model.response.TransactionResultResponse;
import antifraud.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/antifraud")
public class AntifraudController {
    TransactionService transactionService;

    @PostMapping("/transaction")
    TransactionResultResponse validateTransaction(@RequestBody @Valid TransactionRequest transaction) {
        return new TransactionResultResponse(transactionService.checkTransaction(transaction.getAmount()));
    }
}
