package io.reflectoring.buckpal.adapter.in.web;

import io.reflectoring.buckpal.application.domain.model.Account.AccountId;
import io.reflectoring.buckpal.application.domain.model.Money;
import io.reflectoring.buckpal.application.port.in.GetAccountBalanceUseCase;
import io.reflectoring.buckpal.application.port.in.GetAccountBalanceUseCase.GetAccountBalanceQuery;
import io.reflectoring.buckpal.common.WebAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@WebAdapter
@RequestMapping(path = "/api")
public class ApiAccountController {

	private final GetAccountBalanceUseCase getAccountBalanceUseCase;

	public ApiAccountController(GetAccountBalanceUseCase getAccountBalanceUseCase) {
		this.getAccountBalanceUseCase = getAccountBalanceUseCase;
	}

	@GetMapping(path = "/accounts/{id}/balance")
	public Money getAccountBalance(@PathVariable("id") Long accountId) {
		return getAccountBalanceUseCase.getAccountBalance(
				new GetAccountBalanceQuery(new AccountId(accountId)));
	}
}

