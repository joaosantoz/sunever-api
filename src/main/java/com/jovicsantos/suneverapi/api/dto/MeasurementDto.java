package com.jovicsantos.suneverapi.api.dto;

import com.jovicsantos.suneverapi.application.input.MeasurementInput;
import com.jovicsantos.suneverapi.application.output.MeasurementOutput;
import com.jovicsantos.suneverapi.domain.Measurement;

public class MeasurementDto {
	public Measurement toDomain(MeasurementInput measurementInput) {
		return new Measurement(null, measurementInput.getName(), measurementInput.getAbbreviation());
	}

	public MeasurementOutput toOutput(Measurement measurement) {
		return new MeasurementOutput(measurement.getId(), measurement.getName(), measurement.getAbbreviation());
	}
}
