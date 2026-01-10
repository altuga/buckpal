package io.reflectoring.buckpal.application.domain.model;

import java.math.BigInteger;
import java.util.Objects;

public final class Money {

	public static Money ZERO = Money.of(0L);

	private final BigInteger amount;

    public Money(BigInteger amount) {
        this.amount = Objects.requireNonNull(amount);
    }

    public BigInteger getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return "Money(amount=" + amount + ")";
    }

	public boolean isPositiveOrZero(){
		return this.amount.compareTo(BigInteger.ZERO) >= 0;
	}

	public boolean isNegative(){
		return this.amount.compareTo(BigInteger.ZERO) < 0;
	}

	public boolean isPositive(){
		return this.amount.compareTo(BigInteger.ZERO) > 0;
	}

	public boolean isGreaterThanOrEqualTo(Money money){
		return this.amount.compareTo(money.amount) >= 0;
	}

	public boolean isGreaterThan(Money money){
		return this.amount.compareTo(money.amount) >= 1;
	}

	public static Money of(long value) {
		return new Money(BigInteger.valueOf(value));
	}

	public static Money add(Money a, Money b) {
		return new Money(a.amount.add(b.amount));
	}

	public Money minus(Money money){
		return new Money(this.amount.subtract(money.amount));
	}

	public Money plus(Money money){
		return new Money(this.amount.add(money.amount));
	}

	public static Money subtract(Money a, Money b) {
		return new Money(a.amount.subtract(b.amount));
	}

	public Money negate(){
		return new Money(this.amount.negate());
	}

}
