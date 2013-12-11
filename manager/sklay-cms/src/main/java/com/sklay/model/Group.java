package com.sklay.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sklay.core.enums.AuditStatus;
import com.sklay.core.enums.MemberRole;

@Entity
@Table(name = "sklay_group")
public class Group implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6319444707159030312L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	private MemberRole role;

	@Lob
	private String perms;

	@Lob
	private String description;

	@Column(name = "member_count", columnDefinition = "int default 0")
	private Long memberCount;

	@ManyToOne
	@JoinColumn(name = "owner", nullable = false)
	private User owner;

	private AuditStatus status;

	@Column(name = "pgid")
	private Long parentGroupId;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AuditStatus getStatus() {
		return status;
	}

	public void setStatus(AuditStatus status) {
		this.status = status;
	}

	public MemberRole getRole() {
		return role;
	}

	public void setRole(MemberRole role) {
		this.role = role;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public Long getParentGroupId() {
		return parentGroupId;
	}

	public void setParentGroupId(Long parentGroupId) {
		this.parentGroupId = parentGroupId;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Long getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(Long memberCount) {
		this.memberCount = memberCount;
	}

	public Group() {
		super();
	}

	public Group(String name, String description, AuditStatus status) {
		super();
		this.name = name;
		this.description = description;
		this.status = status;
	}

	public Group(String name, MemberRole role, String description, User owner,
			AuditStatus status, Long parentGroupId) {
		super();
		this.name = name;
		this.role = role;
		this.description = description;
		this.owner = owner;
		this.status = status;
		this.parentGroupId = parentGroupId;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", role=" + role
				+ ", perms=" + perms + ", description=" + description
				+ ", memberCount=" + memberCount + ", status=" + status
				+ ", parentGroupId=" + parentGroupId + "]";
	}

}
