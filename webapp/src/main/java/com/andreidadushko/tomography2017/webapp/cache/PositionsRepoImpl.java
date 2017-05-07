package com.andreidadushko.tomography2017.webapp.cache;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PositionsRepoImpl extends AbstractRepoImpl<List<String>> implements IPositionsRepo {

	@Override
	protected String getKey() {
		return "Positions";
	}

}
