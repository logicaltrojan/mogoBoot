package com.example.demo.dbAccess.controller;

import com.example.demo.dbAccess.Repository.PartMetaRepo;
import com.example.demo.dbAccess.model.PartMeta;
import com.example.demo.dbAccess.model.PartStats;
import com.example.demo.dbAccess.model.SupplyContract;
import com.example.demo.dbAccess.model.TestDTO;
import com.example.demo.dbAccess.service.PartMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping("/test")
public class testController {


    @Autowired
    PartMetaRepo repo;

    @Autowired
    PartMetaService partMetaService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public List<List<PartStats>> getPartStats(@RequestBody ArrayList<TestDTO> params) throws Exception {

        ArrayList<List<PartStats>> partStats = new ArrayList<>();

        for(TestDTO testDTO : params){
           partStats.add(partMetaService.getStatByPartNames(testDTO.getClusterId(), testDTO.getPartMetaName()));
        }

        return partStats;
    }

    @ResponseBody
    @RequestMapping(value ="/gen", method= RequestMethod.GET)
    public ArrayList<TestDTO> generator(){

        ArrayList<TestDTO> testDTOS = new ArrayList<>();

        Random r = new Random();

        for(int i = 0; i < 5000; i++){

            TestDTO testDTO = TestDTO.builder().clusterId(Integer.toString(i)).partMetaName(new ArrayList<>()).build();

            int howManyInCluster = -1;
            while(howManyInCluster < 0) {
                howManyInCluster = r.nextInt(15);
            }
            for(int j = 0; j < howManyInCluster; j++){
                testDTO.getPartMetaName().add(Integer.toString(r.nextInt(99999)));
            }
            testDTOS.add(testDTO);
        }

        return testDTOS;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String test(){
        Random r = new Random();

        ArrayList<PartMeta> partMetas = new ArrayList<>();

        int count = 0;

        for(int i = 0; i < 100000 ; i++){
            PartMeta partMeta  = PartMeta.builder()
                    .partName(Integer.toString(i))
                    .supplyContractList(new ArrayList<>())
                    .build();
             int howManyContract = r.nextInt(20);
             for(int j = 0; j < howManyContract; j++){

                 SupplyContract supplyContract = SupplyContract.builder()
                         .partName(Integer.toString(i))
                         .partPrice(r.nextInt(10000))
                         .partQuantity(r.nextInt(100000))
                         .supplyTime(timestamp())
                         .build();

                 partMeta.getSupplyContractList().add(supplyContract);
             }

            partMetas.add(partMeta);
            count++;

        }

        repo.insert(partMetas);

        return "SUCCESS" + count;
    }

    public static Instant between(Instant startInclusive, Instant endExclusive) {
        long startSeconds = startInclusive.getEpochSecond();
        long endSeconds = endExclusive.getEpochSecond();
        long random = ThreadLocalRandom
                .current()
                .nextLong(startSeconds, endSeconds);

        return Instant.ofEpochSecond(random);
    }

    public static Instant timestamp() {
        return Instant.ofEpochSecond(ThreadLocalRandom.current().nextInt());
    }
}
