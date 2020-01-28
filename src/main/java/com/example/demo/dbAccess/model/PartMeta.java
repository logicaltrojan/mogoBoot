package com.example.demo.dbAccess.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@Builder
@Document("PartMeta")
public class PartMeta {

    String id;
    String partName;
    ArrayList<SupplyContract> supplyContractList;
}
