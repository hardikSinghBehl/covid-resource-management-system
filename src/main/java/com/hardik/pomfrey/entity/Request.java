package com.hardik.pomfrey.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.locationtech.jts.geom.Point;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Table(name = "requests")
@Data
public class Request implements Serializable {

	private static final long serialVersionUID = 8450744347572759415L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true, insertable = false, updatable = false)
	private UUID id;

	@Column(name = "resource_type_id", nullable = false)
	private Integer resourceTypeId;

	@Hidden
	@Exclude
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "resource_type_id", nullable = false, insertable = false, updatable = false)
	private MasterResourceType resourceType;

	@Column(name = "requested_by_user_id", nullable = false)
	private UUID requestedByUserId;

	@Hidden
	@Exclude
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "requested_by_user_id", nullable = false, insertable = false, updatable = false)
	private User requestedByUser;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "location", nullable = false)
	private Point location;

	@Column(name = "is_active", nullable = false)
	private Boolean isActive;

	@Column(name = "fulfilled_by_user_id", nullable = true)
	private UUID fulfilledByUserId;

	@Hidden
	@Exclude
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "fulfilled_by_user_id", nullable = true, insertable = false, updatable = false)
	private User fulfilledByUser;

	@Column(name = "created_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
	private LocalDateTime updatedAt;

}
