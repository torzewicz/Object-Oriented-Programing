package com.obiektowe.classes.Interfaces;

import com.obiektowe.classes.DataFrame;

public interface Groupby {

    DataFrame apply(Applyable applyable);

    DataFrame max();

    DataFrame min();

    DataFrame mean();

    DataFrame std();

    DataFrame sum();

    DataFrame var();
}
