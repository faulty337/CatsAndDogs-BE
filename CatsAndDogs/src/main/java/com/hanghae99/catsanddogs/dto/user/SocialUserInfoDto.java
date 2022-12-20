package com.hanghae99.catsanddogs.dto.user;

import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialUserInfoDto {
    private String id;
    private String email;
    private String nicknmae;

}
