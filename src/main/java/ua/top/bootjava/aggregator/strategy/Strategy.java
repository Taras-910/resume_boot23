package ua.top.bootjava.aggregator.strategy;

import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.to.ResumeTo;

import java.io.IOException;
import java.util.List;

public interface Strategy {
    List<ResumeTo> getResumes (Freshen doubleString) throws IOException;
}
