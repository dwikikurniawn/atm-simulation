package com.dwiki.atmsimulation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "mst_transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

	@Id
	@GeneratedValue(generator = "transaction_uuid")
	@GenericGenerator(name = "transaction_uuid", strategy = "uuid")
	private String id;
	private String sourceAccountNumber;
	private String type;
	private Integer amount;
	@UpdateTimestamp
	private Instant time;
	private String recipientAccountNumber;
}
