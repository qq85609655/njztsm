package com.sklay.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sklay_sms_blackword")
public class Blackword implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	private String blackword;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBlackword() {
		return blackword;
	}

	public void setBlackword(String blackword) {
		this.blackword = blackword;
	}

	@Override
	public String toString() {
		return "Blackword [id=" + id + ", blackword=" + blackword + "]";
	}
}
