package com.d23alex.lab42.check;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckRequest {
    Double x;
    Double y;
    List<Double> radii;

}
