package com.obiektowe.classes.Interfaces;

import com.obiektowe.classes.DataFrame;
import com.obiektowe.classes.Exceptions.WrongInsertionTypeException;

public interface Applyable {

    DataFrame apply(DataFrame dataFrame) throws WrongInsertionTypeException;
}
