package com.yygh.hosp.repository;



import com.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface HospitalRepository extends MongoRepository<Hospital, String> {
    //判断是否存在数据
    Hospital getHospitalByHoscode(String hoscode);


    /**
     * 对mongodb进行按照医院的名称进行模糊查询
     * @param hosname 医院名称
     * @return 医院集合
     */
    List<Hospital> findHospitalByHosnameLike(String hosname);



}
