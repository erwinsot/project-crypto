package unal.com.servidor.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import unal.com.servidor.entity.MessagesEntity;

public interface MessageRepository extends JpaRepository<MessagesEntity, Long> {
    List<MessagesEntity> findByMessage(String message);
}
