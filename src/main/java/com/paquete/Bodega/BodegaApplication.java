package com.paquete.Bodega;

import com.paquete.Bodega.models.ERole;
import com.paquete.Bodega.models.RoleEntity;
import com.paquete.Bodega.models.Usuario;
import com.paquete.Bodega.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@ComponentScan("com.paquete.Bodega")
public class BodegaApplication {

	public static void main(String[] args) {

		SpringApplication.run(BodegaApplication.class, args);
		System.out.println("Api rest corriendo.");
	}




	/*@Autowired
	PasswordEncoder passwordEncoder;*/

	@Autowired
	UsuarioRepository userRepository;

	/*@Bean
	CommandLineRunner init(){
		return args -> {

			Usuario userEntity = Usuario .builder()

					.username("Gustavo")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(RoleEntity.builder()
							.nombre(ERole.valueOf(ERole.ADMIN.name()))
							.build()))
					.build();

			Usuario userEntity2 = Usuario.builder()

					.username("EMPLEADO")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(RoleEntity.builder()
							.nombre(ERole.valueOf(ERole.USER.name()))
							.build()))
					.build();

			Usuario userEntity3 = Usuario .builder()

					.username("PAO")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(RoleEntity.builder()
							.nombre(ERole.valueOf(ERole.SUBADMIN.name()))
							.build()))
					.build();

			userRepository.save(userEntity);
			userRepository.save(userEntity2);
			userRepository.save(userEntity3);
		};
	}*/


}
