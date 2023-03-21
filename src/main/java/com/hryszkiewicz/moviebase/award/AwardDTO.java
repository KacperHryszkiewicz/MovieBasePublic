package com.hryszkiewicz.moviebase.award;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AwardDTO {

    private String awardId;

    private String name;

    public AwardDTO(String name) {
        this.name = name;
    }
}
