package com.example.demo.dbAccess.model;


import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class TestDTO {

    private String clusterId;
    private ArrayList<String>  partMetaName;
}
