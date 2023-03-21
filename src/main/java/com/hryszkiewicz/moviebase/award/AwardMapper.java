package com.hryszkiewicz.moviebase.award;

import com.hryszkiewicz.moviebase.award.AwardDTO;
import com.hryszkiewicz.moviebase.award.Award;
import com.hryszkiewicz.moviebase.exception.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AwardMapper {

    public AwardDTO toDTO(Award award) {
        if (award == null) {
            throw new BadRequestException("Award entity cannot be null.");
        }
        var awardDTO = new AwardDTO();
        BeanUtils.copyProperties(award, awardDTO);
        return awardDTO;
    }

    public Award toEntity(AwardDTO awardDTO) {
        if (awardDTO == null) {
            throw new BadRequestException("Award DTO cannot be null.");
        }
        var award = new Award();
        BeanUtils.copyProperties(awardDTO, award);
        return award;
    }
}
