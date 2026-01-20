package io.reflectoring.buckpal.adapter.out.persistence;

import io.reflectoring.buckpal.application.domain.model.Account;
import io.reflectoring.buckpal.application.domain.model.Account.AccountId;
import io.reflectoring.buckpal.application.domain.model.Activity;
import io.reflectoring.buckpal.application.port.out.LoadAccountPort;
import io.reflectoring.buckpal.application.port.out.UpdateAccountStatePort;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

abstract class AbstractAccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

	private final SpringDataAccountRepository accountRepository;
	private final ActivityRepository activityRepository;
	private final AccountMapper accountMapper;

	protected AbstractAccountPersistenceAdapter(
				SpringDataAccountRepository accountRepository,
				ActivityRepository activityRepository,
				AccountMapper accountMapper) {
		this.accountRepository = accountRepository;
		this.activityRepository = activityRepository;
		this.accountMapper = accountMapper;
	}

	@Override
	public Account loadAccount(AccountId accountId, LocalDateTime baselineDate) {
		Long accountIdValue = Objects.requireNonNull(accountId.getValue(), "accountId value must not be null");

		AccountJpaEntity account =
				accountRepository.findById(accountIdValue)
						.orElseThrow(EntityNotFoundException::new);

		List<ActivityJpaEntity> activities =
				activityRepository.findByOwnerSince(
						accountIdValue,
						baselineDate);

		Long withdrawalBalance = activityRepository
				.getWithdrawalBalanceUntil(
						accountIdValue,
						baselineDate)
				.orElse(0L);

		Long depositBalance = activityRepository
				.getDepositBalanceUntil(
						accountIdValue,
						baselineDate)
				.orElse(0L);

		return accountMapper.mapToDomainEntity(
				account,
				activities,
				withdrawalBalance,
				depositBalance);

	}

	@Override
	public void updateActivities(Account account) {
		for (Activity activity : account.getActivityWindow().getActivities()) {
			if (activity.getId() == null) {
				ActivityJpaEntity activityJpaEntity = Objects.requireNonNull(
						accountMapper.mapToJpaEntity(activity),
						"mapped activity must not be null");
				activityRepository.save(activityJpaEntity);
			}
		}
	}

}
