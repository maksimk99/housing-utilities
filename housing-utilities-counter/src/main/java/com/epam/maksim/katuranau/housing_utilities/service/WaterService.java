package com.epam.maksim.katuranau.housing_utilities.service;

import java.util.List;

public interface WaterService {

    void process(List<Integer> userIds);

    void populateData(List<Integer> userIds);
}
