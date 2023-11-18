package com.Jetcheck.Aplication.DTo;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRequest {
    String NowPassword;
    String NewPassword;
    String ConfirmPassword;
}
