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
import com.sklay.core.enums.Sex;

/**
 * The persistent class for the sklay_user database table.
 * 
 */
@Entity
@Table(name = "sklay_user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(length = 50)
	private String phone;

	@Column(name = "name", length = 20)
	private String name;

	@Column(length = 64)
	private String password;

	@Column(length = 30)
	private String salt;

	private String area;

	private String address;

	@Column(name = "medical_history")
	private String medicalHistory;

	private Integer height;

	private Integer weight;

	@Lob
	private String description;

	private Integer age;

	private Sex sex;

	private AuditStatus status;

	@ManyToOne
	@JoinColumn(name = "gid", nullable = true)
	private Group group;

	private Long belong;

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public AuditStatus getStatus() {
		return status;
	}

	public void setStatus(AuditStatus status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Long getBelong() {
		return belong;
	}

	public void setBelong(Long belong) {
		this.belong = belong;
	}

	public String toString() {
		return "User [id=" + id + ", phone=" + phone + ", name=" + name
				+ ", password=" + password + ", salt=" + salt + ", area="
				+ area + ", address=" + address + ", medicalHistory="
				+ medicalHistory + ", height=" + height + ", weight=" + weight
				+ ", description=" + description + ", age=" + age + ", group="
				+ group + "]";
	}

}