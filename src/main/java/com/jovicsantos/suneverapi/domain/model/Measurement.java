package com.jovicsantos.suneverapi.domain.model;

import java.util.UUID;
import lombok.Data;

@Data
public class Measurement {
  private UUID id;
  private String name;
  private String abbreviation;
}