package ru.booklink.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rights")
public class AccessRight {
	private Integer rightId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "right_id", unique = true, nullable = false)
	public Integer getRightId() {
		return rightId;
	}

	public void setRightId(Integer rightId) {
		this.rightId = rightId;
	}

}
