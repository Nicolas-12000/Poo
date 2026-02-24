package com.example.orders_api;

import com.example.orders_api.model.Courier;
import com.example.orders_api.model.Customer;
import com.example.orders_api.model.Merchant;
import com.example.orders_api.model.Product;
import com.example.orders_api.repository.CourierRepository;
import com.example.orders_api.repository.CustomerRepository;
import com.example.orders_api.repository.MerchantRepository;
import com.example.orders_api.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OrdersApiApplication {

	public static void main(String[] args) {
		// load .env into system properties so Spring can pick variables like SPRING_PROFILES_ACTIVE
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(e -> {
			if (System.getProperty(e.getKey()) == null && System.getenv(e.getKey()) == null) {
				System.setProperty(e.getKey(), e.getValue());
			}
		});

		SpringApplication.run(OrdersApiApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(ProductRepository productRepository,
							   CustomerRepository customerRepository,
							   MerchantRepository merchantRepository,
							   CourierRepository courierRepository) {
		return args -> {
			Product p1 = new Product(null, "Pizza", java.math.BigDecimal.valueOf(8.5));
			Product p2 = new Product(null, "Soda", java.math.BigDecimal.valueOf(1.5));
			productRepository.save(p1);
			productRepository.save(p2);

			Merchant m = new Merchant();
			m.setName("TastyFoods");
			m.setMerchantCode("M001");
			merchantRepository.save(m);

			Customer c = new Customer();
			c.setName("John Doe");
			c.setPhone("+573001234567");
			customerRepository.save(c);

			Courier cr = new Courier();
			cr.setName("FastCourier");
			cr.setVehicle("moto");
			courierRepository.save(cr);
		};
	}

}
