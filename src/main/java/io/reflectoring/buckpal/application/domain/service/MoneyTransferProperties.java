package io.reflectoring.buckpal.application.domain.service;

import io.reflectoring.buckpal.application.domain.model.Money;

/**
 * Configuration properties for money transfer use cases.
 */
public class MoneyTransferProperties {

  private Money maximumTransferThreshold = Money.of(1_000_000L);

  public MoneyTransferProperties() {
  }

  public MoneyTransferProperties(Money maximumTransferThreshold) {
    this.maximumTransferThreshold = maximumTransferThreshold;
  }

  public Money getMaximumTransferThreshold() {
    return maximumTransferThreshold;
  }

  public void setMaximumTransferThreshold(Money maximumTransferThreshold) {
    this.maximumTransferThreshold = maximumTransferThreshold;
  }

  @Override
  public String toString() {
    return "MoneyTransferProperties{" +
            "maximumTransferThreshold=" + maximumTransferThreshold +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MoneyTransferProperties that = (MoneyTransferProperties) o;

    return maximumTransferThreshold != null ? maximumTransferThreshold.equals(that.maximumTransferThreshold) : that.maximumTransferThreshold == null;
  }

  @Override
  public int hashCode() {
    return maximumTransferThreshold != null ? maximumTransferThreshold.hashCode() : 0;
  }
}
