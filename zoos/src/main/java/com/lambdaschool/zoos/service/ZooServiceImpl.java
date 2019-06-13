package com.lambdaschool.zoos.service;

import com.lambdaschool.zoos.model.Telephone;
import com.lambdaschool.zoos.model.Zoo;
import com.lambdaschool.zoos.repository.ZooRepository;
import org.hibernate.bytecode.enhance.internal.bytebuddy.EnhancerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

@Service(value = "zooService")
public class ZooServiceImpl implements ZooService
{
    private static final Logger logger = LoggerFactory.getLogger(ZooServiceImpl.class);

    @Autowired
    private ZooRepository zoorepos;

    @Override
    public ArrayList<Zoo> findAll()
    {
        ArrayList<Zoo> list = new ArrayList<>();
        zoorepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Zoo findZooById(long id)
    {
        return zoorepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
    }

    @Override
    public Zoo findZooByName(String name) throws EntityNotFoundException
    {
        Zoo zoo = zoorepos.findByZooname(name);

        if (zoo == null)
        {
            throw new EntityNotFoundException("Zoo " + name + " not found!");
        }
        return zoo;
    }

    @Transactional
    @Override
    public void delete(long id) throws EntityNotFoundException
    {
        if (zoorepos.findById(id).isPresent())
        {
            zoorepos.deleteZooFromZooAnimals(id);
            zoorepos.deleteById(id);

            logger.info("Zoo Deleted");
        }else
        {
            throw new EntityNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public void deleteZooAnimalCombo(long zooid, long animalid)
    {
        if (zoorepos.checkZooAnimalCombo(zooid, animalid).getCount() > 0)
        {
            zoorepos.deleteZooAnimalCombo(zooid, animalid);

            logger.info("Zoo Animal Combo Deleted");
        }
        else
        {
            throw new EntityNotFoundException("Zoo id = " + Long.toString(zooid) + " Animal id = " + Long.toString(animalid));
        }
    }

    @Transactional
    @Override
    public Zoo save(Zoo zoo)
    {
        Zoo newZoo = new Zoo();

        newZoo.setZooname(zoo.getZooname());

        for (Telephone t:zoo.getTelephones())
        {
            newZoo.getTelephones().add(new Telephone(t.getPhonetype(),t.getPhonenumber(), newZoo));
        }

        logger.info("Updating a Zoo");
        return zoorepos.save(newZoo);
    }


    @Transactional
    @Override
    public void saveZooAnimalCombo(long zooid, long animalid)
    {
        zoorepos.saveZooAnimalCombo(zooid, animalid);

        logger.info("Zoo Animal Combo Inserted");
    }



    @Transactional
    @Override
    public Zoo update(Zoo zoo, long id)
    {
        Zoo currentZoo = zoorepos.findById(id)
                  .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));

        if (zoo.getZooname() != null)
        {
            currentZoo.setZooname(zoo.getZooname());
        }

        if (zoo.getTelephones().size() > 0)
        {
            // adds new phone numbers to list
            for (Telephone t:zoo.getTelephones())
            {
                currentZoo.getTelephones().add(new Telephone(t.getPhonetype(),t.getPhonenumber(), currentZoo));
            }
        }

        logger.info("Creating a Zoo");
        return zoorepos.save(currentZoo);
    }
}
