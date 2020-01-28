package com.example.demo.dbAccess.Repository;

import com.example.demo.dbAccess.model.PartMeta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartMetaRepo extends MongoRepository<PartMeta, String> {


}
