package com.bstek.bdf3.jpa.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Dept implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Id
		private String id;
		private String name;
		@OneToMany
		@JoinColumn(name = "dept_Id_", insertable = false, updatable = false)
		private List<User> users;
		
		
		public Dept() {
			
		}
		
		public Dept(String id) {
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

		public List<User> getUsers() {
			return users;
		}

		public void setUsers(List<User> users) {
			this.users = users;
		}
		
		
		
		
		
		
	}