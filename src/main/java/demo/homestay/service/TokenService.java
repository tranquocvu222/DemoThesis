package demo.homestay.service;

import demo.homestay.model.Token;

public interface TokenService {
	Token save(Token token);
	Token findByToken(String token);
}
