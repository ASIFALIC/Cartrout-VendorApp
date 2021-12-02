package com.oliek.cartrout.model.base;

import java.util.List;


public interface BaseCategory<T extends BaseItem>  {

    public List<T> getItems();

}
