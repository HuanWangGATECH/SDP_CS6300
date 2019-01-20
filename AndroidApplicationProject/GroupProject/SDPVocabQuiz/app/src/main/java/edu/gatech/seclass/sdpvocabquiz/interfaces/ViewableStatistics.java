package edu.gatech.seclass.sdpvocabquiz.interfaces;

import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.data.Score;

public interface ViewableStatistics
{
    // Get all scores call by either Student or Quiz
    public List<Score> stats();
}
