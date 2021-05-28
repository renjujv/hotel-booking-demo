package com.linkedin.learning.rest;

import com.linkedin.learning.converter.RoomEntityToReservableRoomResponseConverter;
import com.linkedin.learning.entity.ReservationEntity;
import com.linkedin.learning.entity.RoomEntity;
import com.linkedin.learning.model.request.ReservationRequest;
import com.linkedin.learning.model.response.ReservableRoomResponse;
import com.linkedin.learning.model.response.ReservationResponse;
import com.linkedin.learning.repository.PageableRoomRepository;
import com.linkedin.learning.repository.ReservationRepository;
import com.linkedin.learning.repository.RoomRepository;
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
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<ReservableRoomResponse> getAvailableRooms(
            @RequestParam(value = "checkin") String checkin,
            @RequestParam(value = "checkout") String checkout,
            Pageable pageable){

        String expectedDateFormat = "dd-MM-yyyy";
        log.info("returns pageable data with argument date format {}",expectedDateFormat);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(expectedDateFormat);
        try{
            LocalDate localCheckinDate = LocalDate.parse(checkin, formatter);
            LocalDate localCheckoutDate = LocalDate.parse(checkout, formatter);
        } catch(DateTimeParseException dateTimeParseException){
            throw new RuntimeException("Please check the given date format is same as "+expectedDateFormat,dateTimeParseException);
        }
        Page<RoomEntity> roomEntityList = pageableRoomRepository.findAll(pageable);
//        Page<ReservableRoomResponse> reservableRoomResponses = roomEntityList
//                .map(RoomEntityToReservableRoomResponseConverter::converter);
        Page<ReservableRoomResponse> reservableRoomResponses = roomEntityList
                .map(RoomEntityToReservableRoomResponseConverter::converter);
        reservableRoomResponses.stream().forEach(System.out::println);
//        reservableRoomResponses.stream()
//                .forEach(reservableRoomResponse -> System.out.println(
//                        "["+reservableRoomResponse.getId()+"," +
//                                " "+reservableRoomResponse.getRoomNumber()+"," +
//                                " "+reservableRoomResponse.getPrice()+"," +
//                                " "+reservableRoomResponse.getLinks()+"]"));
        return reservableRoomResponses;
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RoomEntity>> getAllAvailableRooms(){
        List<RoomEntity> roomEntity = roomRepository.findAll();
        return new ResponseEntity<List<RoomEntity>>(roomEntity,HttpStatus.OK);
    }

    @GetMapping(path = "/{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomEntity> getRoomById(
            @PathVariable Integer roomId) {
        RoomEntity roomEntity = roomRepository.findById(roomId).orElse(new RoomEntity(0,"0"));
        return new ResponseEntity<RoomEntity>(roomEntity,HttpStatus.OK);
    }

    //TODO Avoid reservation creation with unavailable room ID by checking roomEntity presence in roomRepository
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody ReservationRequest reservationRequest) {
        ReservationEntity reservationEntity = conversionService.convert(reservationRequest,ReservationEntity.class);
        reservationRepository.save((reservationEntity!=null)?reservationEntity:new ReservationEntity());

        RoomEntity roomEntity = roomRepository.findById(reservationRequest.getRoomId()).orElse(new RoomEntity(0,"0"));
        roomEntity.addReservationEntity(reservationEntity);
        roomRepository.save(roomEntity);
        assert reservationEntity != null;
        reservationEntity.setRoomEntity(roomEntity);
        reservationRepository.save(reservationEntity);

        ReservationResponse reservationResponse = conversionService.convert(reservationEntity,ReservationResponse.class);
        return new ResponseEntity<>(reservationResponse,HttpStatus.CREATED);
    }

    @PutMapping(path = "/update/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationResponse> updateReservation(
            @PathVariable Integer reservationId,
            @RequestBody ReservationRequest reservationRequest) {
        String message = "Need 'room id', 'checkin date', and 'checkout date' to update reservation. Please add the missing values.";
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId).get();
        if(reservationRequest.getRoomId()==null
                || reservationRequest.getCheckout()==null
                || reservationRequest.getCheckin()==null) {
            throw new RuntimeException(message);
        } else {
            reservationEntity.setCheckin(reservationRequest.getCheckin());
            reservationEntity.setCheckout(reservationRequest.getCheckout());

            RoomEntity roomEntity = roomRepository.findById(reservationRequest.getRoomId()).orElse(new RoomEntity(0, "0"));
            roomEntity.addReservationEntity(reservationEntity);
            roomRepository.save(roomEntity);
            reservationEntity.setRoomEntity(roomEntity);
            reservationRepository.save(reservationEntity);
        }
            ReservationResponse reservationResponse = conversionService.convert(reservationEntity,ReservationResponse.class);
        return new ResponseEntity<>(reservationResponse,HttpStatus.ACCEPTED);
    }

    //TODO Fix delete reservation by deleting dependent entity from other table
    @DeleteMapping(path = "/delete/{reservationId}")
    public ResponseEntity<String> deleteReservation(
            @PathVariable Integer reservationId) {
        String RESERVATION_DOESNOT_EXIST = String.format("Reservation with id %s does not exist. Please check the reservation Id again.",reservationId);
        String RESERVATION_DELETED = String.format("Reservation with id %s deleted successfully.",reservationId);
        if(reservationRepository.existsById(reservationId)){
            ReservationEntity reservationEntity = reservationRepository.findById(reservationId).orElseThrow(
                    () -> new EntityNotFoundException(String.format(RESERVATION_DOESNOT_EXIST,reservationId)));
            reservationRepository.delete(reservationEntity);
            return new ResponseEntity<>(RESERVATION_DELETED,HttpStatus.ACCEPTED);
        } else return new ResponseEntity<>(RESERVATION_DOESNOT_EXIST,HttpStatus.NOT_FOUND);
    }
}