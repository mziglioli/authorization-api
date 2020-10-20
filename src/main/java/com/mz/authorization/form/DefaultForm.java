package com.mz.authorization.form;

import com.mz.authorization.model.EntityJpa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class DefaultForm {

    String id;
    boolean active;

	public abstract EntityJpa convertToEntity();
}