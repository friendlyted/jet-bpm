package su.jet.bpm.itest;

import org.ops4j.pax.exam.ConfigurationFactory;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;

import java.io.File;

import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.*;

/**
 * @author Fedor Resnyanskiy
 */
public class OSGiTestEnvironment implements ConfigurationFactory {

    @Override
    public Option[] createConfiguration() {
        return options(
            karafDistributionConfiguration().frameworkUrl(
                maven().groupId("su.jet.bpm")
                    .artifactId("bpm-assembly")
                    .type("zip")
                    .versionAsInProject())
                .useDeployFolder(false)
                .unpackDirectory(new File("target/exam/")),
            features("fake", "bpm-default-engine"),
            keepRuntimeFolder(),
//          logLevel(LogLevelOption.LogLevel.TRACE),
            doNotModifyLogConfiguration(),
//          debugConfiguration("5005", true),
            CoreOptions.junitBundles()
        );
    }


}
