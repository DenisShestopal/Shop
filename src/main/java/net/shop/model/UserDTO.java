package net.shop.model;


import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class UserDTO {

    private Integer id;

    private String login;

    private Boolean admin;

    private Boolean blocked;
}

