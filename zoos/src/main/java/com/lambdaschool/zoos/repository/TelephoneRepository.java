package com.lambdaschool.zoos.repository;

import com.lambdaschool.zoos.model.Telephone;
import org.springframework.data.repository.CrudRepository;

public interface TelephoneRepository extends CrudRepository<Telephone, Long>
{
}
