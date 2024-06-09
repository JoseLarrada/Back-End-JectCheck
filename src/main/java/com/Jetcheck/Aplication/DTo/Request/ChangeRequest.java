package com.Jetcheck.Aplication.DTo.Request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRequest {
    String nowPassword;
    String newPassword;
    String confirmPassword;
}
