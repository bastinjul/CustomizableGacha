package be.julienbastin.customizablegacha;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class BinderModule extends AbstractModule {

    private final CustomizableGacha plugin;

    public BinderModule(CustomizableGacha plugin) {
        this.plugin = plugin;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        this.bind(CustomizableGacha.class).toInstance(this.plugin);
    }
}
