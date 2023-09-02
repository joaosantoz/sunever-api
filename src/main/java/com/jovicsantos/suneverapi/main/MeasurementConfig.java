package com.jovicsantos.suneverapi.main;

import com.jovicsantos.suneverapi.api.dto.MeasurementDtoMapper;
import com.jovicsantos.suneverapi.application.gateway.MeasurementGateway;
import com.jovicsantos.suneverapi.application.interactor.MeasurementInteractor;
import com.jovicsantos.suneverapi.infrastructure.gateway.MeasurementRepositoryGateway;
import com.jovicsantos.suneverapi.infrastructure.persistance.mapper.MeasurementMapper;
import com.jovicsantos.suneverapi.infrastructure.repository.MeasurementRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeasurementConfig {
	@Bean
	MeasurementInteractor measurementInteractor(MeasurementGateway measurementGateway) {
		return new MeasurementInteractor(measurementGateway);
	}

	@Bean
	MeasurementGateway measurementGateway(MeasurementRepository measurementRepository, MeasurementMapper measurementMapper) {
		return new MeasurementRepositoryGateway(measurementRepository, measurementMapper);
	}

	@Bean
	MeasurementMapper measurementMapper() {
		return new MeasurementMapper();
	}

	@Bean
	MeasurementDtoMapper measurementDtoMapper() {
		return new MeasurementDtoMapper();
	}
}
