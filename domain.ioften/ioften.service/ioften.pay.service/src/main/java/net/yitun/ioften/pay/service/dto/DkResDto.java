package net.yitun.ioften.pay.service.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DkResDto implements Serializable {

    private int code;

    private Object data;

    private String message;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}