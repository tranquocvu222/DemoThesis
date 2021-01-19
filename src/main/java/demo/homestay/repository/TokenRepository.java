package demo.homestay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.homestay.model.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {
	Token findByToken(String token);
}
