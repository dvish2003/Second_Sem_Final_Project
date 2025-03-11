package lk.ijse.back_end_prerental.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ResponseUtil {
    private int code;
    private String msg;
    private Object data;
}
