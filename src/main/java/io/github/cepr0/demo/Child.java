package io.github.cepr0.demo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "children")
class Child {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;

	@ManyToOne
	private Parent parent;

	public Child(String name, Parent parent) {
		this.name = name;
		this.parent = parent;
	}
}
