package com.glodon.jwt.entity;

import io.jsonwebtoken.Claims;
import lombok.Data;

/**
 * @Author: yinjw-b
 * @Date: 2021/1/25 15:25
 * @Description:
 */
@Data
public class CheckResult {
    private boolean success;
    private Claims claims;
    private String errCode;
}
