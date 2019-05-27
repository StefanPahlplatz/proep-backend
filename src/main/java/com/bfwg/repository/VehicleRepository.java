package com.bfwg.repository;

import com.bfwg.model.Available;
import com.bfwg.model.Reservation;
import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.xml.stream.Location;
import java.util.Date;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    List<Vehicle> findByUser(User user);
    List<Vehicle> findByMake(String make);
    List<Vehicle> findByModel(String model);
    List<Vehicle>  findByType(String type);
    Vehicle findByRegistration(String registration);
    List<Vehicle> findByPriceBetween (Double pricelower, Double priceupper);

    @Query("select v from Vehicle v left join v.availables a where v.colour like %?1% and v.make like %?2% and v.model like %?3% and v.type like %?4% and v.price" +
            " between ?5 and ?6 and a.startdate <= ?7 and a.enddate > ?8")
    List<Vehicle> findBySearchParameters(String colour,String make,String model, String type,
                                         Double minprice, Double maxprice, Date startdate, Date enddate);

    @Query("select v from Vehicle v left join v.availables a where v.colour like %?1% and v.make like %?2% and v.model like %?3% and v.type like %?4% and v.price" +
            " between ?5 and ?6 and a.startdate <= ?7 and a.enddate > ?8 and v in ?9")
    List<Vehicle> findBySearchParameters(String colour,String make,String model, String type,
                                         Double minprice, Double maxprice, Date startdate, Date enddate, List<Vehicle> vehicles);

    @Query(value = "select * from Vehicle v where (6371 * acos(cos(radians(?2))*cos(radians(v.latitude))*cos(radians(v.longitude)-radians(?1))+sin(radians(?2))*sin(radians(v.latitude)))) < ?3",nativeQuery = true)
    List<Vehicle> findByLocation(Double lon, Double lat, Double distance);
}
