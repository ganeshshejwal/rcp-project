package com.cennox.hazelcast_server.hazelcast;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.cennox.hazelcast_server.repository.AcquirerRepository;
import com.cennox.sharedlibs.entity.Acquirer;
import com.hazelcast.map.MapStore;

@Component
public class AcquirerMapStore implements MapStore<UUID, Acquirer> {
    
    private AcquirerRepository acquirerRepository;

    public AcquirerMapStore(@Lazy AcquirerRepository acquirerRepository) {
        this.acquirerRepository = acquirerRepository;
    }

    private Logger logger = LoggerFactory.getLogger(AcquirerMapStore.class);

    @Override
    public Acquirer load(UUID key) {
       logger.info("Fetching data for key: {} from DB", key);
       return acquirerRepository.findById(key).orElse(null);
    }

    @Override
    public Map<UUID, Acquirer> loadAll(Collection<UUID> keys) {
        Map<UUID, Acquirer> map = new HashMap<>();
        List<Acquirer> acquirers = acquirerRepository.findAllById(keys);
        for (Acquirer acquirer : acquirers) {
            map.put(acquirer.getAcquirerId(), acquirer);
        }
        logger.info("Fetching multiple keys from DB: {}", keys);
        return map;
    }

    @Override
    public Iterable<UUID> loadAllKeys() {
        List<UUID> allKeys = acquirerRepository.findAll().stream().map(Acquirer::getAcquirerId).toList();
        logger.info("Keys loaded: {}", allKeys);
        return allKeys;
    }

    @Override
    public void store(UUID key, Acquirer value) {
        logger.info("Storing data for key: {} in DB", key);
        acquirerRepository.save(value);
    }

    @Override
    public void storeAll(Map<UUID, Acquirer> map) {
        acquirerRepository.saveAll(map.values());
    }

    @Override
    public void delete(UUID key) {
         logger.info("Deleting data for key: {} from DB", key);
        acquirerRepository.deleteById(key);
    }

    @Override
    public void deleteAll(Collection<UUID> keys) {
        keys.forEach(acquirerRepository::deleteById);
    }
  
}

