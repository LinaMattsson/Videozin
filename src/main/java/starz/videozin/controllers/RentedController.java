package starz.videozin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import starz.videozin.repositories.RentedRepository;

@Controller
public class RentedController {

    @Autowired
    RentedRepository rentedRepository;

}
