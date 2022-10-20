package uz.mh.click.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

}
