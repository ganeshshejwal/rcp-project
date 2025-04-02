package com.cennox.hazelcast_server.hazelcast;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.cennox.hazelcast_server.entity.Acquirer;
import com.cennox.hazelcast_server.repository.AcquirerRepository;
import com.hazelcast.map.MapStore;

@Component
public class AcquirerMapStore implements MapStore<Long, Acquirer> {
    
    private AcquirerRepository acquirerRepository;

    public AcquirerMapStore(@Lazy AcquirerRepository acquirerRepository) {
        this.acquirerRepository = acquirerRepository;
    }

    private Logger logger = LoggerFactory.getLogger(AcquirerMapStore.class);

    @Override
    public Acquirer load(Long key) {
       logger.info("Fetching data for key: {} from DB", key);
       return acquirerRepository.findById(key).orElse(null);
    }

    @Override
    public Map<Long, Acquirer> loadAll(Collection<Long> keys) {
        Map<Long, Acquirer> map = new HashMap<>();
        List<Acquirer> acquirers = acquirerRepository.findAllById(keys);
        for (Acquirer acquirer : acquirers) {
            map.put(acquirer.getCacheId(), acquirer);
        }
        logger.info("Fetching multiple keys from DB: {}", keys);
        return map;
    }

    @Override
    public Iterable<Long> loadAllKeys() {
        List<Long> allKeys = acquirerRepository.findAll().stream().map(Acquirer::getCacheId).toList();
        logger.info("Keys loaded: {}", allKeys);
        return allKeys;
    }

    @Override
    public void store(Long key, Acquirer value) {
        logger.info("Storing data for key: {} in DB", key);
        acquirerRepository.save(value);
    }

    @Override
    public void storeAll(Map<Long, Acquirer> map) {
        acquirerRepository.saveAll(map.values());
    }

    @Override
    public void delete(Long key) {
         logger.info("Deleting data for key: {} from DB", key);
        acquirerRepository.deleteById(key);
    }

    @Override
    public void deleteAll(Collection<Long> keys) {
        keys.forEach(acquirerRepository::deleteById);
    }
  
}

