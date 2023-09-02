package com.jovicsantos.suneverapi.application.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class MeasurementOutput {
	private UUID id;
	private String name;
	private String abbreviation;
}
