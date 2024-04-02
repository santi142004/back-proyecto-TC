package com.BackendTc.TuCafe.controller.response;


import com.BackendTc.TuCafe.model.Business;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ClientResponse {

    private String name;
    private String country;
    private String city;
    private String email;
    private String phone;
    private String password;
    private List<Byte> photo;

}
