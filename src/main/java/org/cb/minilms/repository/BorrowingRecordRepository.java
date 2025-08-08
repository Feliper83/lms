package org.cb.minilms.repository;

import org.cb.minilms.entity.BorrowingRecord;
import org.cb.minilms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    List<BorrowingRecord> findByUserId(Long userId);
    List<BorrowingRecord> findByUserAndReturnedDateIsNull(User user);
    List<BorrowingRecord> findByUser(User user);
    long countByUserAndReturnedDateIsNull(User user);
}