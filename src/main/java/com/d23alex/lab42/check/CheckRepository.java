package com.d23alex.lab42.check;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CheckRepository  extends CrudRepository<Checking.Check, Integer> {
    List<Checking.Check> findAllByCreator(String creator);
}
