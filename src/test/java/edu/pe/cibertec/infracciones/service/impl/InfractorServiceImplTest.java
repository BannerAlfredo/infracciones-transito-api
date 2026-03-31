package edu.pe.cibertec.infracciones.service.impl;
import edu.pe.cibertec.infracciones.model.Infractor;
import edu.pe.cibertec.infracciones.model.Multa;
import edu.pe.cibertec.infracciones.repository.InfractorRepository;
import edu.pe.cibertec.infracciones.repository.MultaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InfractorServiceImplTest {
    @Mock
    private InfractorRepository infractorRepository;

    @Mock
    private MultaRepository multaRepository;

    @InjectMocks
    private InfractorServiceImpl service;

    @Test
    void calcularDeuda_ok() {

        Long infractorId = 1L;

        Infractor infractor = new Infractor();
        infractor.setId(infractorId);

        when(infractorRepository.findById(infractorId))
                .thenReturn(Optional.of(infractor));

        Multa m1 = new Multa();
        m1.setMonto(200.0);

        Multa m2 = new Multa();
        m2.setMonto(345.0);

        List<Multa> multas = List.of(m1, m2);

        when(multaRepository.findByInfractor_Id(infractorId))
                .thenReturn(multas);

        double resultado = service.calcularDeuda(infractorId);

        assertEquals(545.0, resultado);
    }
}
