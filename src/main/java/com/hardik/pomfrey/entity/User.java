package com.hardik.pomfrey.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.locationtech.jts.geom.Point;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {

	private static final long serialVersionUID = 2693689507826017120L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true, insertable = false, updatable = false)
	private UUID id;

	@Column(name = "email_id", nullable = false, unique = true)
	private String emailId;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "contact_number")
	private String contactNumber;

	@Column(name = "state_id", nullable = false)
	private Integer stateId;

	@Column(name = "location", nullable = false)
	private Point location;

	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime updatedAt;

	@Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "requestedByUser", fetch = FetchType.LAZY)
	private Set<Request> requested;

	@Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "fulfilledByUser", fetch = FetchType.LAZY)
	private Set<Request> fulfilled;

	@Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<Resource> resources;

	@Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "followerUser", fetch = FetchType.LAZY)
	private Set<FollowMapping> following;

	@Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "followedUser", fetch = FetchType.LAZY)
	private Set<FollowMapping> followers;

	@Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<ReportMapping> reports;

	@Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<Comment> comments;

}
