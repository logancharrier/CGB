package cgb.transfert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cgb.transfert.entity.Log;

public interface LogRepository extends JpaRepository<Log, Long> { 
}
