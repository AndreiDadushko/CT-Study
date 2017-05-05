package com.andreidadushko.tomography2017.dao.db.utils;

import java.util.List;

import com.andreidadushko.tomography2017.dao.db.filters.SortData;

public class FilterUtil {

	public static StringBuilder makeWhere(StringBuilder builder, List<String> sqlParts) {
		if (!sqlParts.isEmpty()) {
			builder.append(" WHERE ");
			for (int i = 0; i < sqlParts.size(); i++) {
				if (i != 0)
					builder.append(" AND ");
				builder.append(sqlParts.get(i));
			}
		}
		return builder;
	}

	public static StringBuilder makeSort(StringBuilder builder, SortData sort) {
		builder.append(" ORDER BY " + sort.getColumn());
		if (sort.getOrder() != null) {
			builder.append(" " + sort.getOrder());
		}
		return builder;
	}
}
