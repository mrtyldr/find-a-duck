package com.kodizim.findaduck.domain.job;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public record rateCommand(@NotNull @Min(1) @Max(5) Integer rate) {
}
