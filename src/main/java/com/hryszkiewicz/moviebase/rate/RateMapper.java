package com.hryszkiewicz.moviebase.rate;

import com.hryszkiewicz.moviebase.exception.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class RateMapper {

    public RateDTO toDTO(Rate rate) {
        if (rate == null) {
            throw new BadRequestException("Rate entity cannot be null.");
        }
        var rateDTO = new RateDTO();
        BeanUtils.copyProperties(rate, rateDTO);
        return rateDTO;
    }

    public Rate toEntity(RateDTO rateDTO) {
        if (rateDTO == null) {
            throw new BadRequestException("Rate DTO cannot be null.");
        }
        var rate = new Rate();
        BeanUtils.copyProperties(rateDTO, rate);
        return rate;
    }
}
