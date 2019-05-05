package com.colorhole.statistic;

import com.colorhole.hole.HoleCreator;
import lombok.Data;

@Data
public class StatisticContainer {
    public String imageName;
    public int imageHeight;
    public int imageWidth;
    public int holeHeight;
    public int holeWidth;
    public HoleCreator.HoleForm holeForm;
    public double holeArea;
}
