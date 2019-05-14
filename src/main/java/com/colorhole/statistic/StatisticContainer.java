package com.colorhole.statistic;

import com.colorhole.hole.HoleCreator;
import lombok.Data;

@Data
public class StatisticContainer {
    private String imageName;
    private String maskName;
    private int imageHeight;
    private int imageWidth;
    private int holeHeight;
    private int holeWidth;
    private HoleCreator.HoleForm holeForm;
    private double holeArea;
}
