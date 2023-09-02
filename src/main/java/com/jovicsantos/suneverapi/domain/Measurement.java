package com.jovicsantos.suneverapi.domain;

import java.util.UUID;

public record Measurement(UUID id, String name, String abbreviation) {
	public Measurement(String name, String abbreviation) {
		this(null, name, abbreviation);
	}
}
