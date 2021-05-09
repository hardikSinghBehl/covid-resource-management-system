package com.hardik.pomfrey.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Table(name = "master_resource_types")
@Data
public class MasterResourceType implements Serializable {

	private static final long serialVersionUID = 6206080039694427391L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true, insertable = false, updatable = false)
	private Integer id;

	@Column(name = "name", nullable = false, unique = true, insertable = false)
	private String name;

	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime updatedAt;

	@Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "resourceType", fetch = FetchType.LAZY)
	private Set<Request> requests;

	@Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "resourceType", fetch = FetchType.LAZY)
	private Set<Resource> resources;

}
