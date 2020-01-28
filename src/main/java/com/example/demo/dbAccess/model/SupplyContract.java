package com.example.demo.dbAccess.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@Document("SupplyContract")
public class SupplyContract {

    String id;
    Instant supplyTime;
    String partName;
    int partPrice;
    int partQuantity;

}
