package com.doiXanh.component;

import java.util.Date;
import java.util.Objects;

import lombok.Data;

@Data
public class UserExcel {
	private int id;

	private int groupId;

	private String firstName;

	private String lastName;

	private String email;

	private String phone;

	private Date createdAt;

	private Date updatedAt;

	private Boolean error;

//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		UserExcel other = (UserExcel) o;
//		return Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
//				&& Objects.equals(email, other.email) && Objects.equals(phone, other.phone)
//				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(updatedAt, other.updatedAt);
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(firstName, lastName, email, phone, createdAt, updatedAt);
//	}
}
