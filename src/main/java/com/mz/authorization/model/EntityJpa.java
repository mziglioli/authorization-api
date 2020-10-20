package com.mz.authorization.model;

import com.mz.authorization.response.DefaultResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public abstract class EntityJpa implements Serializable {

	protected LocalDateTime createdDate;
	protected LocalDateTime updatedDate;
	protected String createdBy;
	protected String updatedBy;
	protected boolean active = false;

	public abstract String getId();

	@Transient
	public abstract DefaultResponse convert();
}