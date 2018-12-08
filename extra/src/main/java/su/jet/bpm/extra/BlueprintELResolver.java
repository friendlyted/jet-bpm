package su.jet.bpm.extra;


import org.camunda.bpm.engine.impl.javax.el.ELContext;
import org.camunda.bpm.engine.impl.javax.el.ELResolver;
import org.osgi.service.blueprint.container.BlueprintContainer;

import java.beans.FeatureDescriptor;
import java.util.Iterator;


public class BlueprintELResolver extends ELResolver {

    private BlueprintContainer blueprintContainer;

    public BlueprintContainer getBlueprintContainer() {
        return blueprintContainer;
    }

    public void setBlueprintContainer(BlueprintContainer blueprintContainer) {
        this.blueprintContainer = blueprintContainer;
    }

    @SuppressWarnings("unchecked")
    public Object getValue(ELContext context, Object base, Object property) {
        if (base == null && blueprintContainer != null && blueprintContainer.getComponentIds().contains(property)) {
            context.setPropertyResolved(true);
            return blueprintContainer.getComponentInstance((String) property);
        }
        return null;
    }

    public boolean isReadOnly(ELContext context, Object base, Object property) {
        return true;
    }

    public void setValue(ELContext context, Object base, Object property,
                         Object value) {
    }

    public Class<?> getCommonPropertyType(ELContext context, Object arg) {
        return Object.class;
    }

    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context,
                                                             Object arg) {
        return null;
    }

    public Class<?> getType(ELContext context, Object arg1, Object arg2) {
        return Object.class;
    }

}
