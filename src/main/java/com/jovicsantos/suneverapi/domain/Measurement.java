package com.jovicsantos.suneverapi.domain;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Measurement {
	private UUID id;
	private String name;
	private String abbreviation;
}
