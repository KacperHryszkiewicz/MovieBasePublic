package com.hryszkiewicz.moviebase.rate;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RateDTO {

    private String rateId;

    private int rateValue;

    private String author;

    private String comment;
}
