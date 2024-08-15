package cl.template.controllers;

import cl.template.models.User;
import cl.template.services.user.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

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

    /*
    CASO 1: EXITOSO
    CASO 2: NO EXISTENTE
     */
    @RepeatedTest(2)
    void testGetUserById(RepetitionInfo repetitionInfo) throws Exception {
        // Obtener el índice de repetición actual
        int repetition = repetitionInfo.getCurrentRepetition();

        // Datos de prueba
        User mockUser = User.builder()
                .id(1L)
                .email("john.doe@example.com")
                .name("John Doe")
                .password("password123")
                .role("Admin")
                .username("john.doe")
                .build();

        switch (repetition) {
            case 1:
                // Configurar el mock para el caso en que el usuario existe
                when(userService.getUserById(1L)).thenReturn(Optional.of(mockUser));

                // Realizar la solicitud y verificar el resultado
                mockMvc.perform(get("/users/1"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(mockUser.getId()))
                        .andExpect(jsonPath("$.name").value(mockUser.getName()))
                        .andExpect(jsonPath("$.email").value(mockUser.getEmail()))
                        .andExpect(jsonPath("$.password").value(mockUser.getPassword()))
                        .andExpect(jsonPath("$.role").value(mockUser.getRole()))
                        .andExpect(jsonPath("$.username").value(mockUser.getUsername()));
                break;

            case 2:
                // Configurar el mock para el caso en que el usuario no existe
                when(userService.getUserById(1L)).thenReturn(Optional.empty());

                // Realizar la solicitud y verificar el resultado
                mockMvc.perform(get("/users/1"))
                        .andExpect(status().isNotFound());
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + repetition);
        }
    }
}
