package su.jet.bpm.itest;

import org.apache.camel.CamelContext;
import org.apache.camel.component.mock.MockEndpoint;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.javax.el.ELException;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessInstanceWithVariablesImpl;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.VariableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerSuite;
import org.ops4j.pax.exam.util.Filter;
import org.osgi.framework.BundleContext;
import su.jet.bpm.service.api.FromBpmServiceException;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * Single test class for single Karaf distribution.
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerSuite.class)
public class KarafDistributionTest {

    @Inject
    private BundleContext bundleContext;

    @Inject
    private RuntimeService runtimeService;

    @Inject
    private TaskService taskService;

    @Inject
    @Filter("(camel.context.name=smokeContext)")
    private CamelContext smokeContext;

    @Inject
    @Filter("(camel.context.name=bpmToCamelContext1)")
    private CamelContext bpmToCamelContext1;

    private MockEndpoint smokeMock;
    private MockEndpoint bpmToCamelRoute1Mock;
    private MockEndpoint bpmToCamelRoute2Mock;

    @Before
    public void resolveEndpoints() {
        smokeMock = MockEndpoint.resolve(smokeContext, "mock:smoke");
        bpmToCamelRoute1Mock = MockEndpoint.resolve(bpmToCamelContext1, "mock:bpmToCamelRoute1");
        bpmToCamelRoute2Mock = MockEndpoint.resolve(bpmToCamelContext1, "mock:bpmToCamelRoute2");
    }

    @Test
    public void testSmokeProcess() {
        assertNotNull(bundleContext);
        assertNotNull(runtimeService);
        assertNotNull(taskService);

        final String smokeProcess = "smokeProcess";
        runtimeService.startProcessInstanceByKey(smokeProcess);
        final Task task = taskService.createTaskQuery().processDefinitionKey(smokeProcess).singleResult();
        assertNotNull(task);
        assertEquals("Smoke Task", task.getName());
        assertEquals(
                runtimeService.createProcessInstanceQuery().processDefinitionKey(smokeProcess).count(),
                1L
        );
        taskService.complete(task.getId());
        assertEquals(
                runtimeService.createProcessInstanceQuery().processDefinitionKey(smokeProcess).count(),
                0L
        );
    }

    @Test
    public void checkSmokeRoute() throws InterruptedException {
        assertNotNull(smokeMock);
        smokeMock.expectedMessageCount(1);
        smokeMock.assertIsSatisfied();
    }

    @Test
    public void testBpmToCamelNoVars() throws InterruptedException {
        runtimeService.startProcessInstanceByKey("bpmToCamelProcessNoVars");
        assertNotNull(bpmToCamelRoute1Mock);
        bpmToCamelRoute1Mock.expectedMessageCount(1);
        bpmToCamelRoute1Mock.assertIsSatisfied();
    }

    @Test
    public void testBpmToCamelLocalVariable() throws InterruptedException {
        runtimeService.startProcessInstanceByKey("bpmToCamelProcessLocalVariable");
        assertNotNull(bpmToCamelRoute2Mock);
        bpmToCamelRoute2Mock.expectedMessageCount(1);
        bpmToCamelRoute2Mock.expectedHeaderReceived("localVar1", "val1");
        bpmToCamelRoute2Mock.assertIsSatisfied();
    }

    @Test
    public void testBpmToCamelNoSuchRoute() {
        try {
            runtimeService.startProcessInstanceByKey("bpmToCamelProcessNoSuchRoute");
            fail("Exception expected");
        } catch (ProcessEngineException ex) {
            assertEquals(FromBpmServiceException.class, ex.getCause().getCause().getClass());
        }
    }

    @Test
    public void testBpmToCamelRouteError() {
        try {
            runtimeService.startProcessInstanceByKey("bpmToCamelProcessCamelError");
            fail("Exception expected");
        } catch (Exception ex) {
            assertEquals(ProcessEngineException.class, ex.getClass());
            assertEquals(ELException.class, ex.getCause().getClass());
            assertEquals(FromBpmServiceException.class, ex.getCause().getCause().getClass());
            assertEquals(RuntimeException.class, ex.getCause().getCause().getCause().getClass());
        }
    }

    @Test
    public void testBpmCamelHeaders() {
        final ProcessInstanceWithVariablesImpl process = (ProcessInstanceWithVariablesImpl) runtimeService.startProcessInstanceByKey("bpmCamelHeaders");

        final VariableMap variables = process.getVariables();

        assertEquals(variables.get("BODY"), "NewBody");
        assertEquals(variables.get("testHeader"), "testHeaderValue");
    }
}
