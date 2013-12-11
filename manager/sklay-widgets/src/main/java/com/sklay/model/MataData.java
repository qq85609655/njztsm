package com.sklay.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sklay.core.enums.Sex;

@Entity
@Table(name = "sklay_matadata")
public class MataData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4300470862401796027L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(name = "age_min")
	private Integer ageMin;

	@Column(name = "age_max")
	private Integer ageMax;

	private Sex sex;

	@Column(name = "low_min")
	private Integer lowPressureMin;

	@Column(name = "low_max")
	private Integer lowPressureMax;

	@Column(name = "high_min")
	private Integer highPressureMin;

	@Column(name = "high_max")
	private Integer highPressureMax;

	private String result;

	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAgeMin() {
		return ageMin;
	}

	public void setAgeMin(Integer ageMin) {
		this.ageMin = ageMin;
	}

	public Integer getAgeMax() {
		return ageMax;
	}

	public void setAgeMax(Integer ageMax) {
		this.ageMax = ageMax;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Integer getLowPressureMin() {
		return lowPressureMin;
	}

	public void setLowPressureMin(Integer lowPressureMin) {
		this.lowPressureMin = lowPressureMin;
	}

	public Integer getLowPressureMax() {
		return lowPressureMax;
	}

	public void setLowPressureMax(Integer lowPressureMax) {
		this.lowPressureMax = lowPressureMax;
	}

	public Integer getHighPressureMin() {
		return highPressureMin;
	}

	public void setHighPressureMin(Integer highPressureMin) {
		this.highPressureMin = highPressureMin;
	}

	public Integer getHighPressureMax() {
		return highPressureMax;
	}

	public void setHighPressureMax(Integer highPressureMax) {
		this.highPressureMax = highPressureMax;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public int hashCode() {
		final Integer prime = 31;
		Integer result = 1;
		result = prime * result + ageMax;
		result = prime * result + ageMin;
		result = prime * result + highPressureMax;
		result = prime * result + highPressureMin;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + lowPressureMax;
		result = prime * result + lowPressureMin;
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result
				+ ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MataData other = (MataData) obj;
		if (ageMax != other.ageMax)
			return false;
		if (ageMin != other.ageMin)
			return false;
		if (highPressureMax != other.highPressureMax)
			return false;
		if (highPressureMin != other.highPressureMin)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lowPressureMax != other.lowPressureMax)
			return false;
		if (lowPressureMin != other.lowPressureMin)
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (sex != other.sex)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MataData [id=" + id + ", ageMin=" + ageMin + ", ageMax="
				+ ageMax + ", sex=" + sex + ", lowPressureMin="
				+ lowPressureMin + ", lowPressureMax=" + lowPressureMax
				+ ", highPressureMin=" + highPressureMin + ", highPressureMax="
				+ highPressureMax + ", result=" + result + ", remark=" + remark
				+ "]";
	}

}
