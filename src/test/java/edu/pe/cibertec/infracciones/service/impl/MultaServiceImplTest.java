package edu.pe.cibertec.infracciones.service.impl;

import edu.pe.cibertec.infracciones.model.EstadoMulta;
import edu.pe.cibertec.infracciones.model.Infractor;
import edu.pe.cibertec.infracciones.model.Multa;
import edu.pe.cibertec.infracciones.model.Vehiculo;
import edu.pe.cibertec.infracciones.repository.InfractorRepository;
import edu.pe.cibertec.infracciones.repository.MultaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MultaServiceImplTest {
    @Mock
    private MultaRepository multaRepository;

    @Mock
    private InfractorRepository infractorRepository;

    @InjectMocks
    private MultaServiceImpl service;

    @Test
    void transferirMulta_ok() {

        Long multaId = 1L;
        Long infractorBId = 2L;

        Multa multa = new Multa();
        multa.setId(multaId);
        multa.setEstado(EstadoMulta.PENDIENTE);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);

        multa.setVehiculo(vehiculo);

        Infractor infractorB = new Infractor();
        infractorB.setId(infractorBId);
        infractorB.setBloqueado(false);

        when(multaRepository.findById(multaId))
                .thenReturn(Optional.of(multa));

        when(infractorRepository.findById(infractorBId))
                .thenReturn(Optional.of(infractorB));

        when(infractorRepository.existsByIdAndVehiculos_Id(infractorBId, 1L))
                .thenReturn(true);

        service.transferirMulta(multaId, infractorBId);

        assertEquals(infractorB, multa.getInfractor());
    }
}
