package io.reflectoring.buckpal.adapter.out.persistence;

import io.reflectoring.buckpal.common.PersistenceAdapter;
import org.springframework.context.annotation.Profile;

@PersistenceAdapter
@Profile("mysql")
class MySqlAccountPersistenceAdapter extends AbstractAccountPersistenceAdapter {

	MySqlAccountPersistenceAdapter(
				SpringDataAccountRepository accountRepository,
				ActivityRepository activityRepository,
				AccountMapper accountMapper) {
		super(accountRepository, activityRepository, accountMapper);
	}

}
