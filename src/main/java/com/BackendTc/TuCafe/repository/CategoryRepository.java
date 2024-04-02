package com.BackendTc.TuCafe.repository;

import com.BackendTc.TuCafe.model.Business;
import com.BackendTc.TuCafe.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
}
