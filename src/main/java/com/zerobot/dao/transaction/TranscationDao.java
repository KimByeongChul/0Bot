package com.zerobot.dao.transaction;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TranscationDao  {
    void insert(Transaction transaction);
    Transaction findByID(String id);
    void deleteByID(String id);
}
