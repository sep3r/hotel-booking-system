package com.sepehr.hotelbooking;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class HotelBookingSystemApplicationTests {

    @Test
    void contextLoads() {
    }

}
