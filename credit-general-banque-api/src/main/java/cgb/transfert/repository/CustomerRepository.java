package cgb.transfert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cgb.transfert.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
