package com.finance.dennis.repository;

import com.finance.dennis.domain.InstrumentMapping;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by XiaoNiuniu on 4/18/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/domain-context.xml","classpath:spring/hsqldb-datasource.xml","classpath:spring/jpa.xml"})
public class InstrumentMappingRepositoryTest extends RepositoryTest{

    @Autowired
    private InstrumentMappingRepository instrumentMappingRepository;

    @Test
    public void SaveAndFind() {
        InstrumentMapping instrumentMapping = new InstrumentMapping("123456", "AStock");
        instrumentMappingRepository.save(instrumentMapping);

        InstrumentMapping instrumentMappingInserted = instrumentMappingRepository.findOne(instrumentMapping.getInstrumentMappingId());
        Assert.assertTrue(instrumentMapping.equals(instrumentMappingInserted));
    }
}
