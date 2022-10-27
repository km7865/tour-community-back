package com.kumoh.tour.persistence;

import com.kumoh.tour.entity.MemberEntity;
import com.kumoh.tour.entity.TourEntity;
import com.kumoh.tour.entity.TourStatus;
import com.kumoh.tour.vo.TourSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//, JpaSpecificationExecutor<TourEntity>
public interface TourRepository extends JpaRepository<TourEntity, Long>{
    @Query(value = "SELECT t FROM TourEntity t WHERE t.status = :status")
    List<TourEntity> findByStatus(TourStatus status);

    @Query(value = "SELECT t FROM TourEntity t WHERE M_KEY = ?1")
    List<TourEntity> findByMemberId(Long memberId);

    @Query(value = "SELECT t.member FROM TourEntity t WHERE t.id = :tourId")
    MemberEntity findMemberByTourId(@Param("tourId") Long tourId);

    @Query(value = "SELECT *, (6371*acos(cos(radians(?1))*cos(radians(t_departure_lat))*cos(radians(t_departure_lng)-radians(?2))+sin(radians(?1))*sin(radians(t_departure_lat)))) AS dist FROM T_TOUR HAVING dist <= 10", nativeQuery = true)
    List<TourEntity> findByLatLng(String lat, String lng);

    @Query(value = "SELECT *, (6371*acos(cos(radians(:departureLat))*cos(radians(t_departure_lat))*cos(radians(t_departure_lng)-radians(:departureLng))+sin(radians(:departureLat))*sin(radians(t_departure_lat)))) AS departure_dist, " +
            "(6371*acos(cos(radians(:destinationLat))*cos(radians(t_destination_lat))*cos(radians(t_destination_lng)-radians(:destinationLng))+sin(radians(:destinationLat))*sin(radians(t_destination_lat)))) AS destination_dist " +
            "FROM T_TOUR WHERE t_title LIKE CONCAT('%', :title, '%') AND t_activity LIKE CONCAT('%', :activity, '%') AND t_sex = :sex " +
            "AND t_start_age >= :startAge AND t_end_age <= :endAge AND t_start_date >= :startDate AND t_end_date <= :endDate AND t_status='FINDING'" +
            "HAVING departure_dist <= :departureDist AND destination_dist <= :destinationDist", nativeQuery = true)
    List<TourEntity> findBySearchParams(
            @Param("title") String title,
            @Param("activity") String activity,
            @Param("sex") int sex,
            @Param("startAge") int startAge,
            @Param("endAge") int endAge,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("departureLat") String departureLat,
            @Param("departureLng") String departureLng,
            @Param("departureDist") int departureDist,
            @Param("destinationLat") String destinationLat,
            @Param("destinationLng") String destinationLng,
            @Param("destinationDist") int destinationDist
    );
}
