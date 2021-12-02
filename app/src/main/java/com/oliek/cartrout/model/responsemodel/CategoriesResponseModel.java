package com.oliek.cartrout.model.responsemodel;

import com.oliek.cartrout.model.CategoryModel;
import com.oliek.cartrout.model.base.BaseResponse;

import java.util.ArrayList;

public class CategoriesResponseModel extends BaseResponse {
    private ArrayList<CategoryModel> categories;

    public ArrayList<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<CategoryModel> categories) {
        this.categories = categories;
    }
}
