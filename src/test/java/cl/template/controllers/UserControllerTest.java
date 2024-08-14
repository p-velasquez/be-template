package cl.template.controllers;

import cl.template.models.User;
import cl.template.service.user.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception{
        closeable.close();
    }

    @Test
    void testGetUserById() throws Exception {
        // Datos de prueba
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("John Doe");

        // Configurar el mock
        when(userService.getUserById(1L)).thenReturn(Optional.of(mockUser));

        // Realizar la solicitud y verificar el resultado
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        // Configurar el mock para devolver null
        when(userService.getUserById(anyLong())).thenReturn(null);

        // Realizar la solicitud y verificar el resultado
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound());
    }
}
