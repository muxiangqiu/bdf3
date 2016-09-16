package com.bstek.bdf3.jpa.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class User implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Id
		@Column(name = "id")
		private String id;
		private String name;
		private int age;
		@Temporal(TemporalType.DATE)
		private Date birthday;
		private boolean enabled;
		private Gender gender;
		@Column(name = "dept_Id_")
		private String deptId;
		@ManyToOne
		@JoinColumn(name = "dept_Id_", insertable = false, updatable = false)
		@Transient
		private Dept dept;
		
		public User() {
			
		}
		
		public User(String id) {
			this.id = id;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public Date getBirthday() {
			return birthday;
		}
		public void setBirthday(Date birthday) {
			this.birthday = birthday;
		}
		public boolean isEnabled() {
			return enabled;
		}
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
		public Gender getGender() {
			return gender;
		}
		public void setGender(Gender gender) {
			this.gender = gender;
		}
		
		public String getDeptId() {
			return deptId;
		}

		public void setDeptId(String deptId) {
			this.deptId = deptId;
		}

		public Dept getDept() {
			return dept;
		}

		public void setDept(Dept dept) {
			this.dept = dept;
		}

		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + ", age=" + age
					+ ", birthday=" + birthday + ", enabled=" + enabled
					+ ", gender=" + gender + "]";
		}
		
		
		
	}