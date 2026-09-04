package com.example.demo.modules.sys.sys001structure.repo;

import com.example.demo.modules.sys.sys001structure.entity.Sys001structure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sys001structureRepo extends JpaRepository<Sys001structure, Long> {
    List<Sys001structure> findBySttOrderBySortAsc(int stt);
    List<Sys001structure> findByStt(int stt);

    List<Sys001structure> findBySttAndTypeOrderBySortAsc(int stt, int type);
    List<Sys001structure> findBySttAndPidOrderBySortAsc(int stt, Long pid);

    @Modifying
    @Query("UPDATE Sys001structure s SET s.sort = :sort WHERE s.id = :id")
    void updateParentAndSort(Integer id, Integer sort);
}
