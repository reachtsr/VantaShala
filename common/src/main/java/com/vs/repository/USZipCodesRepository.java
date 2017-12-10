package com.vs.repository;

import com.vs.model.geo.ZipData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */

public interface USZipCodesRepository extends MongoRepository<ZipData, String> {

    ZipData findBy_id(String zipCode);
}
