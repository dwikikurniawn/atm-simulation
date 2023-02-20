package com.dwiki.atmsimulation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "mst_transaction")
@Getter
@NoArgsConstructor
public class Transaction {

	public Transaction(String sourceAccountNumber, String type, Integer amount, String recipientAccountNumber, String referenceNumber) {
		this.sourceAccountNumber = sourceAccountNumber;
		this.type = type;
		this.amount = amount;
		this.recipientAccountNumber = recipientAccountNumber;
		this.referenceNumber = referenceNumber;
	}

	@Id
	@GeneratedValue(generator = "transaction_uuid")
	@GenericGenerator(name = "transaction_uuid", strategy = "uuid")
	private String id;
	private String sourceAccountNumber;
	private String type;
	private Integer amount;
	@CreationTimestamp
	private Instant time;
	private String recipientAccountNumber;
	private String referenceNumber;
}
