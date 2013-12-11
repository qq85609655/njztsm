package com.sklay.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.sklay.enums.LogLevelType;

@Entity
@Table(name = "sklay_operation")
public class Operation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	@NotBlank
	private String name;

	@Column(name = "content")
	@Lob
	private String content;

	@Column(name = "desctiption")
	@Lob
	private String desctiption;

	@Column(name = "type")
	private LogLevelType type;

	@Column(name = "create_time")
	private Date createTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesctiption() {
		return desctiption;
	}

	public void setDesctiption(String desctiption) {
		this.desctiption = desctiption;
	}

	public LogLevelType getType() {
		return type;
	}

	public void setType(LogLevelType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Operation [id=" + id + ", name=" + name + ", content="
				+ content + ", desctiption=" + desctiption + ", type=" + type
				+ "]";
	}

}
