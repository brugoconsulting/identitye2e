/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.identitye2e.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void homePage() throws Exception {
        mockMvc.perform(get("/index.html"))
                .andExpect(content().string(containsString("Validate vehicles in file directory")));
    }

    @Test
    public void vehicleHomePage() throws Exception {
        mockMvc.perform(get("/vehicleHome"))
                .andExpect(content().string(containsString("Validate again")))
                .andExpect(content().string(containsString("cars1.csv")))
                .andExpect(content().string(containsString("cars2.csv")))
                .andExpect(content().string(containsString("cars3.csv")))
                .andExpect(content().string(containsString("cars4.csv")))
                .andExpect(content().string(containsString("cars5.csv")))
                .andExpect(content().string(containsString("cars6.csv")))
                .andExpect(content().string(containsString("cars7.csv")))
                .andExpect(content().string(containsString("cars8.csv")))
                .andExpect(content().string(containsString("cars9.csv")))
                .andExpect(content().string(containsString("cars10.csv")))
                .andExpect(content().string(containsString("vehicles1.xlsx")))
                .andExpect(content().string(containsString("vehicles2.xls")))

                .andExpect(content().string(containsString("YB64AWW")))
                .andExpect(content().string(containsString("HN61GEJ")))
                .andExpect(content().string(containsString("PJ07WNZ")))
        ;
    }

}
