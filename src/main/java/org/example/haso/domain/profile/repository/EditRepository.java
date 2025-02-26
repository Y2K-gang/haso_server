package org.example.haso.domain.profile.repository;

import org.example.haso.domain.profile.entity.Edit;
import org.example.haso.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditRepository extends JpaRepository<Edit, String> {


    Edit findByUserId(String userId);
}
