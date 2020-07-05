package com.cdvtech.talibanairlines

import com.cdvtech.talibanairlines.model.entity.Plane
import com.cdvtech.talibanairlines.model.entity.Profile
import com.cdvtech.talibanairlines.model.entity.Trip
import com.cdvtech.talibanairlines.model.enums.TripClass
import com.cdvtech.talibanairlines.repository.PlaneRepository
import com.cdvtech.talibanairlines.repository.ProfileRepository
import com.cdvtech.talibanairlines.repository.TripRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SpringBootApplication
class TalibanairlinesApplication implements CommandLineRunner {

    @Autowired
    TripRepository tripRepository

    @Autowired
    ProfileRepository profileRepository

    @Autowired
    PlaneRepository planeRepository

    static void main(String[] args) {
        SpringApplication.run TalibanairlinesApplication, args
    }

    @Override
    void run(String... args) throws Exception {
//        Profile profile = new Profile()
//        profile.username = "kuzeadima"
//        profile.password = "123456"
//        profile.email = "test@mail.com"
//        profile.firstName = "Kuzea"
//        profile.lastName = "Rutherford"
//        profile.phoneNumber = "+32842423423"
//        profileRepository.save(profile)
//
//        List<Profile> profileList = profileRepository.findAll()
//
//		Plane plane = new Plane()
//		planeRepository.insert(plane)
//
//        List<Plane> planeList = planeRepository.findAll()
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//
//        Trip trip = new Trip()
//        trip.setDepartureDate(LocalDate.parse("2018-03-19", formatter))
//        trip.setReturningDate(LocalDate.parse("2018-03-21", formatter))
//        trip.setPlaceOfDeparture("Uganda")
//        trip.setPlaceOfArrival("Kiev")
//        trip.setTripClass(TripClass.ECONOMY)
//        trip.isOneway = false
//        trip.setPlane(planeList.get(0))
//        trip.setPassengers(profileList)
//        tripRepository.insert(trip)
//
//		trip.setDepartureDate(LocalDate.parse("2018-03-19", formatter))
//		trip.setReturningDate(LocalDate.parse("2018-03-20", formatter))
//		trip.setPlaceOfDeparture("Hellwill")
//		trip.setPlaceOfArrival("Tokyo")
//		trip.setTripClass(TripClass.BUSINESS)
//        trip.isOneway = false
//		trip.setPlane(planeList.get(0))
//		trip.setPassengers(profileList)
//		tripRepository.insert(trip)
//
//        List<Trip> tripList = tripRepository.findAll()
//
//        for (def it in tripList) {
//            if(it == trip)
//                println it.toString()
//        }
    }
}
