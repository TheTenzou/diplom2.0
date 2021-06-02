package ru.uds;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.uds.flow.model.Flow;
import ru.uds.flow.repository.FlowRepository;
import ru.uds.udsupgradeevents.model.Difficulty;
import ru.uds.udsupgradeevents.model.Type;
import ru.uds.udsupgradeevents.model.UpgradeEvent;
import ru.uds.udsupgradeevents.repository.UpgradeEventRepository;
import ru.uds.upgradeplann.model.PlannedUpgrade;
import ru.uds.upgradeplann.model.UpgradePlan;
import ru.uds.upgradeplann.repository.PlannedUpgradeRepository;
import ru.uds.upgradeplann.repository.UpgradePlanRepository;

import java.time.LocalDateTime;
import java.util.Collections;

@SpringBootApplication
public class UdsApplication {

	public CommandLineRunner initDb(
			FlowRepository flowRepository,
			UpgradeEventRepository upgradeEventRepository,
			UpgradePlanRepository upgradePlanRepository,
			PlannedUpgradeRepository plannedUpgradeRepository
	) {
		return args -> {
			Flow flow = new Flow();
			flow.setName("flow");
			flowRepository.save(flow);

			UpgradeEvent upgradeEvent = new UpgradeEvent();
			upgradeEvent.setName("upgrade event");
			upgradeEvent.setDescription("desc");
			upgradeEvent.setDifficulty(Difficulty.HIGH);
			upgradeEvent.setType(Type.PROPAGANDA);
			upgradeEvent.setResourceRequirements(27.0);
			upgradeEventRepository.save(upgradeEvent);

			UpgradePlan  upgradePlan = new UpgradePlan();
			upgradePlan.setDateTime(LocalDateTime.now());
			upgradePlan.setResourceLimit(100.0);
			upgradePlan.setName("upgrade name");

			PlannedUpgrade plannedUpgrade = new PlannedUpgrade();
			plannedUpgrade.setPlan(upgradePlan);
			plannedUpgrade.setUpgradeEvent(upgradeEvent);
			plannedUpgrade.setFlow(flow);

			upgradePlan.setPlannedUpgradeList(Collections.singletonList(plannedUpgrade));
			upgradePlanRepository.save(upgradePlan);
			plannedUpgradeRepository.save(plannedUpgrade);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(UdsApplication.class, args);
	}

}
