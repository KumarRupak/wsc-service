/*; Title:  DTO class for Send Registration Status
; Author: Rupak Kumar
; Date:   4 Oct 2021
;==========================================*/

package demo.wsc.beta.model.transport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class RegisterStatus {

    private String status;
    private String token;
    private String date;

    public RegisterStatus(String token) {
        if (!token.equals("NA")) {
            this.status = "success";
            this.token = "Your token is :"+token;
            this.date = LocalDate.now().toString();
        } else  {
            this.status = "failed";
            this.token = "Email has been already registered";
            this.date = LocalDate.now().toString();
        }
    }
}
