package com.alura.literalura;

import com.alura.literalura.principal.Principal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication(scanBasePackages = "com.alura.literalura")
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private Principal principal; // Inyección del bean Principal

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		principal.muestraElMenu(); // Usa el bean gestionado por Spring
	}
}
