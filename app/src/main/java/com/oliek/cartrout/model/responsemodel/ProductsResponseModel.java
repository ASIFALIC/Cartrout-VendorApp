package com.oliek.cartrout.model.responsemodel;

import com.oliek.cartrout.model.CategoryModel;
import com.oliek.cartrout.model.ProductModel;
import com.oliek.cartrout.model.base.BaseResponse;

import java.util.ArrayList;

public class ProductsResponseModel extends BaseResponse {
    private ArrayList<ProductModel> products;

    public ArrayList<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductModel> products) {
        this.products = products;
    }
}
