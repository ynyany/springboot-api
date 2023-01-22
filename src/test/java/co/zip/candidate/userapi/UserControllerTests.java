/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package co.zip.candidate.userapi;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.zip.candidate.userapi.dto.AccountDTO;
import co.zip.candidate.userapi.dto.UpdateUserDTO;
import co.zip.candidate.userapi.dto.UserDTO;
import co.zip.candidate.userapi.model.User;
import co.zip.candidate.userapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.LinkedList;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    private static final UserDTO dummyUserDto = new UserDTO(1l, "testname", "email@email.com", BigDecimal.valueOf(10000), BigDecimal.valueOf(1000));
    private static final UpdateUserDTO updateUser = new UpdateUserDTO(1l, "testname", BigDecimal.valueOf(10000), BigDecimal.valueOf(1000));
    private static final User dummyUser = new User("testname", "email@email.com", BigDecimal.valueOf(10000), BigDecimal.valueOf(1000));

    static {
        dummyUser.setId(1l);
    }

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService service;

    @Test
    public void getUsers() throws Exception {
        LinkedList<UserDTO> value = new LinkedList<>();
        value.add(dummyUserDto);
        when(service.getUsers()).thenReturn(value);
        this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("testname")));

    }

    @Test
    public void getUser() throws Exception {
        when(service.getUser(1l)).thenReturn(dummyUserDto);
        this.mockMvc.perform(get("/users/1")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    public void createUser() throws Exception {

        byte[] jsonContent = toJson(dummyUserDto);
        when(service.createUser(any())).thenReturn(dummyUserDto);
        this.mockMvc.perform(post("/users").content(jsonContent).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void updateUser() throws Exception {

        byte[] jsonContent = toJson(dummyUserDto);
        when(service.updateUser(updateUser)).thenReturn(updateUser);
        this.mockMvc.perform(put("/users/1").content(jsonContent).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void createUserAndValidLocationHeader() throws Exception {
        byte[] jsonContent = toJson(dummyUserDto);
        when(service.createUser(any())).thenReturn(dummyUserDto);

        this.mockMvc.perform(post("/users").content(jsonContent).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andExpect(header().stringValues("Location", "http://localhost/users/1"));
    }

    @Test
    public void createAccount() throws Exception {
        AccountDTO accountDTO = new AccountDTO( "accountName");
        byte[] jsonContent = toJson(accountDTO);
        when(service.createAccount(anyLong(), any())).thenReturn(accountDTO);
        this.mockMvc.perform(post("/users/1/accounts").content(jsonContent).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated());
    }
    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }
}
