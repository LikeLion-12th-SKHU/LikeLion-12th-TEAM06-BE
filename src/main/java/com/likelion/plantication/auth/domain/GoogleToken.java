package com.likelion.plantication.auth.domain;

import com.nimbusds.jose.shaded.gson.annotations.SerializedName;
import lombok.Builder;

@Builder
public record GoogleToken (
        @SerializedName("access_token")
        String accessToken
){
}
