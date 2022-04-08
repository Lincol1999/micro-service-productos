package com.group.microserviceproductos.controller;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

//INDICAMOS LA ESTRUCTURA DE NUESTRO FORMATO DE ERROR
@Getter
@Setter
@Builder
public class ErrorMessage {
    private  String code; //codigo de mensaje
    private List<Map<String, String>> message; //Lista de mensaje, las cuales tendrán el nombre del campo mensaje y el mensaje.
}
