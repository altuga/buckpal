package io.reflectoring.buckpal.application.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A money transfer activity between {@link Account}s.
 */
public class Activity {

	private final ActivityId id;

	/**
	 * The account that owns this activity.
	 */
	private final Account.AccountId ownerAccountId;

	/**
	 * The debited account.
	 */
	private final Account.AccountId sourceAccountId;

	/**
	 * The credited account.
	 */
	private final Account.AccountId targetAccountId;

	/**
	 * The timestamp of the activity.
	 */
	private final LocalDateTime timestamp;

	/**
	 * The money that was transferred between the accounts.
	 */
	private final Money money;

    public Activity(ActivityId id, Account.AccountId ownerAccountId, Account.AccountId sourceAccountId, Account.AccountId targetAccountId, LocalDateTime timestamp, Money money) {
        this.id = id;
        this.ownerAccountId = ownerAccountId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.timestamp = timestamp;
        this.money = money;
    }

    public ActivityId getId() {
        return id;
    }

    public Account.AccountId getOwnerAccountId() {
        return ownerAccountId;
    }

    public Account.AccountId getSourceAccountId() {
        return sourceAccountId;
    }

    public Account.AccountId getTargetAccountId() {
        return targetAccountId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Money getMoney() {
        return money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id) && Objects.equals(ownerAccountId, activity.ownerAccountId) && Objects.equals(sourceAccountId, activity.sourceAccountId) && Objects.equals(targetAccountId, activity.targetAccountId) && Objects.equals(timestamp, activity.timestamp) && Objects.equals(money, activity.money);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerAccountId, sourceAccountId, targetAccountId, timestamp, money);
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", ownerAccountId=" + ownerAccountId +
                ", sourceAccountId=" + sourceAccountId +
                ", targetAccountId=" + targetAccountId +
                ", timestamp=" + timestamp +
                ", money=" + money +
                '}';
    }

	public Activity(
			Account.AccountId ownerAccountId,
			Account.AccountId sourceAccountId,
			Account.AccountId targetAccountId,
			LocalDateTime timestamp,
			Money money) {
		this.id = null;
		this.ownerAccountId = Objects.requireNonNull(ownerAccountId);
		this.sourceAccountId = Objects.requireNonNull(sourceAccountId);
		this.targetAccountId = Objects.requireNonNull(targetAccountId);
		this.timestamp = Objects.requireNonNull(timestamp);
		this.money = Objects.requireNonNull(money);
	}

	public static class ActivityId {
		private final Long value;

        public ActivityId(Long value) {
            this.value = value;
        }

        public Long getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ActivityId that = (ActivityId) o;
            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return "ActivityId(value=" + value + ")";
        }
	}

}
