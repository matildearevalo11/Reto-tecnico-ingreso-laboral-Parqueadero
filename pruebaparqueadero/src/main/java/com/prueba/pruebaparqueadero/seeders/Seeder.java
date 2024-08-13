package com.prueba.pruebaparqueadero.seeders;

import com.github.javafaker.service.RandomService;
import com.prueba.pruebaparqueadero.entities.*;
import com.prueba.pruebaparqueadero.repositories.HistorialVehiculosRepository;
import com.prueba.pruebaparqueadero.repositories.ParqueaderoRepository;
import com.prueba.pruebaparqueadero.repositories.UsuarioRepository;
import com.prueba.pruebaparqueadero.repositories.VehiculoRepository;
import com.prueba.pruebaparqueadero.services.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;

import java.math.BigDecimal;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@RequiredArgsConstructor
@Component
public class Seeder {

    Faker faker = new Faker();
    private final HistorialVehiculosRepository historialVehiculosRepository;
    private final ParqueaderoRepository parqueaderoRepository;
    private final UsuarioRepository usuarioRepository;
    private final VehiculoRepository vehiculoRepository;
    private final VehiculoService vehiculoService;

    FakeValuesService fakeValuesService = new FakeValuesService(
            new Locale("en-GB"), new RandomService());

    int cantSocios=10;
    int cantParqueaderos=40;
    int cantEntradasVehiculos=200;
    LocalDateTime fechaInicio=LocalDateTime.of(2023,1,1,0,0);
    LocalDateTime fechaFin=LocalDateTime.now();

    String pass="$2a$10$jiOBIS1O06xpyNlk2z4ICOIjtptXs3uNiXd6eSmuccNZo8oTiIJvy";
    Random random=new Random();


    public void seed() {
        List<Usuario> users = seedUsers();
        List<Parqueadero> parkings = seedParkings(users);
        seedHistory(parkings);
    }

    private List<Usuario> seedUsers() {
        List<Usuario> users=new ArrayList<>();

        Usuario admin=new Usuario();
        admin.setEmail("admin@mail.com");
        admin.setNombre(faker.name().fullName());
        admin.setRol(Rol.ADMIN);
        admin.setContrasenia(pass);
        usuarioRepository.save(admin);
        users.add(admin);
        for(int i=0;i<cantSocios;i++){
            Usuario socio=seedUserPartner(Rol.SOCIO,null);
            users.add(socio);
        }
        return users;
    }

    public Usuario seedUserPartner(Rol rol,Usuario socio){
        Usuario user=new Usuario();
        user.setEmail(fakeValuesService.bothify("????##@mail.com"));
        user.setNombre(faker.name().fullName());
        user.setRol(rol);
        user.setContrasenia(pass);
        return usuarioRepository.save(user);
    }

    private Parqueadero seedParking(Usuario usuario) {
        Parqueadero parqueadero=new Parqueadero();
        parqueadero.setNombre(faker.harryPotter().character());
        parqueadero.setSocio(usuario);
        parqueadero.setDireccion(faker.rickAndMorty().character());
        parqueadero.setCapacidadVehicular(faker.number().numberBetween(1, 99));
        Double costoHora= faker.number().randomDouble(2,0,1000);
        parqueadero.setCostoHora(BigDecimal.valueOf(costoHora));
        return parqueaderoRepository.save(parqueadero);
    }

    private List<Parqueadero> seedParkings(List<Usuario> usuarios) {
        List<Parqueadero> parqueaderos = new ArrayList<>();
        int totalParqueaderos = cantParqueaderos;
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.get(i);
            int numParqueaderos = random.nextInt(totalParqueaderos - (usuarios.size() - 1 - i)) + 1;
            numParqueaderos = Math.min(numParqueaderos, totalParqueaderos);
            for (int j = 0; j < numParqueaderos; j++) {
                Parqueadero parqueadero = seedParking(usuario);
                parqueaderos.add(parqueadero);
            }
            totalParqueaderos -= numParqueaderos;
        }
        return parqueaderos;
    }

    private List<HistorialVehiculos> seedHistory(List<Parqueadero> parqueaderos) {
        List<HistorialVehiculos> historialVehiculos = new ArrayList<>();
        int totalEntradas = cantEntradasVehiculos;
        for (int i = 0; i < parqueaderos.size(); i++) {
            Parqueadero parqueadero = parqueaderos.get(i);
            int numEntradas = random.nextInt(totalEntradas - (parqueaderos.size() - 1 - i)) + 1;
            numEntradas = Math.min(numEntradas, totalEntradas);
            for (int j = 0; j < numEntradas; j++) {
                HistorialVehiculos historialVehiculos1 = seedHistorial(parqueadero);
                historialVehiculos.add(historialVehiculos1);
            }
            totalEntradas -= numEntradas;
        }
        return historialVehiculos;
    }


    private HistorialVehiculos seedHistorial(Parqueadero parqueadero) {
        HistorialVehiculos historialVehiculos = new HistorialVehiculos();
        historialVehiculos.setParqueadero(parqueadero);
        Date ini = Date.from(fechaInicio.atZone(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(fechaFin.atZone(ZoneId.systemDefault()).toInstant());
        historialVehiculos.setEntrada(faker.date().between(ini, fin).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        Vehiculo vehiculo = seedVehicle(parqueadero);
        historialVehiculos.setVehiculo(vehiculo);

        return historialVehiculosRepository.save(historialVehiculos);
    }

    private Vehiculo seedVehicle(Parqueadero parqueadero) {
        Vehiculo vehiculo = new Vehiculo();
        String placa = faker.regexify("[A-Z]{3}[0-9]{3}");
        vehiculo.setPlaca(placa);
        vehiculo.setMarca(faker.company().name());
        vehiculo.setModelo(faker.commerce().productName());
        vehiculo.setColor(faker.color().name());
        vehiculo.setParqueadero(parqueadero);
        return vehiculoRepository.save(vehiculo);
    }



}
