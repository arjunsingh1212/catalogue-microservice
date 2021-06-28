package org.arjun.cataloguemicroservice.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@SuppressWarnings("PMD.GuardLogStatement")
public class LoggingAspect {

  private static final Logger LOGGER =
          LoggerFactory.getLogger(LoggingAspect.class);

  @Before("execution(public * org.arjun.cataloguemicroservice.service.*.get*())")
  public void logGet() {
    LOGGER.info("Get service called by thread: " + Thread.currentThread().getName());
  }

  @Before("execution(public * org.arjun.cataloguemicroservice.service.*.del*(*))")
  public void logDelete() {
    LOGGER.info("Delete service called by thread: " + Thread.currentThread().getName());
  }

  @Before("execution(public * org.arjun.cataloguemicroservice.service.*.create*(*))")
  public void logCreate() {
    LOGGER.info("Create service called by thread: " + Thread.currentThread().getName());
  }
}
