package com.example.asclepiusjobs.repository;

import com.example.asclepiusjobs.model.Cv;
import com.example.asclepiusjobs.model.SkillOrderOnCv;
import com.example.asclepiusjobs.model.SkillOrderOnCvId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillOrderOnCvRepository extends JpaRepository<SkillOrderOnCv, SkillOrderOnCvId> {
    List<SkillOrderOnCv> findAllByCvOrderByAppearanceOrder(Cv cv);
    void deleteByCv(Cv cv);
}
