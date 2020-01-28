package com.example.demo.dbAccess.service;

import com.example.demo.dbAccess.Repository.PartMetaRepo;
import com.example.demo.dbAccess.model.PartStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartMetaService {

    @Autowired
    PartMetaRepo partMetaRepo;

    MongoTemplate mongoTemplate;

    @Autowired
    public PartMetaService(MongoTemplate mongoTemplate) {
        this.mongoTemplate= mongoTemplate;
    }

    public List<PartStats> getStatByPartNames(String clusterId, ArrayList<String> partNames) throws Exception {

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("partName").in(partNames)),
                Aggregation.unwind("supplyContractList"),
                Aggregation.group(clusterId).avg("supplyContractList.partPrice").as("avg")
                        .stdDevSamp("supplyContractList.partPrice").as("variance"),
                Aggregation.match(Criteria.where("variance").ne(null))
        );

        AggregationResults<PartStats> results= mongoTemplate.aggregate(agg, "PartMeta", PartStats.class);

        List<PartStats> partStats =  results.getMappedResults();

        return partStats;


    }
    public List<PartStats> getStatsByPartNamesGroupByPartName(ArrayList<String> partNames){
        Aggregation agg = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("partName").in(partNames)),
                Aggregation.unwind("supplyContractList"),
                Aggregation.group("partName").avg("supplyContractList.partPrice").as("avg")
                .stdDevSamp("supplyContractList.partPrice").as("variance")
        );

        AggregationResults<PartStats> results= mongoTemplate.aggregate(agg, "PartMeta", PartStats.class);


        return results.getMappedResults();




    }
}
