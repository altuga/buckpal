package io.reflectoring.buckpal.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "activity")
class ActivityJpaEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private LocalDateTime timestamp;

	@Column
	private long ownerAccountId;

	@Column
	private long sourceAccountId;

	@Column
	private long targetAccountId;

	@Column
	private long amount;

	public ActivityJpaEntity() {
	}

	public ActivityJpaEntity(Long id, LocalDateTime timestamp, long ownerAccountId, long sourceAccountId, long targetAccountId, long amount) {
		this.id = id;
		this.timestamp = timestamp;
		this.ownerAccountId = ownerAccountId;
		this.sourceAccountId = sourceAccountId;
		this.targetAccountId = targetAccountId;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public long getOwnerAccountId() {
		return ownerAccountId;
	}

	public void setOwnerAccountId(long ownerAccountId) {
		this.ownerAccountId = ownerAccountId;
	}

	public long getSourceAccountId() {
		return sourceAccountId;
	}

	public void setSourceAccountId(long sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	public long getTargetAccountId() {
		return targetAccountId;
	}

	public void setTargetAccountId(long targetAccountId) {
		this.targetAccountId = targetAccountId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ActivityJpaEntity that = (ActivityJpaEntity) o;
		return ownerAccountId == that.ownerAccountId && sourceAccountId == that.sourceAccountId && targetAccountId == that.targetAccountId && amount == that.amount && Objects.equals(id, that.id) && Objects.equals(timestamp, that.timestamp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, timestamp, ownerAccountId, sourceAccountId, targetAccountId, amount);
	}

	@Override
	public String toString() {
		return "ActivityJpaEntity{" +
				"id=" + id +
				", timestamp=" + timestamp +
				", ownerAccountId=" + ownerAccountId +
				", sourceAccountId=" + sourceAccountId +
				", targetAccountId=" + targetAccountId +
				", amount=" + amount +
				'}';
	}
}
