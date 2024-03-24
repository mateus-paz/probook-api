package com.mateuspaz.probook.strategy.validator;

import com.mateuspaz.probook.entity.PBEntity;

public interface IValidator<ENTITY extends PBEntity> {

	void execute(ENTITY entity);

}
