package com.film.library.service;

import com.film.library.dto.AdminDTO;
import com.film.library.model.Admin;

public interface AdminService {
    Admin findByUsername(String username);

    Admin save(AdminDTO adminDto);
}
