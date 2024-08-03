package cl.template.models;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;
}
