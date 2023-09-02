package com.jovicsantos.suneverapi.api.dto;

import com.jovicsantos.suneverapi.application.input.MeasurementInput;
import com.jovicsantos.suneverapi.application.output.MeasurementOutput;
import com.jovicsantos.suneverapi.domain.Measurement;

public class MeasurementDtoMapper {
	public Measurement toDomain(MeasurementInput measurementInput) {
		return new Measurement(measurementInput.name(), measurementInput.abbreviation());
	}

	public MeasurementInput toInput(Measurement measurement) {
		return new MeasurementInput(measurement.name(), measurement.abbreviation());
	}

	public MeasurementOutput toOutput(Measurement measurement) {
		return new MeasurementOutput(measurement.id(), measurement.name(), measurement.abbreviation());
	}
}
