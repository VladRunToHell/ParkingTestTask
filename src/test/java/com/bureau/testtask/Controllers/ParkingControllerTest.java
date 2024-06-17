package com.bureau.testtask.Controllers;

import com.bureau.testtask.Entities.Machine;
import com.bureau.testtask.Repositories.MachineRepository;
import com.bureau.testtask.Services.ParkingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ParkingController.class)
class ParkingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ParkingService parkingService;

    @MockBean
    private MachineRepository machineRepository;

    @Test
    void getAccount() throws Exception {
        Machine machine = new Machine(2.0f, 2, false);
        machineRepository.save(machine);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/parking/{id}", machine.getIdMachine()))
                .andExpect(status().isOk());
        machineRepository.delete(machine);
    }
}