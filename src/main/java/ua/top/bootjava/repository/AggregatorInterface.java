package ua.top.bootjava.repository;


import ua.top.bootjava.model.Freshen;
import ua.top.bootjava.to.ResumeTo;

import java.util.List;

public interface AggregatorInterface {
    List<ResumeTo> selectBy(Freshen freshen);
}
