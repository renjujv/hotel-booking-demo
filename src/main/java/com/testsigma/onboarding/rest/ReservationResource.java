package com.testsigma.onboarding.rest;

import com.testsigma.onboarding.converter.RoomEntityToReservableRoomResponseConverter;
import com.testsigma.onboarding.entity.Reservation;
import com.testsigma.onboarding.entity.Room;
import com.testsigma.onboarding.model.request.ReservationRequest;
import com.testsigma.onboarding.model.response.ReservableRoomResponse;
import com.testsigma.onboarding.model.response.ReservationResponse;
import com.testsigma.onboarding.repository.PageableRoomRepository;
import com.testsigma.onboarding.repository.ReservationRepository;
import com.testsigma.onboarding.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(ResourceConstants.ROOM_RESERVATION_V1)
@CrossOrigin
public class ReservationResource {
    @Autowired
    PageableRoomRepository pageableRoomRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ConversionService conversionService;

    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern = "dd-MM-yyyy") LocalDate checkin
    /**
     * Get the list of reservations for the provided checkin and checkout dates.
     *
     * @param checkin
     * Checkin date for reservation
     * @param checkout
     * Checkout date for reservation
     */
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ReservableRoomResponse> getAvailableRooms(
            @RequestParam(value = "checkin") String checkin,
            @RequestParam(value = "checkout") String checkout,
            Pageable pageable){

//        String expectedDateFormat = "dd-MM-yyyy";
        String expectedDateFormat = "yyyy-MM-dd";
        log.info("returns pageable data with argument date format {}",expectedDateFormat);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(expectedDateFormat);
        try{
            LocalDate localCheckinDate = LocalDate.parse(checkin, formatter);
            LocalDate localCheckoutDate = LocalDate.parse(checkout, formatter);
        } catch(DateTimeParseException dateTimeParseException){
            throw new RuntimeException("Please check the given date format is same as "+expectedDateFormat,dateTimeParseException);
        }
        Page<Room> roomEntityList = pageableRoomRepository.findAll(pageable);
        Page<ReservableRoomResponse> reservableRoomResponses = roomEntityList
                .map(RoomEntityToReservableRoomResponseConverter::converter);
        reservableRoomResponses.stream().forEach((reservableRoomResponse -> log.debug(reservableRoomResponse.toString())));
        return reservableRoomResponses;
    }

    /**
     * Get the list of all reservations.
     */
    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Room>> getAllAvailableRooms(){
        List<Room> room = roomRepository.findAll();
        return new ResponseEntity<List<Room>>(room,HttpStatus.OK);
    }

    /**
     * Get the details of specified room.
     * @param roomId
     * Id of the room
     */
    @GetMapping(path = "/{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Room> getRoomById(
            @PathVariable Integer roomId) {
        Room room = roomRepository.findById(roomId).orElse(new Room(0,"0"));
        return new ResponseEntity<Room>(room,HttpStatus.OK);
    }

    /**
     * Make a reservation
     * @param reservationRequest
     * Reservation request containing
     * roomID(Room to make reservation on)
     * , checkin Date(Checkin date to create reservation)
     * , checkoutDate(Checkout date to create reservation)
     */
    //TODO Avoid reservation creation with unavailable room ID by checking roomEntity presence in roomRepository
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = conversionService.convert(reservationRequest, Reservation.class);
        reservationRepository.save(reservation);
        Room room = roomRepository.findById(reservationRequest.getRoomId())
                .orElse(new Room(0,"Dummy Room"));
        room.addReservation(reservation);
        roomRepository.save(room);
        assert reservation != null;
        reservation.setBookedRoom(room);
        reservationRepository.save(reservation);

        ReservationResponse reservationResponse = conversionService.convert(reservation,ReservationResponse.class);
        return new ResponseEntity<>(reservationResponse,HttpStatus.CREATED);
    }

    /**
     * Update a reservation
     * @param reservationRequest
     * Reservation update request containing
     * roomID(Room to make reservation on)
     * , checkin Date(Checkin date to create reservation)
     * , checkoutDate(Checkout date to create reservation)
     */
    @PutMapping(path = "/update/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationResponse> updateReservation(
            @PathVariable Integer reservationId,
            @RequestBody ReservationRequest reservationRequest) {
        String message = "Need 'room id', 'checkin date', and 'checkout date' to update reservation. Please add the missing values.";
        Reservation reservation = reservationRepository.findById(reservationId).get();
        if(reservationRequest.getRoomId()==null
                || reservationRequest.getCheckout()==null
                || reservationRequest.getCheckin()==null) {
            throw new RuntimeException(message);
        } else {
            reservation.setCheckin(reservationRequest.getCheckin());
            reservation.setCheckout(reservationRequest.getCheckout());

            Room room = roomRepository.findById(reservationRequest.getRoomId()).orElse(new Room(0, "0"));
            room.addReservation(reservation);
            roomRepository.save(room);
            reservation.setBookedRoom(room);
            reservationRepository.save(reservation);
        }
            ReservationResponse reservationResponse = conversionService.convert(reservation,ReservationResponse.class);
        return new ResponseEntity<>(reservationResponse,HttpStatus.ACCEPTED);
    }

    /**
     * Delete the reservation
     * @param reservationId
     * ID of reservation to be deleted
     */
    //TODO Fix delete reservation by deleting dependent entity from other table
    @DeleteMapping(path = "/delete/{reservationId}")
    public ResponseEntity<String> deleteReservation(
            @PathVariable Integer reservationId) {
        String RESERVATION_DOESNOT_EXIST = String.format("Reservation with id %s does not exist. Please check the reservation Id again.",reservationId);
        String RESERVATION_DELETED = String.format("Reservation with id %s deleted successfully.",reservationId);
        if(reservationRepository.existsById(reservationId)){
            Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                    () -> new EntityNotFoundException(String.format(RESERVATION_DOESNOT_EXIST,reservationId)));
            reservationRepository.delete(reservation);
            return new ResponseEntity<>(RESERVATION_DELETED,HttpStatus.ACCEPTED);
        } else return new ResponseEntity<>(RESERVATION_DOESNOT_EXIST,HttpStatus.NOT_FOUND);
    }
}