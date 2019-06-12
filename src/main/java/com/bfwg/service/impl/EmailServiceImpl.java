package com.bfwg.service.impl;

import com.bfwg.model.Reservation;
import com.bfwg.model.User;
import com.bfwg.model.Vehicle;
import com.bfwg.service.EmailService;
import com.bfwg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void completeRegistration(User user) throws MessagingException{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        final Context context = new Context();

        //Put the content in map
        Map model = new HashMap();
        model.put("firstname", user.getFirstname());
        context.setVariables(model);

        //Get the template
        String html = templateEngine.process("templateRegistration", context);
        helper.setTo(user.getEmail());
        helper.setText(html, true);
        helper.setSubject("AirRnD - Registration");
        javaMailSender.send(message);

    }

    @Override
    public void completeRegistrationOwner(Vehicle vehicle) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        final Context context = new Context();
        Map model = new HashMap();
        model.put("carregistration", vehicle.getRegistration());
        model.put("carmake", vehicle.getMake());
        model.put("cartype", vehicle.getType());
        model.put("colour", vehicle.getColour());
        model.put("model", vehicle.getModel());
        model.put("price", vehicle.getPrice());
        model.put("firstname", vehicle.getUser().getFirstname());
        context.setVariables(model);

        String htmlOwner = templateEngine.process("templateRegistrationOwner", context);
        helper.setTo(vehicle.getUser().getEmail());
        helper.setText(htmlOwner, true);
        helper.setSubject("AirRnD - Registration");
        javaMailSender.send(message);

    }

    @Override
    public void makeReservation(Reservation reservation) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Map model = new HashMap();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
        model.put("reservationid", reservation.getId());
        model.put("firstname", reservation.getUser().getFirstname());
        model.put("startdate", dateFormat.format(reservation.getStartdate()));
        model.put("enddate", dateFormat.format(reservation.getEnddate()));
        model.put("carhost", reservation.getVehicle().getUser().getFirstname());
        model.put("phone", reservation.getVehicle().getUser().getTelephone());
        model.put("pricetotal", reservation.getVehicle().getPrice());
        model.put("address", reservation.getVehicle().getUser().getAddress());
        model.put("carregistration", reservation.getVehicle().getRegistration());
        model.put("carmake", reservation.getVehicle().getMake());
        model.put("cartype", reservation.getVehicle().getType());
        model.put("colour", reservation.getVehicle().getColour());
        model.put("model", reservation.getVehicle().getModel());
        final Context context = new Context();
        context.setVariables(model);

        String htmlReservation = templateEngine.process("templateReservation", context);
        helper.setTo(reservation.getUser().getEmail());
        helper.setText(htmlReservation, true);
        helper.setSubject("AirRnD - Reservation");
        javaMailSender.send(message);

    }
}
