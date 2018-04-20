package starz.videozin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import starz.videozin.entities.Customer;
import starz.videozin.entities.Movie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootApplication
public class VideozinApplication {
	public static Customer activecustomer = new Customer();
		public static List<Movie> cart = new ArrayList();
	public static void main(String[] args) {

		SpringApplication.run(VideozinApplication.class, args);
	}

}
