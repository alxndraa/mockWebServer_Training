package com.demo.starwars.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StarshipResponse {
    private String name;
    private String model;
    private String cost_in_credits;
}
