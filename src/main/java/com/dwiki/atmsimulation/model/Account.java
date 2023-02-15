package com.dwiki.atmsimulation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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