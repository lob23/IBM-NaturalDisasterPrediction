package com.example.naturaldisasterprediction.Service;

import com.example.naturaldisasterprediction.Home.SupplierResponse;

public interface SupplierResponseCallback {
    void onSuccess(SupplierResponse response);
    void onFailure(String errorMessage);
}

