package com.bstek.bdf3;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String id;
		private String name;
		private int age;
		private Date birthday;
		private boolean enabled;
		private Gender gender;
		
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
		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + ", age=" + age
					+ ", birthday=" + birthday + ", enabled=" + enabled
					+ ", gender=" + gender + "]";
		}
		
		
		
	}