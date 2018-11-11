package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.domain.clock.Clock;
import ca.ulaval.glo4003.domain.market.MarketRepository;
import ca.ulaval.glo4003.domain.market.MarketUpdater;
import ca.ulaval.glo4003.domain.notification.NotificationSender;
import ca.ulaval.glo4003.infrastructure.clock.ClockDriver;
import ca.ulaval.glo4003.infrastructure.config.SimulationSettings;
import ca.ulaval.glo4003.infrastructure.notification.EmailNotificationSender;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

public class ProductionContext extends AbstractContext {
  private ClockDriver clockDriver;

  @Override
  public void configureApplication(String apiUrl) {
    super.configureApplication(apiUrl);
    startClockAndMarketsUpdater();
    serviceLocator.registerInstance(NotificationSender.class, createAwsSesSender());
  }

  private void startClockAndMarketsUpdater() {
    clockDriver = new ClockDriver(
        serviceLocator.get(Clock.class),
        SimulationSettings.SIMULATION_UPDATE_FREQUENCY);
    new MarketUpdater(
        serviceLocator.get(Clock.class),
        serviceLocator.get(MarketRepository.class));
    clockDriver.start();
  }

  private EmailNotificationSender createAwsSesSender() {
    AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
        .withRegion(Regions.US_EAST_1).build();
    return new EmailNotificationSender(client);
  }

  @Override
  public void configureDestruction() {
    super.configureDestruction();
    clockDriver.stop();
  }
}
