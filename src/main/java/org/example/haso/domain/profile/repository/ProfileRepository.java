package org.example.haso.domain.profile.repository;

import org.example.haso.domain.product.entity.Product;
import org.example.haso.domain.profile.entity.Edit;
import org.example.haso.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, String> {

    Profile findByUserId(String userId);
}
