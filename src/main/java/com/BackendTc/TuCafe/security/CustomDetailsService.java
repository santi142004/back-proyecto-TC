package com.BackendTc.TuCafe.security;

import com.BackendTc.TuCafe.model.Admin;
import com.BackendTc.TuCafe.model.Business;
import com.BackendTc.TuCafe.model.Client;
import com.BackendTc.TuCafe.repository.AdminRepository;
import com.BackendTc.TuCafe.repository.BusinessRepository;
import com.BackendTc.TuCafe.repository.ClientRepository;
import com.BackendTc.TuCafe.security.detailsimpl.AdminDetailsImpl;
import com.BackendTc.TuCafe.security.detailsimpl.BusinessDetailsImpl;
import com.BackendTc.TuCafe.security.detailsimpl.ClientDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Business business = findBusinessByEmail(email);
        if (business != null) {
            return new BusinessDetailsImpl(business);
        }

        Client client = findClientByEmail(email);
        if (client != null) {
            return new ClientDetailsImpl(client);
        }

        Admin admin = findAdminByEmail(email);
        if (admin != null) {
            return new AdminDetailsImpl(admin);
        }

        throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
    }

    private Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    private Admin findAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    private Business findBusinessByEmail(String email) {
        return businessRepository.findByEmail(email);
    }


}
