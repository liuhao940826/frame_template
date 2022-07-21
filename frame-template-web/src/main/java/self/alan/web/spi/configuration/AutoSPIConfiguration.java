package self.alan.web.spi.configuration;


import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.CollectionUtils;
import self.alan.web.spi.SpiInterface;

@Configuration
@ConditionalOnClass(SpiInterface.class)
public class AutoSPIConfiguration implements EnvironmentAware, ApplicationContextAware,
    BeanDefinitionRegistryPostProcessor {

  private Environment environment;

  private ApplicationContext applicationContext;

  private static final String SINGLETON = "singleton";

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Override
  public void setEnvironment(Environment environment) {
    this.environment =environment;
  }

  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
      throws BeansException {
    List<SpiInterface> spiInterfaces = SpringFactoriesLoader.loadFactories(SpiInterface.class,
        this.getClass().getClassLoader());

    if(CollectionUtils.isEmpty(spiInterfaces)){
      return ;
    }

    spiInterfaces.forEach(spi->{
      GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
      genericBeanDefinition.setBeanClass(spi.getClass());
      genericBeanDefinition.setLazyInit(false);
      genericBeanDefinition.setAbstract(false);
      genericBeanDefinition.setAutowireCandidate(true);
      genericBeanDefinition.setScope(SINGLETON);
      System.out.println("bean的名字:"+spi.getClass().getSimpleName());
      registry.registerBeanDefinition(spi.getClass().getSimpleName(),genericBeanDefinition);
    });


  }

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {

  }


}
