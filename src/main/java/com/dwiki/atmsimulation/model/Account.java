package com.dwiki.atmsimulation.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mst_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

	@Id
	private String accountNumber;
	private String name;
	private String pin;
	private Integer balance;
}