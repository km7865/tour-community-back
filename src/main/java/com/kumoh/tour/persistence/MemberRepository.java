package com.kumoh.tour.persistence;

import com.kumoh.tour.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    boolean existsByEmail(String email);

    MemberEntity findByEmail(String email);

    @Query(value = "SELECT m FROM MemberEntity m INNER JOIN m.tours t WHERE t.id = :tourId")
    List<MemberEntity> findByTourId(@Param("tourId") Long tourId);


}
