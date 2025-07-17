package tn.star.rhconge.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.star.rhconge.Entities.User;
import tn.star.rhconge.Repositories.UserRepository;

import java.util.List;

@Service
public class CongeSchedulerService {

    @Autowired
    private UserRepository userRepository;

    // Cette méthode s'exécutera chaque 1er janvier à 00:00
    @Scheduled(cron = "0 0 0 1 1 *")

    public void gererRenouvellementAnnuelConges() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            int report = Math.min(user.getCongesRestants(), 15);

            user.setCongesAnnuelsRestants(report);
            user.setCongesRestants(report + 25); // report + 25 nouveaux congés
            user.setCongesPris(0); // reset du compteur

            userRepository.save(user);
        }

        System.out.println("✅ Renouvellement annuel des congés effectué avec succès !");
    }
}
