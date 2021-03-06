package org.ovirt.engine.core.bll;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.ovirt.engine.core.bll.validator.storage.MultipleStorageDomainsValidator;
import org.ovirt.engine.core.common.action.AddVmPoolParameters;
import org.ovirt.engine.core.common.config.ConfigValues;

public class AddVmPoolCommandTest extends CommonVmPoolCommandTestAbstract {

    @Mock
    private MultipleStorageDomainsValidator multipleSdValidator;

    @Override
    protected AddVmPoolCommand<AddVmPoolParameters> createCommand() {
        AddVmPoolParameters param = new AddVmPoolParameters(vmPools, testVm, VM_COUNT);
        param.setStorageDomainId(firstStorageDomainId);
        return new AddVmPoolCommand<>(param, null);
    }

    @Before
    public void setUp() {
        mcr.mockConfigValue(ConfigValues.ValidNumOfMonitors, Arrays.asList("1", "2", "4"));
    }

    @Test
    public void validate() {
        setupForStorageTests();
        assertTrue(command.validate());
    }

    @Test
    public void validatePatternBasedPoolName() {
        String patternBaseName = "aa-??bb";
        command.getParameters().getVmStaticData().setName(patternBaseName);
        command.getParameters().getVmPool().setName(patternBaseName);
        assertTrue(command.validateInputs());
    }

    @Test
    public void validateBeanValidations() {
        assertTrue(command.validateInputs());
    }
}
