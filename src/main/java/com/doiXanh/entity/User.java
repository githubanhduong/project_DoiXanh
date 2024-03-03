package com.doiXanh.entity;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"groupUser", "active"})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "group_id", nullable = false)     1
//    @JsonManagedReference
//    private GroupUser groupUser;

//	@Column(name = "group_id", insertable = false, updatable = false)			2
//	private int groupId;

	@ManyToOne
	@JoinColumn(name = "group_id", nullable = false)
	private GroupUser groupUser;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String email;

	@Column
	private String phone;

	@Column(name = "active", nullable = false, columnDefinition = "tinyint(4) DEFAULT '0'")
	private boolean isActive;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Transient
	private int groupId;

	@Transient
	private Boolean error;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User other = (User) o;
		return Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(email, other.email) && Objects.equals(phone, other.phone)
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(updatedAt, other.updatedAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName, email, phone, createdAt, updatedAt);
	}
//    @ManyToOne(fetch = FetchType.LAZY) 
//    @JoinColumn(name = "group_id", nullable = false)
//    private GroupUser group;
}
