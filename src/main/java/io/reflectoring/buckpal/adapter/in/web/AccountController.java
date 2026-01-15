package io.reflectoring.buckpal.adapter.in.web;



import io.reflectoring.buckpal.application.domain.model.Account;
import io.reflectoring.buckpal.application.domain.model.Money;
import io.reflectoring.buckpal.application.port.in.GetAccountBalanceUseCase;
import io.reflectoring.buckpal.application.port.in.SendMoneyCommand;
import io.reflectoring.buckpal.common.WebAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@WebAdapter
public class AccountController {


    public GetAccountBalanceUseCase getAccountBalanceUseCase;
    public GetAccountBalanceUseCase.GetAccountBalanceQuery query;

    @GetMapping(path = "/accounts/{id}/balance")
    public Money getAccountBalance(@PathVariable("id") Long accountId ) {

        System.out.println(" accountId " + accountId);

        return getAccountBalanceUseCase.getAccountBalance(query);
    }


}
