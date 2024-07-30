package com.likelion.plantication.auth.domain;

import com.nimbusds.jose.shaded.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GoogleToken {
    @SerializedName("access_token")
    String accessToken;
}
