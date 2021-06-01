package com.testsigma.onboarding.rest;

import com.testsigma.onboarding.converter.RoomEntityToReservableRoomResponseConverter;
import com.testsigma.onboarding.entity.ReservationEntity;
import com.testsigma.onboarding.entity.RoomEntity;
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
        Page<RoomEntity> roomEntityList = pageableRoomRepository.findAll(pageable);
        Page<ReservableRoomResponse> reservableRoomResponses = roomEntityList
                .map(RoomEntityToReservableRoomResponseConverter::converter);
        reservableRoomResponses.stream().forEach((reservableRoomResponse -> log.debug(reservableRoomResponse.toString())));
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
        RoomEntity roomEntity = roomRepository.findById(roomId).get();
        return new ResponseEntity<RoomEntity>(roomEntity,HttpStatus.OK);
    }

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody ReservationRequest reservationRequest) {
        ReservationEntity reservationEntity = conversionService.convert(reservationRequest,ReservationEntity.class);
        reservationRepository.save((reservationEntity!=null)?reservationEntity:new ReservationEntity());

        RoomEntity roomEntity = roomRepository.findById(reservationRequest.getRoomId()).get();
        roomEntity.addReservationEntity(reservationEntity);
        roomRepository.save(roomEntity);
        reservationEntity.setRoomEntity((roomEntity!=null)?roomEntity:new RoomEntity());
        reservationRepository.save((reservationEntity!=null)?reservationEntity:new ReservationEntity());

        ReservationResponse reservationResponse = conversionService.convert(reservationEntity,ReservationResponse.class);
        return new ResponseEntity<>(reservationResponse,HttpStatus.CREATED);
    }

    @PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservableRoomResponse> updateReservation(
            @RequestBody ReservationRequest reservationRequest) {
        return new ResponseEntity<ReservableRoomResponse>(new ReservableRoomResponse(),HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/{reservationId}")
    public ResponseEntity<ReservableRoomResponse> deleteReservation(
            @PathVariable Long reservationId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}