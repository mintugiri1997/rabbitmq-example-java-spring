package com.mintu.rabbitmqexample.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Status {

    private Request request;
    private String status;
    private String message;

}
