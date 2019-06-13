package com.lambdaschool.zoos.service;

import com.lambdaschool.zoos.repository.TelephoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "telephoneService")
public class TelephoneServiceImpl implements TelephoneService
{
    @Autowired
    TelephoneRepository telephonerepos;
}
