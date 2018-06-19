package io.github.cepr0.demo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Data
@Entity
@Table(name = "parents")
class Parent {
	@Id
	@GeneratedValue
	private Integer id;
   private String name;

	public Parent(String name) {
		this.name = name;
	}
}
