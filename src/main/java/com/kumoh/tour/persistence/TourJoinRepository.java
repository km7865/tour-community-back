package com.kumoh.tour.persistence;

import com.kumoh.tour.entity.JoinStatus;
import com.kumoh.tour.entity.MemberEntity;
import com.kumoh.tour.entity.TourJoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourJoinRepository extends JpaRepository<TourJoinEntity, Long> {
    @Query(value = "SELECT tj FROM TourJoinEntity tj WHERE M_KEY = ?1")
    List<TourJoinEntity> findByMemberId(Long memberId);

    @Query(value = "SELECT count(*) FROM TourJoinEntity tj WHERE T_KEY = :tourId AND tj.status = :status")
    int countByTourId(@Param("tourId") Long tourId, @Param("status") JoinStatus status);

    @Query(value = "SELECT count(*) > 0 FROM TourJoinEntity tj WHERE T_KEY = :tourId AND M_KEY = :memberId")
    boolean existsByTourIdAndMemberId(@Param("tourId") Long tourId, @Param("memberId") Long memberId);

    @Query(value = "SELECT tj.member FROM TourJoinEntity tj WHERE T_KEY = :tourId")
    List<MemberEntity> findMemberByTourId(@Param("tourId") Long tourId);
}
